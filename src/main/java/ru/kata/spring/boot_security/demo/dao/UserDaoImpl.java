package ru.kata.spring.boot_security.demo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> showUsers() {
        List<User> allUsers = em.createQuery ("from User", User.class).getResultList ();
        return allUsers;
    }

    @Override
    public void deleteUser(int id) {
        User user = em.find (User.class, id);
        if (user == null) {
            throw new EntityNotFoundException ("Такого пользователя нет");
        }
        em.remove (user);
    }

    @Override
    public User getUserById(int id) {
        User user = em.find (User.class, id);
        if (user == null) {
            throw new EntityNotFoundException ("Такого пользователя нет");
        }
        return user;
    }

    @Override
    public void addUser(User user) {
        em.persist (user);
    }

    @Override
    public void updateUser(User user) {
        em.merge (user);
    }
}