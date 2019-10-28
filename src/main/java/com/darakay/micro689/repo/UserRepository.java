package com.darakay.micro689.repo;

import com.darakay.micro689.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    boolean existsByLogin(String login);
}
