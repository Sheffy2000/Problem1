package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.dao.UserRepo;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserDao userDao;

    private UserRepo userRepo;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> showUsers() {
        return userRepo.findAll ();
    }

    @Transactional
    @Override
    public void addUser(User user) {
        user.setPassword (passwordEncoder.encode (user.getPassword ()));
        userDao.addUser (user);
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        user.setPassword (passwordEncoder.encode (user.getPassword ()));
        userDao.updateUser (user);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepo.findByUsername (username);
    }

    @Transactional
    @Override
    public User getUserById(int id) {
        return userDao.getUserById (id);
    }

    @Transactional
    @Override
    public void deleteUser(int id) {
        userDao.deleteUser (id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername (username);

        if (user == null) {
            System.out.println ("User not found: " + username);
            throw new UsernameNotFoundException ("User not found: " + username);
        }

        return user;
    }


}
