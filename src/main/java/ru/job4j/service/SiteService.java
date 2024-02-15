package ru.job4j.service;

import ru.job4j.domain.Site;
import ru.job4j.dto.RegistrationDTO;
import ru.job4j.dto.RegistrationResponseDTO;

import java.util.Optional;

public interface SiteService {

    Optional<RegistrationResponseDTO> save(RegistrationDTO registrationDTO);

    Optional<Site> findByLogin(String login);

}
