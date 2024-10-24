package com.github.ngodat0103.yamp.authsvc.service;

import java.util.Set;

public interface CrudService<Dto, Id> {

  Dto create(Dto newDto);

  Dto readById(Id id);

  Dto update(Id id, Dto updateDto);

  void deleteById(Id id);

  Set<Dto> findAll();
}
