// package com.github.ngodat0103.yamp.authsvc.service.impl;
//
// import static com.github.ngodat0103.yamp.authsvc.Util.*;
//
// import com.github.ngodat0103.yamp.authsvc.dto.OperationDto;
// import com.github.ngodat0103.yamp.authsvc.dto.mapper.OperationMapper;
// import com.github.ngodat0103.yamp.authsvc.persistence.entity.Operation;
// import com.github.ngodat0103.yamp.authsvc.persistence.repository.OperationRepository;
// import com.github.ngodat0103.yamp.authsvc.service.CrudService;
// import java.util.List;
// import lombok.AllArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.stereotype.Service;
//
// @Service
// @Slf4j
// @AllArgsConstructor
// public class OperationServiceImpl implements CrudService<Operation, OperationDto, Long> {
//  private OperationRepository operationRepository;
//  private OperationMapper operationMapper;
//
//  @Override
//  public OperationDto create(OperationDto newDto) {
//    if (operationRepository.existsByName(newDto.getName())) {
//      throwConflictException(log, "Operation", "name", newDto.getName());
//    }
//    Operation operation = operationMapper.toEntity(newDto);
//    operation = operationRepository.save(operation);
//    return operationMapper.toDto(operation);
//  }
//
//  @Override
//  public OperationDto readById(Long id) {
//    Operation operation =
//        operationRepository
//            .findById(id)
//            .orElseThrow(notFoundExceptionSupplier(log, "Operation", "id", id));
//    return operationMapper.toDto(operation);
//  }
//
//  @Override
//  public OperationDto update(Long id, OperationDto updateDto) {
//    Operation operation =
//        operationRepository
//            .findById(id)
//            .orElseThrow(notFoundExceptionSupplier(log, "Operation", "id", id));
//    if (operationRepository.existsByName(updateDto.getName())) {
//      throwConflictException(log, "Operation", "name", updateDto.getName());
//    }
//    operation.setName(updateDto.getName());
//    operation = operationRepository.save(operation);
//    return operationMapper.toDto(operation);
//  }
//
//  @Override
//  public void deleteById(Long id) {
//    if (!operationRepository.existsById(id)) {
//      throwNotFoundException(log, "Operation", "id", id);
//    }
//    operationRepository.deleteById(id);
//  }
//
//  @Override
//  public List<OperationDto> findAll() {
//    return operationRepository.findAll().stream().map(operationMapper::toDto).toList();
//  }
// }
