package ru.job4j.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.job4j.domain.Site;
import ru.job4j.domain.Url;
import ru.job4j.dto.RegistrationResponseDTO;
import ru.job4j.dto.ResponseStatisticDTO;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface SiteMapper {

    @Mapping(target = "registration", constant = "true")
    RegistrationResponseDTO getRegistrationResponseDTO(Site site);

    Collection<ResponseStatisticDTO> getResponseStatisticDTO(Collection<Url> urls);

}
