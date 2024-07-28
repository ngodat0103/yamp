package org.example.authservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID roleUuid;
    @Column(unique = true,nullable = false)
    @NotNull
    @Setter
    private String roleName;
    @Setter
    private String roleDescription;
    public Role(String roleName){
        this.roleName = roleName;
    }
}
