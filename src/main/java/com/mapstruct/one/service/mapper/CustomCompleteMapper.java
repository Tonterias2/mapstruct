package com.mapstruct.one.service.mapper;

import com.mapstruct.one.domain.*;

import com.mapstruct.one.service.dto.CustomDoctorDTO;
import com.mapstruct.one.service.dto.DoctorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Doctor} and its DTO {@link DoctorDTO}.
 */
@Mapper(componentModel = "spring", uses = {DoctorMapper.class, EducationMapper.class})
public interface CustomCompleteMapper extends EntityMapper<CustomDoctorDTO, Doctor> {

    // @Mapping(source = "doctor.name", target = "name")
    // @Mapping(source = "education.degreeName", target = "degreeName")
    CustomDoctorDTO toDto(DoctorDTO doctorDTO);

    // @Mapping(target = "degreeName", ignore = true)
    // @Mapping(source = "doctor.name", target = "name")
    // @Mapping(source = "education.degreeName", target = "degreeName")
    Doctor toEntity(CustomDoctorDTO customDoctorDTO);

    default Doctor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Doctor doctor = new Doctor();
        doctor.setId(id);
        return doctor;
    }
}

