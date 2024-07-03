package com.example.userservice.dto.model;
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
    private Long id;
    @NotNull(message = "Tên không được để trống")
    private String name;
    @Email
    @NotNull(message = "Email không được để trống")
    private String email;
    @NotNull(message = "Mật khẩu không được để trống")
    @Size(min = 3,message= " Username phải có tối thiểu 3 kí tự")
    private String username;
    @NotNull
    @Size(min = 8,message = "Mật khẩu tối thiểu 8 kí tự")
    private String password;
}
