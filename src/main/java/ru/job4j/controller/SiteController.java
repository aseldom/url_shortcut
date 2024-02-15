package ru.job4j.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dto.RegistrationDTO;
import ru.job4j.dto.RegistrationResponseDTO;
import ru.job4j.dto.RegistrationUrlDTO;
import ru.job4j.dto.ResponseUrlDTO;
import ru.job4j.service.SiteService;
import ru.job4j.service.UrlService;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/site")
public class SiteController {

    private final SiteService siteService;
    private final UrlService urlService;

    @PostMapping("/registration")
    public ResponseEntity<RegistrationResponseDTO> registerSite(@RequestBody @Valid RegistrationDTO registrationDTO) {
        return siteService.save(registrationDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping("/convert")
    public ResponseEntity<ResponseUrlDTO> registerUrl(@RequestBody @Valid RegistrationUrlDTO registrationUrlDTO) {
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

}