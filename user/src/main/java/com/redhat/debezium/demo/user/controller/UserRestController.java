package com.redhat.debezium.demo.user.controller;

import java.util.List;

import com.redhat.debezium.demo.user.domain.OrderDTO;
import com.redhat.debezium.demo.user.domain.Users;
import com.redhat.debezium.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public Users get(@PathVariable int userId){
        return userService.getUser(userId).get();
    }

    @GetMapping
    public List<Users> create() {
        return userService.allUsers();
    }

    @PostMapping
    public Users create(@RequestBody Users user) {
        userService.createUser(user);
        return user;
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable int userId){
        Users user = get(userId);
        userService.deleteUser(user);
    }

    @PostMapping("/{userId}/order")
    public void addOrder(@PathVariable int userId, @RequestBody OrderDTO order){
        System.out.println("addOrder("+userId+","+order.getOrderId()+")");
        Users user = get(order.getUserId());
        userService.addOrder(user, String.valueOf(order.getOrderId()));
        userService.updateUser(user);
    }

    @DeleteMapping("/{userId}/order/{orderId}")
    public void deleteOrder(@PathVariable int userId, @PathVariable int orderId){
        System.out.println("deleteOrder("+userId+","+orderId+")");
        Users user = get(userId);
        userService.deleteOrder(user, ""+orderId);
        userService.updateUser(user);
    }
}
