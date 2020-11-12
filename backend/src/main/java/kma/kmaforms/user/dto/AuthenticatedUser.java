package kma.kmaforms.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticatedUser {
    private String email;
    private String displayName;
}
