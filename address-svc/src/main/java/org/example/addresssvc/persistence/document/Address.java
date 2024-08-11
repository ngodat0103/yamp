package org.example.addresssvc.persistence.document;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Address {
    @Id
    @JsonIgnore
    private UUID uuid = UUID.randomUUID();
    @NotNull
    @Indexed(unique = true)
    private UUID customerUuid;
    @NotNull
    @Indexed(unique = true)
    private String name;
    @NotNull
    private String cityName;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String province;
    @NotNull String street;
    @NotNull
    private String ward;
    @NotNull
    private String district;

    @NotNull
    private String addressType;

    @JsonIgnore
    private LocalDateTime createAt = LocalDateTime.now();
    @JsonIgnore
    private LocalDateTime lastModifiedAt = LocalDateTime.now() ;


}
