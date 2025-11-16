package dnd.helper.dataservice.service;

import openapi.dto.ModifyUser;
import openapi.dto.User;
import dnd.helper.dataservice.exception.NotFoundException;
import dnd.helper.dataservice.model.entity.UserEntity;
import dnd.helper.dataservice.model.mapper.UserMapper;
import dnd.helper.dataservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public User addRole(Long userId, User.RolesEnum role) {
        UserEntity entity = findUserById(userId);
        entity.setRoles(List.of(role));
        return mapper.toDto(entity);
    }

    public User createUser(User user) {
        UserEntity entity = new UserEntity();
        Long id = entity.getId();
        entity = mapper.toEntity(user);
        entity.setId(id);
        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    public User getUserById(Long userId) {
        UserEntity entity = findUserById(userId);
        return mapper.toDto(entity);
    }

    public User updateUser(Long userId, ModifyUser modifyUser) {
        UserEntity entity = findUserById(userId);
        if (modifyUser.getBio() != null) entity.setBio(modifyUser.getBio());
        if (modifyUser.getUsername() != null) entity.setUsername(modifyUser.getUsername());
        if (modifyUser.getAvatarUrl() != null) entity.setAvatarUrl(modifyUser.getAvatarUrl());
        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    private UserEntity findUserById(Long userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + userId + " не найден"));
    }
}
