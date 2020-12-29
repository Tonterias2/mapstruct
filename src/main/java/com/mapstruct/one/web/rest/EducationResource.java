package com.mapstruct.one.web.rest;

import com.mapstruct.one.service.EducationService;
import com.mapstruct.one.web.rest.errors.BadRequestAlertException;
import com.mapstruct.one.service.dto.EducationDTO;
import com.mapstruct.one.service.dto.EducationCriteria;
import com.mapstruct.one.service.EducationQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mapstruct.one.domain.Education}.
 */
@RestController
@RequestMapping("/api")
public class EducationResource {

    private final Logger log = LoggerFactory.getLogger(EducationResource.class);

    private static final String ENTITY_NAME = "education";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EducationService educationService;

    private final EducationQueryService educationQueryService;

    public EducationResource(EducationService educationService, EducationQueryService educationQueryService) {
        this.educationService = educationService;
        this.educationQueryService = educationQueryService;
    }

    /**
     * {@code POST  /educations} : Create a new education.
     *
     * @param educationDTO the educationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new educationDTO, or with status {@code 400 (Bad Request)} if the education has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/educations")
    public ResponseEntity<EducationDTO> createEducation(@RequestBody EducationDTO educationDTO) throws URISyntaxException {
        log.debug("REST request to save Education : {}", educationDTO);
        if (educationDTO.getId() != null) {
            throw new BadRequestAlertException("A new education cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EducationDTO result = educationService.save(educationDTO);
        return ResponseEntity.created(new URI("/api/educations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /educations} : Updates an existing education.
     *
     * @param educationDTO the educationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated educationDTO,
     * or with status {@code 400 (Bad Request)} if the educationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the educationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/educations")
    public ResponseEntity<EducationDTO> updateEducation(@RequestBody EducationDTO educationDTO) throws URISyntaxException {
        log.debug("REST request to update Education : {}", educationDTO);
        if (educationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EducationDTO result = educationService.save(educationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, educationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /educations} : get all the educations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of educations in body.
     */
    @GetMapping("/educations")
    public ResponseEntity<List<EducationDTO>> getAllEducations(EducationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Educations by criteria: {}", criteria);
        Page<EducationDTO> page = educationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /educations/count} : count all the educations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/educations/count")
    public ResponseEntity<Long> countEducations(EducationCriteria criteria) {
        log.debug("REST request to count Educations by criteria: {}", criteria);
        return ResponseEntity.ok().body(educationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /educations/:id} : get the "id" education.
     *
     * @param id the id of the educationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the educationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/educations/{id}")
    public ResponseEntity<EducationDTO> getEducation(@PathVariable Long id) {
        log.debug("REST request to get Education : {}", id);
        Optional<EducationDTO> educationDTO = educationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(educationDTO);
    }

    /**
     * {@code DELETE  /educations/:id} : delete the "id" education.
     *
     * @param id the id of the educationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/educations/{id}")
    public ResponseEntity<Void> deleteEducation(@PathVariable Long id) {
        log.debug("REST request to delete Education : {}", id);
        educationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
