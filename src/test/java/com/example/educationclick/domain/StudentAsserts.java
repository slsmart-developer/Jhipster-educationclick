package com.example.educationclick.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class StudentAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStudentAllPropertiesEquals(Student expected, Student actual) {
        assertStudentAutoGeneratedPropertiesEquals(expected, actual);
        assertStudentAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStudentAllUpdatablePropertiesEquals(Student expected, Student actual) {
        assertStudentUpdatableFieldsEquals(expected, actual);
        assertStudentUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStudentAutoGeneratedPropertiesEquals(Student expected, Student actual) {
        assertThat(expected)
            .as("Verify Student auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStudentUpdatableFieldsEquals(Student expected, Student actual) {
        assertThat(expected)
            .as("Verify Student relevant properties")
            .satisfies(e -> assertThat(e.getFirstName()).as("check firstName").isEqualTo(actual.getFirstName()))
            .satisfies(e -> assertThat(e.getLastName()).as("check lastName").isEqualTo(actual.getLastName()))
            .satisfies(e -> assertThat(e.getContactNo()).as("check contactNo").isEqualTo(actual.getContactNo()))
            .satisfies(e -> assertThat(e.getDateOfBirth()).as("check dateOfBirth").isEqualTo(actual.getDateOfBirth()))
            .satisfies(e -> assertThat(e.getGender()).as("check gender").isEqualTo(actual.getGender()))
            .satisfies(e -> assertThat(e.getEmail()).as("check email").isEqualTo(actual.getEmail()))
            .satisfies(e -> assertThat(e.getPassword()).as("check password").isEqualTo(actual.getPassword()))
            .satisfies(e -> assertThat(e.getUsername()).as("check username").isEqualTo(actual.getUsername()))
            .satisfies(e -> assertThat(e.getGuardianName()).as("check guardianName").isEqualTo(actual.getGuardianName()))
            .satisfies(e -> assertThat(e.getAddress()).as("check address").isEqualTo(actual.getAddress()))
            .satisfies(e -> assertThat(e.getGuardianContact()).as("check guardianContact").isEqualTo(actual.getGuardianContact()))
            .satisfies(e -> assertThat(e.getGuardianEmail()).as("check guardianEmail").isEqualTo(actual.getGuardianEmail()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStudentUpdatableRelationshipsEquals(Student expected, Student actual) {}
}
