package org.example.authservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
public class AccountRole {
    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
   public static class Id implements Serializable {
       private UUID accountUuid;
       private UUID roleUuid;
   }
     @EmbeddedId
     private Id id = new Id();
    @CreationTimestamp
    private LocalDateTime addOn ;
    @ManyToOne
    @JoinColumn(name = "accountUuid",insertable = false,updatable = false)
    private Account account;
    @ManyToOne
    @JoinColumn(name = "roleUuid",insertable = false,updatable = false)
    private Role role;
    public AccountRole(Account account, Role role){
        this.account = account;
        this.role = role;
        this.id.accountUuid = account.getAccountUuid();
        this.id.roleUuid = role.getRoleUuid();
    }
}
