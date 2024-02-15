package ru.job4j.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RegistrationUrlDTO {

    @NotBlank
    private String url;

    private  String site;

}
