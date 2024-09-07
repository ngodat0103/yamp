package com.github.ngodat0103.yamp.authsvc.dto.kafka;


import java.io.Serializable;
import java.time.LocalDateTime;


public record UpdateAccountMessage(Action action, LocalDateTime lastModifiedAt)  {
}
