package com.dra.unittest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.dra.unittest.DTO.UserDTO;
import com.dra.unittest.entity.UserEntity;
import com.dra.unittest.exception.NotFoundException;
import com.dra.unittest.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    public void getAll_shouldReturnList(){
        List<UserDTO> expectOutput = Arrays.asList(
            new UserDTO(1L, "AAA", "BBB"),
            new UserDTO(2L, "CCC", "DDD")
        );
        List<UserEntity> entityList = Arrays.asList(
            new UserEntity(1L, "AAA", "BBB"),
            new UserEntity(2L, "CCC", "DDD")
        );
        when(userRepository.findAll()).thenReturn(entityList);
        List<UserDTO> returnData = userService.getAll();
        assertEquals(expectOutput, returnData);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void save_shouldReturnList(){
        UserDTO inputD = new UserDTO(null, "AAA", "BBB");
        UserEntity returnEntity = new UserEntity(1L, "AAA", "BBB");
        when(userRepository.save(any(UserEntity.class))).thenReturn(returnEntity);
        UserDTO returnData = userService.save(inputD);
        assertEquals(returnData.getId(), returnEntity.getId());
        assertEquals(returnData.getName(), returnEntity.getName());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void get_shouldReturnItem(){
        Long inputId = 1L;
        UserEntity returnEntity = new UserEntity(1L, "AAA", "BBB");
        when(userRepository.findById(inputId)).thenReturn(Optional.of(returnEntity));
        UserDTO returnData = userService.get(inputId);
        assertEquals(returnEntity.getId(), returnData.getId());
        verify(userRepository, times(1)).findById(inputId);
    }

    @Test
    public void get_shouldReturnExcpWhenUserNotFound(){
        when(userRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userService.get(any()));
        verify(userRepository, times(1)).findById(any());
    }

    @Test
    public void update_shouldReturnItem(){
        Long inputId = 1L;
        UserDTO inputData = new UserDTO(inputId, "AAA", "BBB");
        UserDTO expectOutput = new UserDTO(inputId, "AAA", "BBB");
        UserEntity returnEntity = new UserEntity(1L, "AAA", "BBB");
        when(userRepository.findById(inputId)).thenReturn(Optional.of(returnEntity));
        when(userRepository.save(any(UserEntity.class))).thenReturn(returnEntity);
        UserDTO returnData = userService.update(inputId, inputData);
        assertEquals(expectOutput, returnData);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void update_shouldReturnExcpWhenUserNotFound(){
        Long id = 1L;
        when(userRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userService.update(id, any(UserDTO.class)));
        verify(userRepository, times(1)).findById(any());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    public void partialUpdate_shouldReturnItem(){
        Long inputId = 1L;
        UserDTO inputData = new UserDTO(inputId, "AAA", "BBB");
        UserDTO expectOutput = new UserDTO(inputId, "AAA", "BBB");
        UserEntity returnEntity = new UserEntity(1L, "AAA", "BBB");
        when(userRepository.findById(inputId)).thenReturn(Optional.of(returnEntity));
        when(userRepository.save(any(UserEntity.class))).thenReturn(returnEntity);
        UserDTO returnData = userService.partialUpdate(inputId, inputData);
        assertEquals(expectOutput, returnData);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void partialUpdate_shouldReturnExpWhenUserNotFound(){
        Long id = 1L;
        when(userRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userService.partialUpdate(id, any(UserDTO.class)));
        verify(userRepository, times(1)).findById(any());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    public void delete_shouldReturnItem(){
        Long inputId = 1L;
        UserEntity returnEntity = new UserEntity(1L, "AAA", "BBB");
        when(userRepository.findById(any())).thenReturn(Optional.of(returnEntity));
        doNothing().when(userRepository).deleteById(inputId);
        userService.delete(inputId);
        verify(userRepository, times(1)).findById(any());
        verify(userRepository, times(1)).deleteById(inputId);
    }

    @Test
    public void delete_shouldReturnNotFountExceptionWhenNotFoundId(){
        Long inputId = 200L;
        when(userRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userService.delete(inputId));
        verify(userRepository, times(1)).findById(any());
        verify(userRepository, never()).deleteById(any()); //never reach this
    }

}
