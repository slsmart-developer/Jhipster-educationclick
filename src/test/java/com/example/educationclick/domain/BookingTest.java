package com.example.educationclick.domain;

import static com.example.educationclick.domain.BookingTestSamples.*;
import static com.example.educationclick.domain.StudentTestSamples.*;
import static com.example.educationclick.domain.TeacherTestSamples.*;
import static com.example.educationclick.domain.TimeslotTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.educationclick.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BookingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Booking.class);
        Booking booking1 = getBookingSample1();
        Booking booking2 = new Booking();
        assertThat(booking1).isNotEqualTo(booking2);

        booking2.setId(booking1.getId());
        assertThat(booking1).isEqualTo(booking2);

        booking2 = getBookingSample2();
        assertThat(booking1).isNotEqualTo(booking2);
    }

    @Test
    void timeslotTest() throws Exception {
        Booking booking = getBookingRandomSampleGenerator();
        Timeslot timeslotBack = getTimeslotRandomSampleGenerator();

        booking.setTimeslot(timeslotBack);
        assertThat(booking.getTimeslot()).isEqualTo(timeslotBack);

        booking.timeslot(null);
        assertThat(booking.getTimeslot()).isNull();
    }

    @Test
    void teacherTest() throws Exception {
        Booking booking = getBookingRandomSampleGenerator();
        Teacher teacherBack = getTeacherRandomSampleGenerator();

        booking.setTeacher(teacherBack);
        assertThat(booking.getTeacher()).isEqualTo(teacherBack);

        booking.teacher(null);
        assertThat(booking.getTeacher()).isNull();
    }

    @Test
    void studentTest() throws Exception {
        Booking booking = getBookingRandomSampleGenerator();
        Student studentBack = getStudentRandomSampleGenerator();

        booking.setStudent(studentBack);
        assertThat(booking.getStudent()).isEqualTo(studentBack);

        booking.student(null);
        assertThat(booking.getStudent()).isNull();
    }
}
