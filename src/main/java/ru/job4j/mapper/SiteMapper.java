package ru.job4j.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.job4j.domain.Site;
import ru.job4j.dto.RegistrationDTO;
import ru.job4j.dto.RegistrationResponseDTO;

@Mapper(componentModel = "spring")
public interface SiteMapper {

    @Mapping(target = "registration", constant = "true")
    RegistrationResponseDTO getRegistrationResponseDTO(Site site);

}
