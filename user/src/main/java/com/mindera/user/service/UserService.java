package com.mindera.user.service;

import com.mindera.user.exception.UserAlreadyExistsException;
import com.mindera.user.exception.UserNotFoundException;
import com.mindera.user.domain.User;
import com.mindera.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    private void validateUserNotFound(Optional<User> user, Integer id, String x) {
        if (user.isEmpty()) {
            throw new UserNotFoundException("User " + id + x);
        }
    }
    public User getOne(Integer id) {
        Optional<User> user = repository.findById(id);
        validateUserNotFound(user, id, " not found");
        return user.get();
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public User addOne(User user) {
        try {
            repository.save(user);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("duplicate key")) {
                throw new UserAlreadyExistsException("User already exists!", e);
            }
        }
        return user;
    }

    public void updateUser(Integer id, User user) {
        validateUserNotFound(repository.findById(id), id, " not found!");
        user.setId(id);
        repository.save(user);
    }

    public void partiallyUpdateUser(Integer id, User toUpdate) {
        Optional<User> user = repository.findById(id);
        validateUserNotFound(repository.findById(id), id, " not found!");

        if (!isNull(toUpdate.getName())) {
            user.get().setName(toUpdate.getName());
        }

        if (!isNull(toUpdate.getPassword())) {
            user.get().setPassword(toUpdate.getPassword());
        }

        if (!isNull(toUpdate.getContacts())) {
            if (!isNull(toUpdate.getContacts().getPhoneNumber())) {
                user.get().getContacts().setPhoneNumber(toUpdate.getContacts().getPhoneNumber());
            }
        }

        repository.save(user.get());
    }

    public void deleteUser(Integer id) {
        Optional<User> user = repository.findById(id);
        validateUserNotFound(user, id, " not found!");
        repository.delete(user.get());
    }
}
