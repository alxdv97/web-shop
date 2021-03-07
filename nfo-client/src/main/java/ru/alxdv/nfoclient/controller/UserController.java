package ru.alxdv.nfoclient.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alxdv.nfoclient.service.UserService;

@RestController
@RequestMapping(value = "/auth/users")
@Tag(name = "User auth controller", description = "Provides user's authentications services")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/{username}")
    @Operation(
            summary = "Check if user exist",
            description = "Returns all customers"
    )
    public Boolean isUserPresent(@Parameter(description = "Username")
                                   @PathVariable String username) {
        return service.isUserPresent(username);
    }
}
