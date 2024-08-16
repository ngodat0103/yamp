package org.example.authservice.dto;

import lombok.*;

import java.util.Set;
@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private String username;
    private Set<String> roles;

}
