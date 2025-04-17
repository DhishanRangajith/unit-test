package com.dra.unittest.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String name;
    private String email;

}
