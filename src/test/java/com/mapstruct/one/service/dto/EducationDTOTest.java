package com.mapstruct.one.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mapstruct.one.web.rest.TestUtil;

public class EducationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EducationDTO.class);
        EducationDTO educationDTO1 = new EducationDTO();
        educationDTO1.setId(1L);
        EducationDTO educationDTO2 = new EducationDTO();
        assertThat(educationDTO1).isNotEqualTo(educationDTO2);
        educationDTO2.setId(educationDTO1.getId());
        assertThat(educationDTO1).isEqualTo(educationDTO2);
        educationDTO2.setId(2L);
        assertThat(educationDTO1).isNotEqualTo(educationDTO2);
        educationDTO1.setId(null);
        assertThat(educationDTO1).isNotEqualTo(educationDTO2);
    }
}
