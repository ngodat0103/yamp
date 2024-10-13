package com.github.ngodat0103.yamp.authsvc.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(nullable = false, updatable = false)
  private UUID uuid;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @JsonIgnore private boolean enabled = true;
  @JsonIgnore private boolean accountNonExpired = true;
  @JsonIgnore private boolean accountNonLocked = true;
  @JsonIgnore private boolean credentialsNonExpired = true;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "role_uuid")
  private Role role;

  public Account(String email, String username, String password) {
    this.username = username;
    this.password = password;
    this.email = email;
  }
}
