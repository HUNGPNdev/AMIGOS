package com.amigos.user.service;

import com.amigos.authentication.request.SignUpForm;
import com.amigos.common.MockData;
import com.amigos.common.ResponseApi;
import com.amigos.dto.UserDTO;
import com.amigos.errors.handle.ObjectDuplicateException;
import com.amigos.role.model.Role;
import com.amigos.role.model.RoleName;
import com.amigos.role.repository.RoleRepository;
import com.amigos.user.model.User;
import com.amigos.user.repository.UserRepository;
import com.amigos.user.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    PasswordEncoder encoder;

    @Mock
    RoleRepository roleRepository;

    @Mock
    ModelMapper modelMapper;

    MockData mockData;

    @Before
    public void init() {
        mockData = new MockData();
    }


    @Test(expected = ObjectDuplicateException.class)
    public void registration_Not_Found_UserName_Return_ObjectDuplicateException() {
        SignUpForm signUpForm = new SignUpForm("username", "firstname", "lastName", "email", "password", "phone", "address", Arrays.asList("admin"));
        when(userRepository.existsByUserName(signUpForm.getUserName())).thenReturn(true);
        try {
            userService.registerUser(signUpForm);
        } catch (Exception ex) {
            if(ex instanceof ObjectDuplicateException) {
                throw (ObjectDuplicateException)ex;
            } else fail();
        }
    }

    @Test(expected = ObjectDuplicateException.class)
    public void registration_With_Email_Exists_Return_ObjectDuplicateException() {
        SignUpForm signUpForm = new SignUpForm("username", "firstname", "lastName", "email", "password", "phone", "address", Arrays.asList("admin"));
        when(userRepository.existsByEmail(signUpForm.getEmail())).thenReturn(true);
        try {
            userService.registerUser(signUpForm);
        } catch (Exception ex) {
            if(ex instanceof ObjectDuplicateException) {
                throw (ObjectDuplicateException)ex;
            } else fail();
        }
    }

    @Test
    public void registration_With_Role_Admin() {
        SignUpForm signUpForm = new SignUpForm("username", "firstname", "lastName", "email", "password", "phone", "address", Arrays.asList("admin"));
        UserDTO userAssignDTO = mockData.getUserAssignDTO();
        when(roleRepository.findByName(RoleName.ROLE_ADMIN)).thenReturn(Optional.of(new Role()));
        Mockito.when(modelMapper.map(signUpForm, UserDTO.class)).thenReturn(userAssignDTO);

        ResponseApi response = userService.registerUser(signUpForm);
        ResponseApi responseApi = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase());

        assertEquals(responseApi, response);
    }

    @Test
    public void registration_With_Role_User_Return_Ok() {
        SignUpForm signUpForm = new SignUpForm("username", "firstname", "lastName", "email", "password", "phone", "address", Arrays.asList("user"));
        UserDTO u = mockData.getUserAssignDTO();
        when(roleRepository.findByName(RoleName.ROLE_USER)).thenReturn(Optional.of(new Role()));
        when(modelMapper.map(signUpForm, UserDTO.class)).thenReturn(u);
        ResponseApi signUpForm2 = userService.registerUser(signUpForm);

        ResponseApi responseApi = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase());
        assertEquals(responseApi, signUpForm2);
    }

    @Test
    public void registration_With_Role_Pm_Return_Ok() {
        SignUpForm signUpForm = new SignUpForm("username", "firstname", "lastName", "email", "password", "phone", "address", Arrays.asList("pm"));
        UserDTO u = mockData.getUserAssignDTO();
        when(roleRepository.findByName(RoleName.ROLE_PM)).thenReturn(Optional.of(new Role()));
        when(modelMapper.map(signUpForm, UserDTO.class)).thenReturn(u);
        ResponseApi signUpForm2 = userService.registerUser(signUpForm);

        ResponseApi responseApi = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase());
        assertEquals(responseApi, signUpForm2);
    }

    @Test
    public void TestPR() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        System.out.println(date.getTime());
    }
}
