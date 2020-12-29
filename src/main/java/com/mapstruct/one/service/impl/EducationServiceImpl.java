package com.mapstruct.one.service.impl;

import com.mapstruct.one.service.EducationService;
import com.mapstruct.one.domain.Education;
import com.mapstruct.one.repository.EducationRepository;
import com.mapstruct.one.service.dto.EducationDTO;
import com.mapstruct.one.service.mapper.EducationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Education}.
 */
@Service
@Transactional
public class EducationServiceImpl implements EducationService {

    private final Logger log = LoggerFactory.getLogger(EducationServiceImpl.class);

    private final EducationRepository educationRepository;

    private final EducationMapper educationMapper;

    public EducationServiceImpl(EducationRepository educationRepository, EducationMapper educationMapper) {
        this.educationRepository = educationRepository;
        this.educationMapper = educationMapper;
    }

    @Override
    public EducationDTO save(EducationDTO educationDTO) {
        log.debug("Request to save Education : {}", educationDTO);
        Education education = educationMapper.toEntity(educationDTO);
        education = educationRepository.save(education);
        return educationMapper.toDto(education);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EducationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Educations");
        return educationRepository.findAll(pageable)
            .map(educationMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<EducationDTO> findOne(Long id) {
        log.debug("Request to get Education : {}", id);
        return educationRepository.findById(id)
            .map(educationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Education : {}", id);
        educationRepository.deleteById(id);
    }
}
