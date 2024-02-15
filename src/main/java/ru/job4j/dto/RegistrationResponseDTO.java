package ru.job4j.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(of = {"login"})
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponseDTO {

    @NotNull
    private Boolean registration = false;

    @NotBlank
    private String login;

    @Min(6)
    private String password = "";

    public RegistrationResponseDTO(String login) {
        this.login = login;
    }

}
