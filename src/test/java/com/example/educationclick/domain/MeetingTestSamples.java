package com.example.educationclick.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MeetingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Meeting getMeetingSample1() {
        return new Meeting().id(1L).meetingLink("meetingLink1");
    }

    public static Meeting getMeetingSample2() {
        return new Meeting().id(2L).meetingLink("meetingLink2");
    }

    public static Meeting getMeetingRandomSampleGenerator() {
        return new Meeting().id(longCount.incrementAndGet()).meetingLink(UUID.randomUUID().toString());
    }
}
