package kz.alken1t15.backratinglogcollege.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public class GrantedAuthorityImpl implements GrantedAuthority {
    private final String authority;

    private final boolean role;

    @Override
    public String getAuthority() {
        if (role) {
            return "ROLE_" + authority;
        }
        return authority;
    }
}