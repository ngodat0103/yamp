package com.example.yamp.usersvc.dto.address;

import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

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
