package ru.job4j.service;

import ru.job4j.domain.Site;
import ru.job4j.dto.RegistrationDTO;
import ru.job4j.dto.RegistrationResponseDTO;
import ru.job4j.exception.ServiceException;

import java.util.Optional;

public interface SiteService {

    RegistrationResponseDTO save(RegistrationDTO registrationDTO) throws ServiceException;

    Optional<Site> findByLogin(String login);

}
