package ru.job4j.controller;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.dto.RegistrationDTO;
import ru.job4j.dto.RegistrationResponseDTO;
import ru.job4j.exception.ControllerException;
import ru.job4j.exception.ServiceException;
import ru.job4j.service.SiteService;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/site")
public class SiteController {

    private final SiteService siteService;

    @PostMapping("/registration")
    public ResponseEntity<RegistrationResponseDTO> registerSite(@Valid @RequestBody RegistrationDTO registrationDTO) throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    siteService.save(registrationDTO)
            );
        } catch (ServiceException e) {
            if (e.getCause() instanceof DataIntegrityViolationException) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new RegistrationResponseDTO(registrationDTO.getSite()));
            }
            throw new ControllerException("can't save site", e);
        }
    }

}