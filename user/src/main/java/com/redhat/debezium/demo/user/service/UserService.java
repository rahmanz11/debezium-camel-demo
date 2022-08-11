package com.redhat.debezium.demo.user.service;

import java.util.List;
import java.util.Optional;

import com.redhat.debezium.demo.user.domain.Users;

public interface UserService {

    void createUser(Users user);

    Optional<Users> getUser(Integer userId);

    void updateUser(Users user);

    void deleteUser(Users user);

    void addOrder(Users user, String orderId);

    void deleteOrder(Users user, String orderId);

    int countOrders(Users user);

    List<Users> allUsers();
}
