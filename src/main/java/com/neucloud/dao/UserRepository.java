package com.neucloud.dao;

import com.neucloud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by neucloud on 2017/6/22.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);

    List<User> findByAge(int age);
}
