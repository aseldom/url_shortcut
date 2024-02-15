package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.domain.Site;
import ru.job4j.domain.Url;
import ru.job4j.dto.RegistrationUrlDTO;
import ru.job4j.dto.ResponseStatisticDTO;
import ru.job4j.dto.ResponseUrlDTO;
import ru.job4j.mapper.SiteMapper;
import ru.job4j.repository.CrudRepository;
import ru.job4j.repository.SiteRepository;
import ru.job4j.repository.UrlRepository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UrlServiceImpl implements UrlService {

    final static Logger LOGGER = LoggerFactory.getLogger(SimpleSiteService.class);
    private final BCryptPasswordEncoder encoder;

    private final UrlRepository urlRepository;
    private final SiteRepository siteRepository;
    private final CrudRepository crudRepository;
    private final SiteMapper mapper;

    @Override
    public Optional<ResponseUrlDTO> save(RegistrationUrlDTO urlDTO) {
        try {
            Optional<Site> optionalSite = siteRepository.findByLogin(urlDTO.getSite());
            if (optionalSite.isPresent()) {
                Url url = new Url(urlDTO.getUrl(), getHashCode(), optionalSite.get());
                urlRepository.save(url);
                return Optional.of(new ResponseUrlDTO(url.getHashCode()));
            }

        } catch (Exception e) {
        LOGGER.error("Error save URL: " + e.getMessage());
    }
        return Optional.empty();
    }

    @Override
    public Optional<Url> findByHashCode(String hashCode) {
        Optional<Url> optionalUrl = urlRepository.findByHashCode(hashCode);
        if (optionalUrl.isPresent()) {
            crudRepository.runWithConfirm(
                    "UPDATE Url SET counter = counter + 1 WHERE hashCode = :fHashCode",
                    Map.of("fHashCode", hashCode)
            );
        }
        return optionalUrl;
    }

    @Override
    public Collection<ResponseStatisticDTO> findAllBySite(String siteLogin) {
        Site site = siteRepository.findByLogin(siteLogin).get();
        Collection<Url> urls = urlRepository.findAllBySite(site);
        return mapper.getResponseStatisticDTO(urls);
    }

    private String getHashCode() {
        return encoder.encode(String.valueOf(Math.random())).substring(8, 14);
    }
}