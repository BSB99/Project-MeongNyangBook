package com.example.meongnyangbook.user;

public enum UserRoleEnum {
    MEMBER(Authority.MEMBER),  // 사용자 권한
    ADMIN(Authority.ADMIN),  // 관리자 권한
    BLOCK(Authority.BLOCK);  // 차단 상태

    private final String authority;

    UserRoleEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String MEMBER = "ROLE_MEMBER";
        public static final String ADMIN = "ROLE_ADMIN";
        public static final String BLOCK = "ROLE_BLOCK";
    }
}