package com.example.userservice.dto.model;
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
    private Long id;
    @NotNull
    private String name;
    @Email
    @NotNull
    private String email;
    @NotNull
    @Size
    private String username;
    @NotNull
    @Size
    private String password;
}
