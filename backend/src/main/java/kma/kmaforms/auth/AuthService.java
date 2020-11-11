package kma.kmaforms.auth;

import com.microsoft.azure.spring.autoconfigure.aad.UserPrincipal;
import kma.kmaforms.user.dto.AuthenticatedUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.Map;


@Service
public class AuthService {

    public AuthenticatedUser getAuthorizedUser() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Object> map = (Map<String, Object>) userPrincipal.getClaims();
        AuthenticatedUser user = AuthenticatedUser.builder()
                .email(map.get("email").toString())
                .displayName(map.get("name").toString())
                .build();
        return user;
    }
}
