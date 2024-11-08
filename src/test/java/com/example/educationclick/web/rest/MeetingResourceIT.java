package com.example.educationclick.web.rest;

import static com.example.educationclick.domain.MeetingAsserts.*;
import static com.example.educationclick.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.educationclick.IntegrationTest;
import com.example.educationclick.domain.Meeting;
import com.example.educationclick.repository.MeetingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
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
 * Integration tests for the {@link MeetingResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class MeetingResourceIT {

    private static final String DEFAULT_MEETING_LINK = "AAAAAAAAAA";
    private static final String UPDATED_MEETING_LINK = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/meetings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MeetingRepository meetingRepository;

    @Mock
    private MeetingRepository meetingRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMeetingMockMvc;

    private Meeting meeting;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Meeting createEntity(EntityManager em) {
        Meeting meeting = new Meeting().meetingLink(DEFAULT_MEETING_LINK);
        return meeting;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Meeting createUpdatedEntity(EntityManager em) {
        Meeting meeting = new Meeting().meetingLink(UPDATED_MEETING_LINK);
        return meeting;
    }

    @BeforeEach
    public void initTest() {
        meeting = createEntity(em);
    }

    @Test
    @Transactional
    void createMeeting() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Meeting
        var returnedMeeting = om.readValue(
            restMeetingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(meeting)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Meeting.class
        );

        // Validate the Meeting in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertMeetingUpdatableFieldsEquals(returnedMeeting, getPersistedMeeting(returnedMeeting));
    }

    @Test
    @Transactional
    void createMeetingWithExistingId() throws Exception {
        // Create the Meeting with an existing ID
        meeting.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMeetingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(meeting)))
            .andExpect(status().isBadRequest());

        // Validate the Meeting in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMeetingLinkIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        meeting.setMeetingLink(null);

        // Create the Meeting, which fails.

        restMeetingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(meeting)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMeetings() throws Exception {
        // Initialize the database
        meetingRepository.saveAndFlush(meeting);

        // Get all the meetingList
        restMeetingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meeting.getId().intValue())))
            .andExpect(jsonPath("$.[*].meetingLink").value(hasItem(DEFAULT_MEETING_LINK)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMeetingsWithEagerRelationshipsIsEnabled() throws Exception {
        when(meetingRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMeetingMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(meetingRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMeetingsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(meetingRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMeetingMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(meetingRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getMeeting() throws Exception {
        // Initialize the database
        meetingRepository.saveAndFlush(meeting);

        // Get the meeting
        restMeetingMockMvc
            .perform(get(ENTITY_API_URL_ID, meeting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(meeting.getId().intValue()))
            .andExpect(jsonPath("$.meetingLink").value(DEFAULT_MEETING_LINK));
    }

    @Test
    @Transactional
    void getNonExistingMeeting() throws Exception {
        // Get the meeting
        restMeetingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMeeting() throws Exception {
        // Initialize the database
        meetingRepository.saveAndFlush(meeting);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the meeting
        Meeting updatedMeeting = meetingRepository.findById(meeting.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMeeting are not directly saved in db
        em.detach(updatedMeeting);
        updatedMeeting.meetingLink(UPDATED_MEETING_LINK);

        restMeetingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMeeting.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedMeeting))
            )
            .andExpect(status().isOk());

        // Validate the Meeting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMeetingToMatchAllProperties(updatedMeeting);
    }

    @Test
    @Transactional
    void putNonExistingMeeting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        meeting.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMeetingMockMvc
            .perform(put(ENTITY_API_URL_ID, meeting.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(meeting)))
            .andExpect(status().isBadRequest());

        // Validate the Meeting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMeeting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        meeting.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMeetingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(meeting))
            )
            .andExpect(status().isBadRequest());

        // Validate the Meeting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMeeting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        meeting.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMeetingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(meeting)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Meeting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMeetingWithPatch() throws Exception {
        // Initialize the database
        meetingRepository.saveAndFlush(meeting);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the meeting using partial update
        Meeting partialUpdatedMeeting = new Meeting();
        partialUpdatedMeeting.setId(meeting.getId());

        restMeetingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMeeting.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMeeting))
            )
            .andExpect(status().isOk());

        // Validate the Meeting in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMeetingUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedMeeting, meeting), getPersistedMeeting(meeting));
    }

    @Test
    @Transactional
    void fullUpdateMeetingWithPatch() throws Exception {
        // Initialize the database
        meetingRepository.saveAndFlush(meeting);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the meeting using partial update
        Meeting partialUpdatedMeeting = new Meeting();
        partialUpdatedMeeting.setId(meeting.getId());

        partialUpdatedMeeting.meetingLink(UPDATED_MEETING_LINK);

        restMeetingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMeeting.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMeeting))
            )
            .andExpect(status().isOk());

        // Validate the Meeting in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMeetingUpdatableFieldsEquals(partialUpdatedMeeting, getPersistedMeeting(partialUpdatedMeeting));
    }

    @Test
    @Transactional
    void patchNonExistingMeeting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        meeting.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMeetingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, meeting.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(meeting))
            )
            .andExpect(status().isBadRequest());

        // Validate the Meeting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMeeting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        meeting.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMeetingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(meeting))
            )
            .andExpect(status().isBadRequest());

        // Validate the Meeting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMeeting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        meeting.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMeetingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(meeting)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Meeting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMeeting() throws Exception {
        // Initialize the database
        meetingRepository.saveAndFlush(meeting);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the meeting
        restMeetingMockMvc
            .perform(delete(ENTITY_API_URL_ID, meeting.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return meetingRepository.count();
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

    protected Meeting getPersistedMeeting(Meeting meeting) {
        return meetingRepository.findById(meeting.getId()).orElseThrow();
    }

    protected void assertPersistedMeetingToMatchAllProperties(Meeting expectedMeeting) {
        assertMeetingAllPropertiesEquals(expectedMeeting, getPersistedMeeting(expectedMeeting));
    }

    protected void assertPersistedMeetingToMatchUpdatableProperties(Meeting expectedMeeting) {
        assertMeetingAllUpdatablePropertiesEquals(expectedMeeting, getPersistedMeeting(expectedMeeting));
    }
}
