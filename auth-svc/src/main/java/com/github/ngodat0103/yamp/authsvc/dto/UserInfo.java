package com.github.ngodat0103.yamp.authsvc.dto;

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
