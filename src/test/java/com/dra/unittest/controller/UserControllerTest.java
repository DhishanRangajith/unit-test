package com.dra.unittest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.dra.unittest.DTO.UserDTO;
import com.dra.unittest.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    @Test
    public void save_responseShouldBeMatch(){
        UserDTO inputData = new UserDTO(null, "AAA", "BBB");
        UserDTO outputData = new UserDTO(100L, "AAA", "BBB");
        when(userService.save(inputData)).thenReturn(outputData);
        ResponseEntity<UserDTO> response = userController.save(inputData);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(outputData, response.getBody());
        verify(userService, times(1)).save(inputData);
    }

    @Test
    public void get_responseShouldBeMatch(){
        List<UserDTO> outputData = new ArrayList<>();
        outputData.add(new UserDTO(100L, "AAA", "BBB"));
        when(userService.getAll()).thenReturn(outputData);
        ResponseEntity<List<UserDTO>> response = userController.get();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(outputData, response.getBody());
        verify(userService, times(1)).getAll();
    }

    @Test
    public void getOne_responseShouldBeMatch(){
        Long inputId = 100L;
        UserDTO outputData = new UserDTO(100L, "AAA", "BBB");
        when(userService.get(inputId)).thenReturn(outputData);
        ResponseEntity<UserDTO> response = userController.getOne(inputId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(outputData, response.getBody());
        verify(userService, times(1)).get(inputId);
    }

    @Test
    public void put_responseShouldBeMatch(){
        Long inputId = 100L;
        UserDTO inputData = new UserDTO(100L, "AAA", "BBB");
        UserDTO outputData = new UserDTO(100L, "AAA", "BBB");
        when(userService.update(inputId, inputData)).thenReturn(outputData);
        ResponseEntity<UserDTO> response = userController.put(inputId, inputData);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(outputData, response.getBody());
        verify(userService, times(1)).update(inputId, inputData);
    }

    @Test
    public void patch_responseShouldBeMatch(){
        Long inputId = 100L;
        UserDTO inputData = new UserDTO(100L, "AAA", "BBB");
        UserDTO outputData = new UserDTO(100L, "AAA", "BBB");
        when(userService.partialUpdate(inputId, inputData)).thenReturn(outputData);
        ResponseEntity<UserDTO> response = userController.patch(inputId, inputData);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(outputData, response.getBody());
        verify(userService, times(1)).partialUpdate(inputId, outputData);
    }

    @Test
    public void delete_responseShouldBeMatch(){
        Long inputId = 100L;
        doNothing().when(userService).delete(inputId);
        ResponseEntity<?> response = userController.delete(inputId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).delete(inputId);
    }

}

