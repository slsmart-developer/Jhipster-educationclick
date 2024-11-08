package com.example.educationclick.domain;

import static com.example.educationclick.domain.SubjectTestSamples.*;
import static com.example.educationclick.domain.TeacherTestSamples.*;
import static com.example.educationclick.domain.TimeslotTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.educationclick.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TeacherTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Teacher.class);
        Teacher teacher1 = getTeacherSample1();
        Teacher teacher2 = new Teacher();
        assertThat(teacher1).isNotEqualTo(teacher2);

        teacher2.setId(teacher1.getId());
        assertThat(teacher1).isEqualTo(teacher2);

        teacher2 = getTeacherSample2();
        assertThat(teacher1).isNotEqualTo(teacher2);
    }

    @Test
    void timeslotsTest() throws Exception {
        Teacher teacher = getTeacherRandomSampleGenerator();
        Timeslot timeslotBack = getTimeslotRandomSampleGenerator();

        teacher.addTimeslots(timeslotBack);
        assertThat(teacher.getTimeslots()).containsOnly(timeslotBack);
        assertThat(timeslotBack.getTeacher()).isEqualTo(teacher);

        teacher.removeTimeslots(timeslotBack);
        assertThat(teacher.getTimeslots()).doesNotContain(timeslotBack);
        assertThat(timeslotBack.getTeacher()).isNull();

        teacher.timeslots(new HashSet<>(Set.of(timeslotBack)));
        assertThat(teacher.getTimeslots()).containsOnly(timeslotBack);
        assertThat(timeslotBack.getTeacher()).isEqualTo(teacher);

        teacher.setTimeslots(new HashSet<>());
        assertThat(teacher.getTimeslots()).doesNotContain(timeslotBack);
        assertThat(timeslotBack.getTeacher()).isNull();
    }

    @Test
    void subjectTest() throws Exception {
        Teacher teacher = getTeacherRandomSampleGenerator();
        Subject subjectBack = getSubjectRandomSampleGenerator();

        teacher.setSubject(subjectBack);
        assertThat(teacher.getSubject()).isEqualTo(subjectBack);

        teacher.subject(null);
        assertThat(teacher.getSubject()).isNull();
    }
}
