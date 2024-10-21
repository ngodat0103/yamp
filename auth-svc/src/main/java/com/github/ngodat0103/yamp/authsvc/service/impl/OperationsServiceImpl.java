package com.github.ngodat0103.yamp.authsvc.service.impl;

import com.github.ngodat0103.yamp.authsvc.dto.permission.OperationsDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.module.Operations;
import com.github.ngodat0103.yamp.authsvc.service.EntityCrudService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class OperationsServiceImpl
    implements EntityCrudService<Operations, OperationsDto, Long> {

    @Override
    public OperationsDto create(OperationsDto newDto) {
        return null;
    }

    @Override
    public OperationsDto readById(Long id) {
        return null;
    }

    @Override
    public OperationsDto update(Long id, OperationsDto updateDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<OperationsDto> findAll() {
        return List.of();
    }
}
