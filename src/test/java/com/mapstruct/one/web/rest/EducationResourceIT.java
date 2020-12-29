package com.mapstruct.one.web.rest;

import com.mapstruct.one.MapstructApp;
import com.mapstruct.one.domain.Education;
import com.mapstruct.one.repository.EducationRepository;
import com.mapstruct.one.service.EducationService;
import com.mapstruct.one.service.dto.EducationDTO;
import com.mapstruct.one.service.mapper.EducationMapper;
import com.mapstruct.one.service.dto.EducationCriteria;
import com.mapstruct.one.service.EducationQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EducationResource} REST controller.
 */
@SpringBootTest(classes = MapstructApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EducationResourceIT {

    private static final String DEFAULT_DEGREE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DEGREE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INSTITUTE = "AAAAAAAAAA";
    private static final String UPDATED_INSTITUTE = "BBBBBBBBBB";

    private static final Integer DEFAULT_YEAR_OF_PASSING = 1;
    private static final Integer UPDATED_YEAR_OF_PASSING = 2;
    private static final Integer SMALLER_YEAR_OF_PASSING = 1 - 1;

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private EducationMapper educationMapper;

    @Autowired
    private EducationService educationService;

    @Autowired
    private EducationQueryService educationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEducationMockMvc;

    private Education education;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Education createEntity(EntityManager em) {
        Education education = new Education()
            .degreeName(DEFAULT_DEGREE_NAME)
            .institute(DEFAULT_INSTITUTE)
            .yearOfPassing(DEFAULT_YEAR_OF_PASSING);
        return education;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Education createUpdatedEntity(EntityManager em) {
        Education education = new Education()
            .degreeName(UPDATED_DEGREE_NAME)
            .institute(UPDATED_INSTITUTE)
            .yearOfPassing(UPDATED_YEAR_OF_PASSING);
        return education;
    }

    @BeforeEach
    public void initTest() {
        education = createEntity(em);
    }

    @Test
    @Transactional
    public void createEducation() throws Exception {
        int databaseSizeBeforeCreate = educationRepository.findAll().size();
        // Create the Education
        EducationDTO educationDTO = educationMapper.toDto(education);
        restEducationMockMvc.perform(post("/api/educations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(educationDTO)))
            .andExpect(status().isCreated());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeCreate + 1);
        Education testEducation = educationList.get(educationList.size() - 1);
        assertThat(testEducation.getDegreeName()).isEqualTo(DEFAULT_DEGREE_NAME);
        assertThat(testEducation.getInstitute()).isEqualTo(DEFAULT_INSTITUTE);
        assertThat(testEducation.getYearOfPassing()).isEqualTo(DEFAULT_YEAR_OF_PASSING);
    }

    @Test
    @Transactional
    public void createEducationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = educationRepository.findAll().size();

        // Create the Education with an existing ID
        education.setId(1L);
        EducationDTO educationDTO = educationMapper.toDto(education);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEducationMockMvc.perform(post("/api/educations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(educationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEducations() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList
        restEducationMockMvc.perform(get("/api/educations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(education.getId().intValue())))
            .andExpect(jsonPath("$.[*].degreeName").value(hasItem(DEFAULT_DEGREE_NAME)))
            .andExpect(jsonPath("$.[*].institute").value(hasItem(DEFAULT_INSTITUTE)))
            .andExpect(jsonPath("$.[*].yearOfPassing").value(hasItem(DEFAULT_YEAR_OF_PASSING)));
    }
    
    @Test
    @Transactional
    public void getEducation() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get the education
        restEducationMockMvc.perform(get("/api/educations/{id}", education.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(education.getId().intValue()))
            .andExpect(jsonPath("$.degreeName").value(DEFAULT_DEGREE_NAME))
            .andExpect(jsonPath("$.institute").value(DEFAULT_INSTITUTE))
            .andExpect(jsonPath("$.yearOfPassing").value(DEFAULT_YEAR_OF_PASSING));
    }


    @Test
    @Transactional
    public void getEducationsByIdFiltering() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        Long id = education.getId();

        defaultEducationShouldBeFound("id.equals=" + id);
        defaultEducationShouldNotBeFound("id.notEquals=" + id);

        defaultEducationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEducationShouldNotBeFound("id.greaterThan=" + id);

        defaultEducationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEducationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEducationsByDegreeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where degreeName equals to DEFAULT_DEGREE_NAME
        defaultEducationShouldBeFound("degreeName.equals=" + DEFAULT_DEGREE_NAME);

        // Get all the educationList where degreeName equals to UPDATED_DEGREE_NAME
        defaultEducationShouldNotBeFound("degreeName.equals=" + UPDATED_DEGREE_NAME);
    }

    @Test
    @Transactional
    public void getAllEducationsByDegreeNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where degreeName not equals to DEFAULT_DEGREE_NAME
        defaultEducationShouldNotBeFound("degreeName.notEquals=" + DEFAULT_DEGREE_NAME);

        // Get all the educationList where degreeName not equals to UPDATED_DEGREE_NAME
        defaultEducationShouldBeFound("degreeName.notEquals=" + UPDATED_DEGREE_NAME);
    }

    @Test
    @Transactional
    public void getAllEducationsByDegreeNameIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where degreeName in DEFAULT_DEGREE_NAME or UPDATED_DEGREE_NAME
        defaultEducationShouldBeFound("degreeName.in=" + DEFAULT_DEGREE_NAME + "," + UPDATED_DEGREE_NAME);

        // Get all the educationList where degreeName equals to UPDATED_DEGREE_NAME
        defaultEducationShouldNotBeFound("degreeName.in=" + UPDATED_DEGREE_NAME);
    }

    @Test
    @Transactional
    public void getAllEducationsByDegreeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where degreeName is not null
        defaultEducationShouldBeFound("degreeName.specified=true");

        // Get all the educationList where degreeName is null
        defaultEducationShouldNotBeFound("degreeName.specified=false");
    }
                @Test
    @Transactional
    public void getAllEducationsByDegreeNameContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where degreeName contains DEFAULT_DEGREE_NAME
        defaultEducationShouldBeFound("degreeName.contains=" + DEFAULT_DEGREE_NAME);

        // Get all the educationList where degreeName contains UPDATED_DEGREE_NAME
        defaultEducationShouldNotBeFound("degreeName.contains=" + UPDATED_DEGREE_NAME);
    }

    @Test
    @Transactional
    public void getAllEducationsByDegreeNameNotContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where degreeName does not contain DEFAULT_DEGREE_NAME
        defaultEducationShouldNotBeFound("degreeName.doesNotContain=" + DEFAULT_DEGREE_NAME);

        // Get all the educationList where degreeName does not contain UPDATED_DEGREE_NAME
        defaultEducationShouldBeFound("degreeName.doesNotContain=" + UPDATED_DEGREE_NAME);
    }


    @Test
    @Transactional
    public void getAllEducationsByInstituteIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where institute equals to DEFAULT_INSTITUTE
        defaultEducationShouldBeFound("institute.equals=" + DEFAULT_INSTITUTE);

        // Get all the educationList where institute equals to UPDATED_INSTITUTE
        defaultEducationShouldNotBeFound("institute.equals=" + UPDATED_INSTITUTE);
    }

    @Test
    @Transactional
    public void getAllEducationsByInstituteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where institute not equals to DEFAULT_INSTITUTE
        defaultEducationShouldNotBeFound("institute.notEquals=" + DEFAULT_INSTITUTE);

        // Get all the educationList where institute not equals to UPDATED_INSTITUTE
        defaultEducationShouldBeFound("institute.notEquals=" + UPDATED_INSTITUTE);
    }

    @Test
    @Transactional
    public void getAllEducationsByInstituteIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where institute in DEFAULT_INSTITUTE or UPDATED_INSTITUTE
        defaultEducationShouldBeFound("institute.in=" + DEFAULT_INSTITUTE + "," + UPDATED_INSTITUTE);

        // Get all the educationList where institute equals to UPDATED_INSTITUTE
        defaultEducationShouldNotBeFound("institute.in=" + UPDATED_INSTITUTE);
    }

    @Test
    @Transactional
    public void getAllEducationsByInstituteIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where institute is not null
        defaultEducationShouldBeFound("institute.specified=true");

        // Get all the educationList where institute is null
        defaultEducationShouldNotBeFound("institute.specified=false");
    }
                @Test
    @Transactional
    public void getAllEducationsByInstituteContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where institute contains DEFAULT_INSTITUTE
        defaultEducationShouldBeFound("institute.contains=" + DEFAULT_INSTITUTE);

        // Get all the educationList where institute contains UPDATED_INSTITUTE
        defaultEducationShouldNotBeFound("institute.contains=" + UPDATED_INSTITUTE);
    }

    @Test
    @Transactional
    public void getAllEducationsByInstituteNotContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where institute does not contain DEFAULT_INSTITUTE
        defaultEducationShouldNotBeFound("institute.doesNotContain=" + DEFAULT_INSTITUTE);

        // Get all the educationList where institute does not contain UPDATED_INSTITUTE
        defaultEducationShouldBeFound("institute.doesNotContain=" + UPDATED_INSTITUTE);
    }


    @Test
    @Transactional
    public void getAllEducationsByYearOfPassingIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where yearOfPassing equals to DEFAULT_YEAR_OF_PASSING
        defaultEducationShouldBeFound("yearOfPassing.equals=" + DEFAULT_YEAR_OF_PASSING);

        // Get all the educationList where yearOfPassing equals to UPDATED_YEAR_OF_PASSING
        defaultEducationShouldNotBeFound("yearOfPassing.equals=" + UPDATED_YEAR_OF_PASSING);
    }

    @Test
    @Transactional
    public void getAllEducationsByYearOfPassingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where yearOfPassing not equals to DEFAULT_YEAR_OF_PASSING
        defaultEducationShouldNotBeFound("yearOfPassing.notEquals=" + DEFAULT_YEAR_OF_PASSING);

        // Get all the educationList where yearOfPassing not equals to UPDATED_YEAR_OF_PASSING
        defaultEducationShouldBeFound("yearOfPassing.notEquals=" + UPDATED_YEAR_OF_PASSING);
    }

    @Test
    @Transactional
    public void getAllEducationsByYearOfPassingIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where yearOfPassing in DEFAULT_YEAR_OF_PASSING or UPDATED_YEAR_OF_PASSING
        defaultEducationShouldBeFound("yearOfPassing.in=" + DEFAULT_YEAR_OF_PASSING + "," + UPDATED_YEAR_OF_PASSING);

        // Get all the educationList where yearOfPassing equals to UPDATED_YEAR_OF_PASSING
        defaultEducationShouldNotBeFound("yearOfPassing.in=" + UPDATED_YEAR_OF_PASSING);
    }

    @Test
    @Transactional
    public void getAllEducationsByYearOfPassingIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where yearOfPassing is not null
        defaultEducationShouldBeFound("yearOfPassing.specified=true");

        // Get all the educationList where yearOfPassing is null
        defaultEducationShouldNotBeFound("yearOfPassing.specified=false");
    }

    @Test
    @Transactional
    public void getAllEducationsByYearOfPassingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where yearOfPassing is greater than or equal to DEFAULT_YEAR_OF_PASSING
        defaultEducationShouldBeFound("yearOfPassing.greaterThanOrEqual=" + DEFAULT_YEAR_OF_PASSING);

        // Get all the educationList where yearOfPassing is greater than or equal to UPDATED_YEAR_OF_PASSING
        defaultEducationShouldNotBeFound("yearOfPassing.greaterThanOrEqual=" + UPDATED_YEAR_OF_PASSING);
    }

    @Test
    @Transactional
    public void getAllEducationsByYearOfPassingIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where yearOfPassing is less than or equal to DEFAULT_YEAR_OF_PASSING
        defaultEducationShouldBeFound("yearOfPassing.lessThanOrEqual=" + DEFAULT_YEAR_OF_PASSING);

        // Get all the educationList where yearOfPassing is less than or equal to SMALLER_YEAR_OF_PASSING
        defaultEducationShouldNotBeFound("yearOfPassing.lessThanOrEqual=" + SMALLER_YEAR_OF_PASSING);
    }

    @Test
    @Transactional
    public void getAllEducationsByYearOfPassingIsLessThanSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where yearOfPassing is less than DEFAULT_YEAR_OF_PASSING
        defaultEducationShouldNotBeFound("yearOfPassing.lessThan=" + DEFAULT_YEAR_OF_PASSING);

        // Get all the educationList where yearOfPassing is less than UPDATED_YEAR_OF_PASSING
        defaultEducationShouldBeFound("yearOfPassing.lessThan=" + UPDATED_YEAR_OF_PASSING);
    }

    @Test
    @Transactional
    public void getAllEducationsByYearOfPassingIsGreaterThanSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where yearOfPassing is greater than DEFAULT_YEAR_OF_PASSING
        defaultEducationShouldNotBeFound("yearOfPassing.greaterThan=" + DEFAULT_YEAR_OF_PASSING);

        // Get all the educationList where yearOfPassing is greater than SMALLER_YEAR_OF_PASSING
        defaultEducationShouldBeFound("yearOfPassing.greaterThan=" + SMALLER_YEAR_OF_PASSING);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEducationShouldBeFound(String filter) throws Exception {
        restEducationMockMvc.perform(get("/api/educations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(education.getId().intValue())))
            .andExpect(jsonPath("$.[*].degreeName").value(hasItem(DEFAULT_DEGREE_NAME)))
            .andExpect(jsonPath("$.[*].institute").value(hasItem(DEFAULT_INSTITUTE)))
            .andExpect(jsonPath("$.[*].yearOfPassing").value(hasItem(DEFAULT_YEAR_OF_PASSING)));

        // Check, that the count call also returns 1
        restEducationMockMvc.perform(get("/api/educations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEducationShouldNotBeFound(String filter) throws Exception {
        restEducationMockMvc.perform(get("/api/educations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEducationMockMvc.perform(get("/api/educations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEducation() throws Exception {
        // Get the education
        restEducationMockMvc.perform(get("/api/educations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEducation() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        int databaseSizeBeforeUpdate = educationRepository.findAll().size();

        // Update the education
        Education updatedEducation = educationRepository.findById(education.getId()).get();
        // Disconnect from session so that the updates on updatedEducation are not directly saved in db
        em.detach(updatedEducation);
        updatedEducation
            .degreeName(UPDATED_DEGREE_NAME)
            .institute(UPDATED_INSTITUTE)
            .yearOfPassing(UPDATED_YEAR_OF_PASSING);
        EducationDTO educationDTO = educationMapper.toDto(updatedEducation);

        restEducationMockMvc.perform(put("/api/educations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(educationDTO)))
            .andExpect(status().isOk());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
        Education testEducation = educationList.get(educationList.size() - 1);
        assertThat(testEducation.getDegreeName()).isEqualTo(UPDATED_DEGREE_NAME);
        assertThat(testEducation.getInstitute()).isEqualTo(UPDATED_INSTITUTE);
        assertThat(testEducation.getYearOfPassing()).isEqualTo(UPDATED_YEAR_OF_PASSING);
    }

    @Test
    @Transactional
    public void updateNonExistingEducation() throws Exception {
        int databaseSizeBeforeUpdate = educationRepository.findAll().size();

        // Create the Education
        EducationDTO educationDTO = educationMapper.toDto(education);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEducationMockMvc.perform(put("/api/educations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(educationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEducation() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        int databaseSizeBeforeDelete = educationRepository.findAll().size();

        // Delete the education
        restEducationMockMvc.perform(delete("/api/educations/{id}", education.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
