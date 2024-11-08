package com.example.educationclick.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class StudentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Student getStudentSample1() {
        return new Student()
            .id(1L)
            .firstName("firstName1")
            .lastName("lastName1")
            .contactNo("contactNo1")
            .gender("gender1")
            .email("email1")
            .password("password1")
            .username("username1")
            .guardianName("guardianName1")
            .address("address1")
            .guardianContact("guardianContact1")
            .guardianEmail("guardianEmail1");
    }

    public static Student getStudentSample2() {
        return new Student()
            .id(2L)
            .firstName("firstName2")
            .lastName("lastName2")
            .contactNo("contactNo2")
            .gender("gender2")
            .email("email2")
            .password("password2")
            .username("username2")
            .guardianName("guardianName2")
            .address("address2")
            .guardianContact("guardianContact2")
            .guardianEmail("guardianEmail2");
    }

    public static Student getStudentRandomSampleGenerator() {
        return new Student()
            .id(longCount.incrementAndGet())
            .firstName(UUID.randomUUID().toString())
            .lastName(UUID.randomUUID().toString())
            .contactNo(UUID.randomUUID().toString())
            .gender(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .password(UUID.randomUUID().toString())
            .username(UUID.randomUUID().toString())
            .guardianName(UUID.randomUUID().toString())
            .address(UUID.randomUUID().toString())
            .guardianContact(UUID.randomUUID().toString())
            .guardianEmail(UUID.randomUUID().toString());
    }
}
