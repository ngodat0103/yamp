package com.github.ngodat0103.yamp.authsvc.dto.mapper;

import com.github.ngodat0103.yamp.authsvc.dto.OperationDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.Operation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OperationMapper extends AbstractMapper<OperationDto, Operation> {}
