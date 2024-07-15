package com.example.userservice.dto.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    @Nullable
    @JsonProperty ( access = JsonProperty.Access.READ_ONLY)
    private Long userId;
    @Email
    @NotNull private String email;
    @NotNull private String username;
    @JsonProperty( access = JsonProperty.Access.WRITE_ONLY)
    @NotNull private String password;
    @NotNull private String firstName;
    @NotNull private String lastName;
}
