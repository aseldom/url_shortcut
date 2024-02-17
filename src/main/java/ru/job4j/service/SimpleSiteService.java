package ru.job4j.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.domain.Site;
import ru.job4j.dto.RegistrationDTO;
import ru.job4j.dto.RegistrationResponseDTO;
import ru.job4j.mapper.SiteMapper;
import ru.job4j.repository.SiteRepository;

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
    public Optional<RegistrationResponseDTO> save(RegistrationDTO registrationDTO) {
        if (findByLogin(registrationDTO.getSite()).isEmpty()) {
            try {
                String password = getPassword(begin, length);
                Site site = new Site(
                        registrationDTO.getSite(),
                        getHash(password)
                );
                siteRepository.save(site).setPassword(password);
                RegistrationResponseDTO dto = siteMapper.getRegistrationResponseDTO(site);
                return Optional.of(dto);
            } catch (Exception e) {
                LOGGER.error("Error save person: " + e.getMessage());
            }
        }
        return Optional.of(new RegistrationResponseDTO(registrationDTO.getSite()));
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
