package com.example.educationclick.domain;

import static com.example.educationclick.domain.StudyMaterialTestSamples.*;
import static com.example.educationclick.domain.TeacherTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.educationclick.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StudyMaterialTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudyMaterial.class);
        StudyMaterial studyMaterial1 = getStudyMaterialSample1();
        StudyMaterial studyMaterial2 = new StudyMaterial();
        assertThat(studyMaterial1).isNotEqualTo(studyMaterial2);

        studyMaterial2.setId(studyMaterial1.getId());
        assertThat(studyMaterial1).isEqualTo(studyMaterial2);

        studyMaterial2 = getStudyMaterialSample2();
        assertThat(studyMaterial1).isNotEqualTo(studyMaterial2);
    }

    @Test
    void teacherTest() throws Exception {
        StudyMaterial studyMaterial = getStudyMaterialRandomSampleGenerator();
        Teacher teacherBack = getTeacherRandomSampleGenerator();

        studyMaterial.setTeacher(teacherBack);
        assertThat(studyMaterial.getTeacher()).isEqualTo(teacherBack);

        studyMaterial.teacher(null);
        assertThat(studyMaterial.getTeacher()).isNull();
    }
}
