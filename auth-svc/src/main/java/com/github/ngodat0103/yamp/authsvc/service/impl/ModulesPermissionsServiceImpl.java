package com.github.ngodat0103.yamp.authsvc.service.impl;

import com.github.ngodat0103.yamp.authsvc.dto.permission.ModulesPermissionsDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.permission.ModulesPermissions;
import com.github.ngodat0103.yamp.authsvc.service.EntityCrudService;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ModulesPermissionsServiceImpl
    implements EntityCrudService<ModulesPermissions, ModulesPermissionsDto, Long> {

    @Override
    public ModulesPermissionsDto create(ModulesPermissionsDto newDto) {
        return null;
    }

    @Override
    public ModulesPermissionsDto readById(Long aLong) {
        return null;
    }

    @Override
    public ModulesPermissionsDto update(Long aLong, ModulesPermissionsDto updateDto) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public List<ModulesPermissionsDto> findAll() {
        return List.of();
    }
}
