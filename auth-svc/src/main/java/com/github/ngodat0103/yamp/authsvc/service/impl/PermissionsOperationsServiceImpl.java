package com.github.ngodat0103.yamp.authsvc.service.impl;

import com.github.ngodat0103.yamp.authsvc.dto.permission.PermissionsOperationsDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.permission.PermissionsOperations;
import com.github.ngodat0103.yamp.authsvc.service.EntityCrudService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PermissionsOperationsServiceImpl
    implements EntityCrudService<PermissionsOperations, PermissionsOperationsDto, Long> {

    @Override
    public PermissionsOperationsDto create(PermissionsOperationsDto newDto) {
        return null;
    }

    @Override
    public PermissionsOperationsDto readById(Long aLong) {
        return null;
    }

    @Override
    public PermissionsOperationsDto update(Long aLong, PermissionsOperationsDto updateDto) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public List<PermissionsOperationsDto> findAll() {
        return List.of();
    }
}
