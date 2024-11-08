package com.example.educationclick.web.rest;

import static com.example.educationclick.domain.StudyMaterialAsserts.*;
import static com.example.educationclick.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.educationclick.IntegrationTest;
import com.example.educationclick.domain.StudyMaterial;
import com.example.educationclick.repository.StudyMaterialRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link StudyMaterialResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class StudyMaterialResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_UPLOAD_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPLOAD_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/study-materials";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private StudyMaterialRepository studyMaterialRepository;

    @Mock
    private StudyMaterialRepository studyMaterialRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStudyMaterialMockMvc;

    private StudyMaterial studyMaterial;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudyMaterial createEntity(EntityManager em) {
        StudyMaterial studyMaterial = new StudyMaterial()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE)
            .uploadDate(DEFAULT_UPLOAD_DATE);
        return studyMaterial;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudyMaterial createUpdatedEntity(EntityManager em) {
        StudyMaterial studyMaterial = new StudyMaterial()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .uploadDate(UPDATED_UPLOAD_DATE);
        return studyMaterial;
    }

    @BeforeEach
    public void initTest() {
        studyMaterial = createEntity(em);
    }

    @Test
    @Transactional
    void createStudyMaterial() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the StudyMaterial
        var returnedStudyMaterial = om.readValue(
            restStudyMaterialMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(studyMaterial)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            StudyMaterial.class
        );

        // Validate the StudyMaterial in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertStudyMaterialUpdatableFieldsEquals(returnedStudyMaterial, getPersistedStudyMaterial(returnedStudyMaterial));
    }

    @Test
    @Transactional
    void createStudyMaterialWithExistingId() throws Exception {
        // Create the StudyMaterial with an existing ID
        studyMaterial.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudyMaterialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(studyMaterial)))
            .andExpect(status().isBadRequest());

        // Validate the StudyMaterial in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        studyMaterial.setName(null);

        // Create the StudyMaterial, which fails.

        restStudyMaterialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(studyMaterial)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUploadDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        studyMaterial.setUploadDate(null);

        // Create the StudyMaterial, which fails.

        restStudyMaterialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(studyMaterial)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStudyMaterials() throws Exception {
        // Initialize the database
        studyMaterialRepository.saveAndFlush(studyMaterial);

        // Get all the studyMaterialList
        restStudyMaterialMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studyMaterial.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_FILE))))
            .andExpect(jsonPath("$.[*].uploadDate").value(hasItem(DEFAULT_UPLOAD_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllStudyMaterialsWithEagerRelationshipsIsEnabled() throws Exception {
        when(studyMaterialRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStudyMaterialMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(studyMaterialRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllStudyMaterialsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(studyMaterialRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStudyMaterialMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(studyMaterialRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getStudyMaterial() throws Exception {
        // Initialize the database
        studyMaterialRepository.saveAndFlush(studyMaterial);

        // Get the studyMaterial
        restStudyMaterialMockMvc
            .perform(get(ENTITY_API_URL_ID, studyMaterial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(studyMaterial.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64.getEncoder().encodeToString(DEFAULT_FILE)))
            .andExpect(jsonPath("$.uploadDate").value(DEFAULT_UPLOAD_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingStudyMaterial() throws Exception {
        // Get the studyMaterial
        restStudyMaterialMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStudyMaterial() throws Exception {
        // Initialize the database
        studyMaterialRepository.saveAndFlush(studyMaterial);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the studyMaterial
        StudyMaterial updatedStudyMaterial = studyMaterialRepository.findById(studyMaterial.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedStudyMaterial are not directly saved in db
        em.detach(updatedStudyMaterial);
        updatedStudyMaterial
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .uploadDate(UPDATED_UPLOAD_DATE);

        restStudyMaterialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStudyMaterial.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedStudyMaterial))
            )
            .andExpect(status().isOk());

        // Validate the StudyMaterial in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedStudyMaterialToMatchAllProperties(updatedStudyMaterial);
    }

    @Test
    @Transactional
    void putNonExistingStudyMaterial() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        studyMaterial.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudyMaterialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, studyMaterial.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(studyMaterial))
            )
            .andExpect(status().isBadRequest());

        // Validate the StudyMaterial in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStudyMaterial() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        studyMaterial.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudyMaterialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(studyMaterial))
            )
            .andExpect(status().isBadRequest());

        // Validate the StudyMaterial in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStudyMaterial() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        studyMaterial.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudyMaterialMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(studyMaterial)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the StudyMaterial in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStudyMaterialWithPatch() throws Exception {
        // Initialize the database
        studyMaterialRepository.saveAndFlush(studyMaterial);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the studyMaterial using partial update
        StudyMaterial partialUpdatedStudyMaterial = new StudyMaterial();
        partialUpdatedStudyMaterial.setId(studyMaterial.getId());

        partialUpdatedStudyMaterial
            .description(UPDATED_DESCRIPTION)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .uploadDate(UPDATED_UPLOAD_DATE);

        restStudyMaterialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStudyMaterial.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStudyMaterial))
            )
            .andExpect(status().isOk());

        // Validate the StudyMaterial in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStudyMaterialUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedStudyMaterial, studyMaterial),
            getPersistedStudyMaterial(studyMaterial)
        );
    }

    @Test
    @Transactional
    void fullUpdateStudyMaterialWithPatch() throws Exception {
        // Initialize the database
        studyMaterialRepository.saveAndFlush(studyMaterial);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the studyMaterial using partial update
        StudyMaterial partialUpdatedStudyMaterial = new StudyMaterial();
        partialUpdatedStudyMaterial.setId(studyMaterial.getId());

        partialUpdatedStudyMaterial
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .uploadDate(UPDATED_UPLOAD_DATE);

        restStudyMaterialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStudyMaterial.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStudyMaterial))
            )
            .andExpect(status().isOk());

        // Validate the StudyMaterial in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStudyMaterialUpdatableFieldsEquals(partialUpdatedStudyMaterial, getPersistedStudyMaterial(partialUpdatedStudyMaterial));
    }

    @Test
    @Transactional
    void patchNonExistingStudyMaterial() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        studyMaterial.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudyMaterialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, studyMaterial.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(studyMaterial))
            )
            .andExpect(status().isBadRequest());

        // Validate the StudyMaterial in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStudyMaterial() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        studyMaterial.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudyMaterialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(studyMaterial))
            )
            .andExpect(status().isBadRequest());

        // Validate the StudyMaterial in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStudyMaterial() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        studyMaterial.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudyMaterialMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(studyMaterial)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the StudyMaterial in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStudyMaterial() throws Exception {
        // Initialize the database
        studyMaterialRepository.saveAndFlush(studyMaterial);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the studyMaterial
        restStudyMaterialMockMvc
            .perform(delete(ENTITY_API_URL_ID, studyMaterial.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return studyMaterialRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected StudyMaterial getPersistedStudyMaterial(StudyMaterial studyMaterial) {
        return studyMaterialRepository.findById(studyMaterial.getId()).orElseThrow();
    }

    protected void assertPersistedStudyMaterialToMatchAllProperties(StudyMaterial expectedStudyMaterial) {
        assertStudyMaterialAllPropertiesEquals(expectedStudyMaterial, getPersistedStudyMaterial(expectedStudyMaterial));
    }

    protected void assertPersistedStudyMaterialToMatchUpdatableProperties(StudyMaterial expectedStudyMaterial) {
        assertStudyMaterialAllUpdatablePropertiesEquals(expectedStudyMaterial, getPersistedStudyMaterial(expectedStudyMaterial));
    }
}
