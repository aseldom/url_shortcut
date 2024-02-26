package ru.job4j.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.domain.Site;
import ru.job4j.dto.RegistrationDTO;
import ru.job4j.dto.RegistrationResponseDTO;
import ru.job4j.exception.ServiceException;
import ru.job4j.mapper.SiteMapper;
import ru.job4j.repository.SiteRepository;
import ru.job4j.service.SiteService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SimpleSiteService implements SiteService {

    private final static Logger LOGGER = LoggerFactory.getLogger(SimpleSiteService.class);
    private final BCryptPasswordEncoder encoder;
    private final SiteRepository siteRepository;
    private final SiteMapper siteMapper;

    @Value("${password.begin.character}")
    private int begin;
    @Value("${password.length}")
    private int length;

    @Override
    public RegistrationResponseDTO save(RegistrationDTO registrationDTO) throws ServiceException {
        String password = getPassword(begin, length);
        Site site = new Site(
                registrationDTO.getSite(),
                getHash(password)
        );
        try {
            siteRepository.save(site).setPassword(password);
            return siteMapper.getRegistrationResponseDTO(site);
        } catch (Exception e) {
            LOGGER.error("Error save site: ", e);
            throw new ServiceException("can't save site", e);
        }
    }

    @Override
    public Optional<Site> findByLogin(String login) {
        return siteRepository.findByLogin(login);
    }

    private String getPassword(int begin, int length) {
        return encoder.encode(String.valueOf(Math.random())).substring(begin, begin + length);
    }

    private String getHash(String password) {
        return encoder.encode(password);
    }

}
