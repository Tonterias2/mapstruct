package com.mapstruct.one.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EducationMapperTest {

    private EducationMapper educationMapper;

    @BeforeEach
    public void setUp() {
        educationMapper = new EducationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(educationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(educationMapper.fromId(null)).isNull();
    }
}
