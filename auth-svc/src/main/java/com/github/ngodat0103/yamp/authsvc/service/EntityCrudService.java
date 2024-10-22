package com.github.ngodat0103.yamp.authsvc.service;

import java.util.List;

public interface EntityCrudService<E, D, ID> {

  D create(D newDto);

  D readById(ID id);

  D update(ID id, D updateDto);

  void deleteById(ID id);

  List<D> findAll();
}
