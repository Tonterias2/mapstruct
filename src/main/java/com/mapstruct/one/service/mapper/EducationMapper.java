package com.mapstruct.one.service.mapper;


import com.mapstruct.one.domain.*;
import com.mapstruct.one.service.dto.EducationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Education} and its DTO {@link EducationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EducationMapper extends EntityMapper<EducationDTO, Education> {



    default Education fromId(Long id) {
        if (id == null) {
            return null;
        }
        Education education = new Education();
        education.setId(id);
        return education;
    }
}
