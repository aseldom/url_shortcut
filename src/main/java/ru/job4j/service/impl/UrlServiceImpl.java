package ru.job4j.service.impl;

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
import ru.job4j.exception.ServiceException;
import ru.job4j.mapper.SiteMapper;
import ru.job4j.repository.CrudRepository;
import ru.job4j.repository.SiteRepository;
import ru.job4j.repository.UrlRepository;
import ru.job4j.service.UrlService;

import java.util.Collection;
import java.util.Collections;
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
    public ResponseUrlDTO save(RegistrationUrlDTO urlDTO) throws ServiceException {
        try {
            Optional<Site> optionalSite = siteRepository.findByLogin(urlDTO.getSite());
            if (optionalSite.isEmpty()) {
                throw new RuntimeException("Can't find site in database");
            }
            Url url = new Url(urlDTO.getUrl(), getHashCode(), optionalSite.get());
            urlRepository.save(url);
            return new ResponseUrlDTO(url.getHashCode());
        } catch (Exception e) {
        LOGGER.error("Error save URL: ", e);
        throw new ServiceException("Error save URL", e);
        }
    }

    @Override
    public Optional<Url> findByHashCode(String hashCode) {
        Optional<Url> optionalUrl = urlRepository.findByHashCode(hashCode);
        if (optionalUrl.isPresent()) {
            increaseCounter(hashCode);
        }
        return optionalUrl;
    }

    @Override
    public Collection<ResponseStatisticDTO> findAllBySite(String siteLogin) {
        Optional<Site> optionalSite = siteRepository.findByLogin(siteLogin);
        if (optionalSite.isEmpty()) {
            return Collections.emptyList();
        }
        Collection<Url> urls = urlRepository.findAllBySite(optionalSite.get());
        return mapper.getResponseStatisticDTO(urls);
    }

    private void increaseCounter(String hashCode) {
        crudRepository.runWithConfirm(
                "UPDATE Url SET counter = counter + 1 WHERE hashCode = :fHashCode",
                Map.of("fHashCode", hashCode)
        );
    }

    private String getHashCode() {
        return encoder.encode(String.valueOf(Math.random())).substring(8, 14);
    }
}