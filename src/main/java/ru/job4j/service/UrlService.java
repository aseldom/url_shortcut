package ru.job4j.service;

import ru.job4j.domain.Url;
import ru.job4j.dto.RegistrationUrlDTO;
import ru.job4j.dto.ResponseUrlDTO;

import java.util.Optional;

public interface UrlService {

    Optional<ResponseUrlDTO> save(RegistrationUrlDTO url);

    Optional<Url> findByHashCode(String hashCode);

}
