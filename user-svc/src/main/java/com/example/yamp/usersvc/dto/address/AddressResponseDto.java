package com.example.yamp.usersvc.dto.address;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;


@Data
@Builder
public class AddressResponseDto {
    private UUID customerUuid;
    int currentElements;
    int totalElements;
    int totalPages;
    int currentPage;
    private Set<AddressDto> addresses;
}
