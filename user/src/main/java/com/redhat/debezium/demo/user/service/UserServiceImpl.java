package com.redhat.debezium.demo.user.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.redhat.debezium.demo.user.domain.Users;
import com.redhat.debezium.demo.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public void createUser(Users user) {
        userRepo.save(user);
    }

    @Override
    public Optional<Users> getUser(Integer userId) {
        return userRepo.findById(userId);
    }

    @Override
    public void updateUser(Users user) {
        userRepo.save(user);
    }

    @Override
    public void deleteUser(Users user) {
        userRepo.delete(user);
    }

    @Override
    public void addOrder(Users user, String orderId) {
        user.addOrder(orderId);
        userRepo.save(user);
    }

    @Override
    public void deleteOrder(Users user, String orderId) {
        user.deleteOrder(orderId);
    }

    @Override
    public int countOrders(Users user) {
        return user.getOrders().size();
    }

    @Override
    public List<Users> allUsers() {
        return StreamSupport.stream(userRepo.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
