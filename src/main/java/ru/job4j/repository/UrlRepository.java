package ru.job4j.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.domain.Site;
import ru.job4j.domain.Url;

import java.util.Collection;
import java.util.Optional;

public interface UrlRepository extends CrudRepository<Url, Integer> {

    @Override
    Collection<Url> findAll();

    Optional<Url> findByHashCode(String hash);

    Collection<Url> findAllBySite(Site site);

}