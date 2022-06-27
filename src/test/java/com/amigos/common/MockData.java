package com.amigos.common;


import com.amigos.dto.UserDTO;

import java.util.UUID;

public class MockData {
    public UserDTO getUserAssignDTO() {
        UserDTO use = new UserDTO();
        use.setId(UUID.randomUUID());
        use.setUserName("userName");
        use.setFirstName("first name");
        use.setLastName("last name");
        use.setEmail("admin@gmail.com");
        use.setAddress("UK");
        return use;
    }
}
