package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {
    public List<User> showUsers();

    public void deleteUser(int id);

    public User getUserById(int id);

    public void addUser(User user);

    public void updateUser(User user);
}
