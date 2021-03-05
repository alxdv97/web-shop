package ru.alxdv.nfoclient.service;

public interface UserService {
    Boolean isUserPresent(String username);
    Long createUser(String username);
    void deleteUser(String username);
}
