package com.redhat.debezium.demo.user.repository;

import com.redhat.debezium.demo.user.domain.Users;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Users, Integer> {
}
