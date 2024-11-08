package com.example.educationclick.web.rest;

import com.example.educationclick.domain.Timeslot;
import com.example.educationclick.repository.TimeslotRepository;
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
 * REST controller for managing {@link com.example.educationclick.domain.Timeslot}.
 */
@RestController
@RequestMapping("/api/timeslots")
@Transactional
public class TimeslotResource {

    private final Logger log = LoggerFactory.getLogger(TimeslotResource.class);

    private static final String ENTITY_NAME = "timeslot";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TimeslotRepository timeslotRepository;

    public TimeslotResource(TimeslotRepository timeslotRepository) {
        this.timeslotRepository = timeslotRepository;
    }

    /**
     * {@code POST  /timeslots} : Create a new timeslot.
     *
     * @param timeslot the timeslot to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new timeslot, or with status {@code 400 (Bad Request)} if the timeslot has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Timeslot> createTimeslot(@Valid @RequestBody Timeslot timeslot) throws URISyntaxException {
        log.debug("REST request to save Timeslot : {}", timeslot);
        if (timeslot.getId() != null) {
            throw new BadRequestAlertException("A new timeslot cannot already have an ID", ENTITY_NAME, "idexists");
        }
        timeslot = timeslotRepository.save(timeslot);
        return ResponseEntity.created(new URI("/api/timeslots/" + timeslot.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, timeslot.getId().toString()))
            .body(timeslot);
    }

    /**
     * {@code PUT  /timeslots/:id} : Updates an existing timeslot.
     *
     * @param id the id of the timeslot to save.
     * @param timeslot the timeslot to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated timeslot,
     * or with status {@code 400 (Bad Request)} if the timeslot is not valid,
     * or with status {@code 500 (Internal Server Error)} if the timeslot couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Timeslot> updateTimeslot(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Timeslot timeslot
    ) throws URISyntaxException {
        log.debug("REST request to update Timeslot : {}, {}", id, timeslot);
        if (timeslot.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, timeslot.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!timeslotRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        timeslot = timeslotRepository.save(timeslot);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, timeslot.getId().toString()))
            .body(timeslot);
    }

    /**
     * {@code PATCH  /timeslots/:id} : Partial updates given fields of an existing timeslot, field will ignore if it is null
     *
     * @param id the id of the timeslot to save.
     * @param timeslot the timeslot to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated timeslot,
     * or with status {@code 400 (Bad Request)} if the timeslot is not valid,
     * or with status {@code 404 (Not Found)} if the timeslot is not found,
     * or with status {@code 500 (Internal Server Error)} if the timeslot couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Timeslot> partialUpdateTimeslot(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Timeslot timeslot
    ) throws URISyntaxException {
        log.debug("REST request to partial update Timeslot partially : {}, {}", id, timeslot);
        if (timeslot.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, timeslot.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!timeslotRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Timeslot> result = timeslotRepository
            .findById(timeslot.getId())
            .map(existingTimeslot -> {
                if (timeslot.getStartTime() != null) {
                    existingTimeslot.setStartTime(timeslot.getStartTime());
                }
                if (timeslot.getEndTime() != null) {
                    existingTimeslot.setEndTime(timeslot.getEndTime());
                }
                if (timeslot.getAvailability() != null) {
                    existingTimeslot.setAvailability(timeslot.getAvailability());
                }

                return existingTimeslot;
            })
            .map(timeslotRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, timeslot.getId().toString())
        );
    }

    /**
     * {@code GET  /timeslots} : get all the timeslots.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of timeslots in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Timeslot>> getAllTimeslots(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Timeslots");
        Page<Timeslot> page = timeslotRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /timeslots/:id} : get the "id" timeslot.
     *
     * @param id the id of the timeslot to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the timeslot, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Timeslot> getTimeslot(@PathVariable("id") Long id) {
        log.debug("REST request to get Timeslot : {}", id);
        Optional<Timeslot> timeslot = timeslotRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(timeslot);
    }

    /**
     * {@code DELETE  /timeslots/:id} : delete the "id" timeslot.
     *
     * @param id the id of the timeslot to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimeslot(@PathVariable("id") Long id) {
        log.debug("REST request to delete Timeslot : {}", id);
        timeslotRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
