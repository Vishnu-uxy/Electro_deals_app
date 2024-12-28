package com.cts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

	@NotNull
    private long id;

    @NotNull(message="name should not be null")
    @Size(min = 4, max = 15)
    private String name;

    @NotEmpty(message="email should not be null")
    @Email
   
    private String email;

    @NotEmpty
    @Size(min = 3, max = 15, message = "Password must be a minimum of three characters and a maximum of 10 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character")
    private String password;
}
