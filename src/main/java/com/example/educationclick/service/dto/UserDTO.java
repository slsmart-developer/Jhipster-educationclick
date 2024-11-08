package com.example.educationclick.service.dto;

import com.example.educationclick.domain.Subject;
import com.example.educationclick.domain.User;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * A DTO representing a user, with only the public attributes.
 */
@Data
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String login;

    private String contactNo; // New field

    private LocalDate dateOfBirth;

    private String gender;

    private String guardianName;

    private String address;

    private String guardianContact;

    private String guardianEmail;

    private List<Subject> subjects; // New field for list of subjects

    public UserDTO() {
        // Empty constructor needed for Jackson.
    }

    public UserDTO(User user) {
        this.id = user.getId();
        // Customize it here if you need, or not, firstName/lastName/etc
        this.login = user.getLogin();

        this.contactNo = user.getContactNo();
        this.dateOfBirth = user.getDateOfBirth();
        this.gender = user.getGender();
        this.guardianName = user.getGuardianName();
        this.address = user.getAddress();
        this.guardianContact = user.getGuardianContact();
        this.guardianEmail = user.getGuardianEmail();
        //        if (user.getSubjects() != null && !user.getSubjects().isEmpty()) {
        //            this.subjects = new ArrayList<>(user.getSubjects());
        //        } else {
        //            this.subjects = new ArrayList<>();
        //        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGuardianContact() {
        return guardianContact;
    }

    public void setGuardianContact(String guardianContact) {
        this.guardianContact = guardianContact;
    }

    public String getGuardianEmail() {
        return guardianEmail;
    }

    public void setGuardianEmail(String guardianEmail) {
        this.guardianEmail = guardianEmail;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserDTO{" +
            "id='" + id + '\'' +
            ", login='" + login + '\'' +
            ", contactNo='" + contactNo + '\'' +
            ", dateOfBirth='" + dateOfBirth + '\'' +
            ", gender='" + gender + '\'' +
            ", subjects='" + subjects + '\'' +  // Include the list of subjects
            ", guardianName='" + guardianName + '\'' +
            ", address='" + address + '\'' +
            ", guardianContact='" + guardianContact + '\'' +
            ", guardianEmail='" + guardianEmail + '\'' +
            "}";
    }
}
