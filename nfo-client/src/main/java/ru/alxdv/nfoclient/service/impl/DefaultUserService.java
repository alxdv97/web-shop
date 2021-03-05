package ru.alxdv.nfoclient.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alxdv.nfoclient.entity.User;
import ru.alxdv.nfoclient.repository.UserRepository;
import ru.alxdv.nfoclient.service.UserService;

@Service
public class DefaultUserService implements UserService {

    @Autowired
    private UserRepository repo;

    @Override
    public Boolean isUserPresent(String username) {
        return repo.existsByUsername(username);
    }

    @Override
    public Long createUser(String username) {
        return repo.save(User.builder().username(username).build()).getId();
    }

    @Override
    public void deleteUser(String username) {
        repo.deleteByUsername(username);
    }
}
