package com.github.ngodat0103.yamp.authsvc.event;


import java.time.LocalDateTime;
import java.util.UUID;


public record Model (Action action,
                     UUID uuid,
                     LocalDateTime createAt,
                     LocalDateTime lastModifiedAt){

}
