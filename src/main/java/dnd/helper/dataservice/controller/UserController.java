package dnd.helper.dataservice.controller;

import openapi.api.UsersApi;
import openapi.dto.ModifyUser;
import openapi.dto.User;
import dnd.helper.dataservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController implements UsersApi {
    private final UserService service;

    @Override
    public ResponseEntity<User> addMasterRole(Long id) {
        return ResponseEntity.ok(service.addRole(id, User.RolesEnum.MASTER));
    }

    @Override
    public ResponseEntity<User> addPlayerRole(Long id) {
        return ResponseEntity.ok(service.addRole(id, User.RolesEnum.PLAYER));
    }

    @Override
    public ResponseEntity<User> createUser(User user) {
        return ResponseEntity.ok(service.createUser(user));
    }

    @Override
    public ResponseEntity<User> getUser(Long id) {
        return ResponseEntity.ok(service.getUserById(id));
    }

    @Override
    public ResponseEntity<User> updateUser(Long id, ModifyUser modifyUser) {
        return ResponseEntity.ok(service.updateUser(id, modifyUser));
    }
}
