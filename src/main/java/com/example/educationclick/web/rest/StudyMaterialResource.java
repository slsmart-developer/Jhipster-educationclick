package com.example.educationclick.web.rest;

import com.example.educationclick.domain.StudyMaterial;
import com.example.educationclick.repository.StudyMaterialRepository;
import com.example.educationclick.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.example.educationclick.domain.StudyMaterial}.
 */
@RestController
@RequestMapping("/api/study-materials")
@Transactional
public class StudyMaterialResource {

    private final Logger log = LoggerFactory.getLogger(StudyMaterialResource.class);

    private static final String ENTITY_NAME = "studyMaterial";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StudyMaterialRepository studyMaterialRepository;

    public StudyMaterialResource(StudyMaterialRepository studyMaterialRepository) {
        this.studyMaterialRepository = studyMaterialRepository;
    }

    /**
     * {@code POST  /study-materials} : Create a new studyMaterial.
     *
     * @param studyMaterial the studyMaterial to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new studyMaterial, or with status {@code 400 (Bad Request)} if the studyMaterial has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<StudyMaterial> createStudyMaterial(@Valid @RequestBody StudyMaterial studyMaterial) throws URISyntaxException {
        log.debug("REST request to save StudyMaterial : {}", studyMaterial);
        if (studyMaterial.getId() != null) {
            throw new BadRequestAlertException("A new studyMaterial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        studyMaterial = studyMaterialRepository.save(studyMaterial);
        return ResponseEntity.created(new URI("/api/study-materials/" + studyMaterial.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, studyMaterial.getId().toString()))
            .body(studyMaterial);
    }

    /**
     * {@code PUT  /study-materials/:id} : Updates an existing studyMaterial.
     *
     * @param id the id of the studyMaterial to save.
     * @param studyMaterial the studyMaterial to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated studyMaterial,
     * or with status {@code 400 (Bad Request)} if the studyMaterial is not valid,
     * or with status {@code 500 (Internal Server Error)} if the studyMaterial couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<StudyMaterial> updateStudyMaterial(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StudyMaterial studyMaterial
    ) throws URISyntaxException {
        log.debug("REST request to update StudyMaterial : {}, {}", id, studyMaterial);
        if (studyMaterial.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, studyMaterial.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!studyMaterialRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        studyMaterial = studyMaterialRepository.save(studyMaterial);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, studyMaterial.getId().toString()))
            .body(studyMaterial);
    }

    /**
     * {@code PATCH  /study-materials/:id} : Partial updates given fields of an existing studyMaterial, field will ignore if it is null
     *
     * @param id the id of the studyMaterial to save.
     * @param studyMaterial the studyMaterial to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated studyMaterial,
     * or with status {@code 400 (Bad Request)} if the studyMaterial is not valid,
     * or with status {@code 404 (Not Found)} if the studyMaterial is not found,
     * or with status {@code 500 (Internal Server Error)} if the studyMaterial couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StudyMaterial> partialUpdateStudyMaterial(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody StudyMaterial studyMaterial
    ) throws URISyntaxException {
        log.debug("REST request to partial update StudyMaterial partially : {}, {}", id, studyMaterial);
        if (studyMaterial.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, studyMaterial.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!studyMaterialRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StudyMaterial> result = studyMaterialRepository
            .findById(studyMaterial.getId())
            .map(existingStudyMaterial -> {
                if (studyMaterial.getName() != null) {
                    existingStudyMaterial.setName(studyMaterial.getName());
                }
                if (studyMaterial.getDescription() != null) {
                    existingStudyMaterial.setDescription(studyMaterial.getDescription());
                }
                if (studyMaterial.getFile() != null) {
                    existingStudyMaterial.setFile(studyMaterial.getFile());
                }
                if (studyMaterial.getFileContentType() != null) {
                    existingStudyMaterial.setFileContentType(studyMaterial.getFileContentType());
                }
                if (studyMaterial.getUploadDate() != null) {
                    existingStudyMaterial.setUploadDate(studyMaterial.getUploadDate());
                }

                return existingStudyMaterial;
            })
            .map(studyMaterialRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, studyMaterial.getId().toString())
        );
    }

    /**
     * {@code GET  /study-materials} : get all the studyMaterials.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of studyMaterials in body.
     */
    @GetMapping("")
    public ResponseEntity<List<StudyMaterial>> getAllStudyMaterials(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of StudyMaterials");
        Page<StudyMaterial> page;
        if (eagerload) {
            page = studyMaterialRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = studyMaterialRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /study-materials/:id} : get the "id" studyMaterial.
     *
     * @param id the id of the studyMaterial to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the studyMaterial, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<StudyMaterial> getStudyMaterial(@PathVariable("id") Long id) {
        log.debug("REST request to get StudyMaterial : {}", id);
        Optional<StudyMaterial> studyMaterial = studyMaterialRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(studyMaterial);
    }

    /**
     * {@code DELETE  /study-materials/:id} : delete the "id" studyMaterial.
     *
     * @param id the id of the studyMaterial to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudyMaterial(@PathVariable("id") Long id) {
        log.debug("REST request to delete StudyMaterial : {}", id);
        studyMaterialRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
