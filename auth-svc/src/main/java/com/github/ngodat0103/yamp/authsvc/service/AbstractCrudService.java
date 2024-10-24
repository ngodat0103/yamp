package com.github.ngodat0103.yamp.authsvc.service;

import com.github.ngodat0103.yamp.authsvc.dto.mapper.AbstractMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public abstract class AbstractCrudService<Dto, Entity, Id> implements CrudService<Dto, Id> {
  protected AbstractMapper<Dto, Entity> mapper;
}
