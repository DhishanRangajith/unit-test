package com.dra.unittest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dra.unittest.DTO.UserDTO;
import com.dra.unittest.service.UserService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> save(@RequestBody UserDTO dto) {
        UserDTO userDTO = this.userService.save(dto);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> get() {
        List<UserDTO> list = this.userService.getAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getOne(@PathVariable Long id) {
        UserDTO data = this.userService.get(id);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDTO> put(@PathVariable Long id, @RequestBody UserDTO dto) {
        UserDTO data = this.userService.update(id, dto);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<UserDTO> patch(@PathVariable Long id, @RequestBody UserDTO dto) {
        UserDTO data = this.userService.partialUpdate(id, dto);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        this.userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
