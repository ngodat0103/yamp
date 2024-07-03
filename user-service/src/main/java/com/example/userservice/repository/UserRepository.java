package com.example.userservice.repository;

import com.example.userservice.entity.Users;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Users,Long> {
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Users save(Users users);
//   User findById(long id);
//   User findByUsernameOrEmail(String username,String email);
//    User findByUsername(String username);
//
//    User findByEmail(String email);
//
//    User findByUsernameOrEmailAndPassword(String username,String email,String password);
//    List<User> getAll();
//    User findByUsernameAndPassword(String username,String password);
}
