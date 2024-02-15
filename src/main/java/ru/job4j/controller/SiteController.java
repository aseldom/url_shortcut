package ru.job4j.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dto.*;
import ru.job4j.service.SiteService;
import ru.job4j.service.UrlService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;

@AllArgsConstructor
@RestController
@RequestMapping("/site")
public class SiteController {

    private final SiteService siteService;
    private final UrlService urlService;

    @PostMapping("/registration")
    public ResponseEntity<RegistrationResponseDTO> registerSite(@Valid @RequestBody RegistrationDTO registrationDTO) {
        return siteService.save(registrationDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping("/convert")
    public ResponseEntity<ResponseUrlDTO> registerUrl(@Valid @RequestBody RegistrationUrlDTO registrationUrlDTO, Principal principal) {
        registrationUrlDTO.setSite(principal.getName());
        return urlService.save(registrationUrlDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping("/redirect/{code}")
    public ResponseEntity<?> getUrl(@PathVariable String code) {
        return urlService.findByHashCode(code)
                .map(url -> ResponseEntity
                        .status(HttpStatus.OK)
                        .header("HTTP CODE - 302 ", url.getUrl()).build())
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());

    }

    @GetMapping("/statistic")
    public ResponseEntity<?> getStatistic(Principal principal) {
        Collection<ResponseStatisticDTO> statisticDTO = urlService.findAllBySite(principal.getName());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(statisticDTO);

    }

}