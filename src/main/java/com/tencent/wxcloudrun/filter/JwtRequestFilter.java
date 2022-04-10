package com.tencent.wxcloudrun.filter;

import com.tencent.wxcloudrun.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author james mu
 * @date 2020/9/7 19:14
 */
@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final String DEV_TOKEN = "dev_token";

    private final UserDetailsService userDetailsService;

    public JwtRequestFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String jwtToken = request.getHeader("Authorization");
        if (StringUtils.equals(jwtToken, DEV_TOKEN)) {
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication() == null && StringUtils.isNotEmpty(jwtToken)) {
            Claims claims = JwtUtils.getAllClaimsFromToken(jwtToken);
            String openId = claims.getSubject();
            UserDetails userDetails = userDetailsService.loadUserByUsername(openId);
            // if token is valid configure Spring Security to manually set authentication
            if (JwtUtils.validateClaims(claims, userDetails)) {
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(token);
            }
        }
        chain.doFilter(request, response);
    }
}