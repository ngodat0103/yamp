package com.example.userservice.repositories;

import com.example.userservice.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Long>{
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    User save(User user);
    User findByUsername(String username);

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
