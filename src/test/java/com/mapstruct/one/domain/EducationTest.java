package com.mapstruct.one.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mapstruct.one.web.rest.TestUtil;

public class EducationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Education.class);
        Education education1 = new Education();
        education1.setId(1L);
        Education education2 = new Education();
        education2.setId(education1.getId());
        assertThat(education1).isEqualTo(education2);
        education2.setId(2L);
        assertThat(education1).isNotEqualTo(education2);
        education1.setId(null);
        assertThat(education1).isNotEqualTo(education2);
    }
}
