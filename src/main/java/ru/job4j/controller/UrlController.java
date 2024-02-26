package ru.job4j.controller;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.dto.RegistrationUrlDTO;
import ru.job4j.dto.ResponseStatisticDTO;
import ru.job4j.dto.ResponseUrlDTO;
import ru.job4j.exception.ControllerException;
import ru.job4j.exception.ServiceException;
import ru.job4j.service.UrlService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;

@AllArgsConstructor
@RestController
@RequestMapping("/site")
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/convert")
    public ResponseEntity<ResponseUrlDTO> registerUrl(@Valid @RequestBody RegistrationUrlDTO registrationUrlDTO, Principal principal) throws ControllerException {
        registrationUrlDTO.setSite(principal.getName());
        try {
            ResponseUrlDTO responseUrlDTO = urlService.save(registrationUrlDTO);
            return ResponseEntity.ok(responseUrlDTO);
        } catch (ServiceException e) {
            if (e.getCause() instanceof DataIntegrityViolationException) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "URL already has been in base.");
            }
            throw new ControllerException("can't convert URL", e);
        }
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