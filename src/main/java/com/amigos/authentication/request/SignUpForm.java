package com.amigos.authentication.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpForm {

    @NotNull
    @NotBlank(message = "User name is mandatory")
    private String userName;

    @NotNull
    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotNull
    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @NotNull
    @NotBlank(message = "Last name is mandatory")
    @Pattern(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")
    private String email;

    @NotNull
    @NotBlank(message = "Password is mandatory")
    private String password;
    private String phone;
    private String address;

    private List<String> role;
}
