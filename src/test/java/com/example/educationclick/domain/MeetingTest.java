package com.example.educationclick.domain;

import static com.example.educationclick.domain.MeetingTestSamples.*;
import static com.example.educationclick.domain.StudentTestSamples.*;
import static com.example.educationclick.domain.TeacherTestSamples.*;
import static com.example.educationclick.domain.TimeslotTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.educationclick.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MeetingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Meeting.class);
        Meeting meeting1 = getMeetingSample1();
        Meeting meeting2 = new Meeting();
        assertThat(meeting1).isNotEqualTo(meeting2);

        meeting2.setId(meeting1.getId());
        assertThat(meeting1).isEqualTo(meeting2);

        meeting2 = getMeetingSample2();
        assertThat(meeting1).isNotEqualTo(meeting2);
    }

    @Test
    void teacherTest() throws Exception {
        Meeting meeting = getMeetingRandomSampleGenerator();
        Teacher teacherBack = getTeacherRandomSampleGenerator();

        meeting.setTeacher(teacherBack);
        assertThat(meeting.getTeacher()).isEqualTo(teacherBack);

        meeting.teacher(null);
        assertThat(meeting.getTeacher()).isNull();
    }

    @Test
    void timeslotTest() throws Exception {
        Meeting meeting = getMeetingRandomSampleGenerator();
        Timeslot timeslotBack = getTimeslotRandomSampleGenerator();

        meeting.setTimeslot(timeslotBack);
        assertThat(meeting.getTimeslot()).isEqualTo(timeslotBack);

        meeting.timeslot(null);
        assertThat(meeting.getTimeslot()).isNull();
    }

    @Test
    void studentTest() throws Exception {
        Meeting meeting = getMeetingRandomSampleGenerator();
        Student studentBack = getStudentRandomSampleGenerator();

        meeting.setStudent(studentBack);
        assertThat(meeting.getStudent()).isEqualTo(studentBack);

        meeting.student(null);
        assertThat(meeting.getStudent()).isNull();
    }
}
