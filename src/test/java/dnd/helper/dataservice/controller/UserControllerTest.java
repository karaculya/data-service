package dnd.helper.dataservice.controller;

import dnd.helper.dataservice.service.UserService;
import openapi.dto.ModifyUser;
import openapi.dto.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User testUser;
    private ModifyUser testModifyUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.userId(1L);
        testUser.setUsername("testUser");

        testModifyUser = new ModifyUser();
        testModifyUser.setUsername("updatedUser");
    }

    @Test
    void addMasterRole_ShouldReturnUserWithMasterRole() {
        Long userId = 1L;
        User expectedUser = new User();
        expectedUser.userId(userId);
        expectedUser.setUsername("testUser");

        when(userService.addRole(userId, User.RolesEnum.MASTER)).thenReturn(expectedUser);

        ResponseEntity<User> response = userController.addMasterRole(userId);
        
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(expectedUser, response.getBody());
        verify(userService, times(1)).addRole(userId, User.RolesEnum.MASTER);
    }

    @Test
    void addPlayerRole_ShouldReturnUserWithPlayerRole() {
        Long userId = 1L;
        User expectedUser = new User();
        expectedUser.userId(userId);
        expectedUser.setUsername("testUser");

        when(userService.addRole(userId, User.RolesEnum.PLAYER)).thenReturn(expectedUser);

        ResponseEntity<User> response = userController.addPlayerRole(userId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(expectedUser, response.getBody());
        verify(userService, times(1)).addRole(userId, User.RolesEnum.PLAYER);
    }

    @Test
    void createUser_ShouldReturnCreatedUser() {
        when(userService.createUser(testUser)).thenReturn(testUser);

        ResponseEntity<User> response = userController.createUser(testUser);
        
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(testUser, response.getBody());
        verify(userService, times(1)).createUser(testUser);
    }

    @Test
    void getUser_ShouldReturnUser() {
        Long userId = 1L;
        when(userService.getUserById(userId)).thenReturn(testUser);
        
        ResponseEntity<User> response = userController.getUser(userId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(testUser, response.getBody());
        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser() {
        Long userId = 1L;
        User updatedUser = new User();
        updatedUser.userId(userId);
        updatedUser.setUsername("updatedUser");

        when(userService.updateUser(eq(userId), any(ModifyUser.class))).thenReturn(updatedUser);

        ResponseEntity<User> response = userController.updateUser(userId, testModifyUser);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(updatedUser, response.getBody());
        verify(userService, times(1)).updateUser(userId, testModifyUser);
    }

    @Test
    void addMasterRole_WithNullId_ShouldCallServiceWithNull() {
        User expectedUser = new User();
        when(userService.addRole(null, User.RolesEnum.MASTER)).thenReturn(expectedUser);

        ResponseEntity<User> response = userController.addMasterRole(null);
        
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(userService, times(1)).addRole(null, User.RolesEnum.MASTER);
    }

    @Test
    void getUser_WithNonExistentId_ShouldReturnUserFromService() {
        Long nonExistentId = 999L;
        when(userService.getUserById(nonExistentId)).thenReturn(null);
        
        ResponseEntity<User> response = userController.getUser(nonExistentId);
        
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(userService, times(1)).getUserById(nonExistentId);
    }

    @Test
    void updateUser_WithNullModifyUser_ShouldCallServiceWithNull() {
        Long userId = 1L;
        User updatedUser = new User();
        updatedUser.userId(userId);

        when(userService.updateUser(eq(userId), isNull())).thenReturn(updatedUser);
        
        ResponseEntity<User> response = userController.updateUser(userId, null);
        
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(userService, times(1)).updateUser(userId, null);
    }
}
