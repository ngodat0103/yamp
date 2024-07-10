package com.example.userservice.dto.model;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    @Nullable
    private Long user_id;
    @Email
    @NotNull private String email;
    @NotNull private String username;
    @NotNull private String password;
    @NotNull private String firstName;
    @NotNull private String lastName;
}
