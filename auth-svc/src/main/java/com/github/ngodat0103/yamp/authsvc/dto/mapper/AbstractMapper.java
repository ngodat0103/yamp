package com.github.ngodat0103.yamp.authsvc.dto.mapper;

public interface AbstractMapper<Dto, Entity> {
  Dto toDto(Entity entity);

  Entity toEntity(Dto dto);
}
