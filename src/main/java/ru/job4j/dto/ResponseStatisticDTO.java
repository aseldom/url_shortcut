package ru.job4j.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = {"url"})
public class ResponseStatisticDTO {

    private String url;

    private int total;

}
