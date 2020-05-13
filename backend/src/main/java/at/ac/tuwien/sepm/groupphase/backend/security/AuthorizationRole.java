package at.ac.tuwien.sepm.groupphase.backend.security;

public enum AuthorizationRole {

    USER(AuthorizationRole.USER_ROLE), ADMIN(AuthorizationRole.USER_ROLE, AuthorizationRole.ADMIN_ROLE);

    private String[] authorities;

    AuthorizationRole(String... authorities) {
        this.authorities = authorities;
    }

    public String[] getAuthorities() {
        return authorities;
    }

    public final static String USER_ROLE = "ROLE_USER";
    public final static String ADMIN_ROLE = "ROLE_ADMIN";
}
