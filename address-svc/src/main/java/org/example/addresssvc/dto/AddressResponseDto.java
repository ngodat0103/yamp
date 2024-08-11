package org.example.addresssvc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AddressResponseDto {
 private UUID customerUuid;
 int elements;
 int currentPage;
 int totalPages;
 Set<AddressDto> data;
}
