package ru.job4j.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.domain.Site;
import ru.job4j.repository.SiteRepository;

import java.util.Optional;

import static java.util.Collections.emptyList;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private SiteRepository siteRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Site> optionalSite = siteRepository.findByLogin(login);
        if (optionalSite.isEmpty()) {
            throw new UsernameNotFoundException(login);
        }
        Site site = optionalSite.get();
        return new User(site.getLogin(), site.getPassword(), emptyList());
    }

}