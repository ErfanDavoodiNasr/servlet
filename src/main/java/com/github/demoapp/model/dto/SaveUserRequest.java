package com.github.demoapp.model.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class SaveUserRequest {

    private Integer id;

    @Size(min = 3, max = 20, message = "First name must be between 3 and 20 characters")
    @NotBlank(message = "First name cannot be empty")
    private String firstName;

    @Size(min = 3, max = 20, message = "Last name must be between 3 and 20 characters")
    @NotBlank(message = "Last name cannot be empty")
    private String lastName;

    @NotBlank(message = "Phone number cannot be empty")
    @Pattern(regexp = "^[A-Za-z0-9._-]+@gmail\\.com$")
    private String email;

    @Size(min = 8, max = 20, message = "password must contain exactly 8-20 digits")
    @NotBlank(message = "password cannot be empty")
    private String password;

    private String userProfileURL;
}