package com.example.userservice.repositories;

import com.example.userservice.entity.Users;
import jakarta.validation.constraints.NotNull;
import org.apache.catalina.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface UserRepository extends CrudRepository<Users,Long>{
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Users save( Users users);
    Users findByUsername(String username);

    //  ArrayList<User> getAll(Pageable pageable);
//   User findById(long id);
//   User findByUsernameOrEmail(String username,String email);
//    User findByUsername(String username);
//
//    User findByEmail(String email);
//
//    User findByUsernameOrEmailAndPassword(String username,String email,String password);
//    User findByUsernameAndPassword(String username,String password);
}
