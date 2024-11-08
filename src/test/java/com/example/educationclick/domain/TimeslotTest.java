package com.example.educationclick.domain;

import static com.example.educationclick.domain.TeacherTestSamples.*;
import static com.example.educationclick.domain.TimeslotTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.educationclick.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TimeslotTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Timeslot.class);
        Timeslot timeslot1 = getTimeslotSample1();
        Timeslot timeslot2 = new Timeslot();
        assertThat(timeslot1).isNotEqualTo(timeslot2);

        timeslot2.setId(timeslot1.getId());
        assertThat(timeslot1).isEqualTo(timeslot2);

        timeslot2 = getTimeslotSample2();
        assertThat(timeslot1).isNotEqualTo(timeslot2);
    }

    @Test
    void teacherTest() throws Exception {
        Timeslot timeslot = getTimeslotRandomSampleGenerator();
        Teacher teacherBack = getTeacherRandomSampleGenerator();

        timeslot.setTeacher(teacherBack);
        assertThat(timeslot.getTeacher()).isEqualTo(teacherBack);

        timeslot.teacher(null);
        assertThat(timeslot.getTeacher()).isNull();
    }
}
