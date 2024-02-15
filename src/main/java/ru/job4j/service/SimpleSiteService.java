package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.domain.Site;
import ru.job4j.dto.RegistrationDTO;
import ru.job4j.dto.RegistrationResponseDTO;
import ru.job4j.mapper.SiteMapper;
import ru.job4j.repository.SiteRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleSiteService implements SiteService {

    final static Logger LOGGER = LoggerFactory.getLogger(SimpleSiteService.class);
    private BCryptPasswordEncoder encoder;
    private final SiteRepository siteRepository;
    private final SiteMapper siteMapper;

    @Override
    public Optional<RegistrationResponseDTO> save(RegistrationDTO registrationDTO) {
        if (findByLogin(registrationDTO.getSite()).isEmpty()) {
            try {
                String password = getPassword();
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

    private String getPassword() {
        return encoder.encode(String.valueOf(Math.random())).substring(8, 14);
    }

    private String getHash(String password) {
        return encoder.encode(password);
    }

}
