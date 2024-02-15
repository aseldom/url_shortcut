package ru.job4j.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RegistrationDTO {

    @NotBlank
    private String site;

    private String password;

}
