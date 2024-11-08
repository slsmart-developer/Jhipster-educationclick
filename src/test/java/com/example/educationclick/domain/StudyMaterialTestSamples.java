package com.example.educationclick.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class StudyMaterialTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static StudyMaterial getStudyMaterialSample1() {
        return new StudyMaterial().id(1L).name("name1").description("description1");
    }

    public static StudyMaterial getStudyMaterialSample2() {
        return new StudyMaterial().id(2L).name("name2").description("description2");
    }

    public static StudyMaterial getStudyMaterialRandomSampleGenerator() {
        return new StudyMaterial()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString());
    }
}
