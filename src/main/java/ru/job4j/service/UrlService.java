package ru.job4j.service;

import ru.job4j.domain.Url;
import ru.job4j.dto.RegistrationUrlDTO;
import ru.job4j.dto.ResponseStatisticDTO;
import ru.job4j.dto.ResponseUrlDTO;
import ru.job4j.exception.ServiceException;

import java.util.Collection;
import java.util.Optional;

public interface UrlService {

    ResponseUrlDTO save(RegistrationUrlDTO url) throws ServiceException;

    Optional<Url> findByHashCode(String hashCode);

    Collection<ResponseStatisticDTO> findAllBySite(String site);

}
