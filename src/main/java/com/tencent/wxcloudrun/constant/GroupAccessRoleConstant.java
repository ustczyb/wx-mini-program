package com.tencent.wxcloudrun.constant;

import java.util.HashMap;
import java.util.Map;

public class GroupAccessRoleConstant {

    public static final String GROUP_CREATER = "组长";
    public static final String GROUP_ADMINISTRATOR = "管理员";
    public static final String GROUP_MEMBER = "组员";

    private static final Map<Integer, String> ID_ROLE_CONSTANT_MAP = new HashMap<>();
    private static final Map<String, Integer> ROLE_ID_CONSTANT_MAP = new HashMap<>();

    static {
        ID_ROLE_CONSTANT_MAP.put(1, GROUP_CREATER);
        ID_ROLE_CONSTANT_MAP.put(2, GROUP_ADMINISTRATOR);
        ID_ROLE_CONSTANT_MAP.put(3, GROUP_MEMBER);

        ROLE_ID_CONSTANT_MAP.put(GROUP_CREATER, 1);
        ROLE_ID_CONSTANT_MAP.put(GROUP_ADMINISTRATOR, 2);
        ROLE_ID_CONSTANT_MAP.put(GROUP_MEMBER, 3);
    }

    public static String getRoleConstantById(Integer id) {
        return ID_ROLE_CONSTANT_MAP.get(id);
    }

    public static Integer getIdByStr(String roleStr) {
        return ROLE_ID_CONSTANT_MAP.get(roleStr);
    }


}
