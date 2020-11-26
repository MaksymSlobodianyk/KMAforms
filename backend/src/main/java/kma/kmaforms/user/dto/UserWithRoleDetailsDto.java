package kma.kmaforms.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserWithRoleDetailsDto {
    private String email;
    private String displayName;
    private String role;
}
