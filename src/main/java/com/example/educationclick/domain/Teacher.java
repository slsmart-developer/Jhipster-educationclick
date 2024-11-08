package com.example.educationclick.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Teacher.
 */
@Entity
@Table(name = "teacher")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "fee_per_hour", nullable = false)
    private Double feePerHour;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "teacher")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "teacher" }, allowSetters = true)
    private Set<Timeslot> timeslots = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Subject subject;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Teacher id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Teacher name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public Teacher email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getFeePerHour() {
        return this.feePerHour;
    }

    public Teacher feePerHour(Double feePerHour) {
        this.setFeePerHour(feePerHour);
        return this;
    }

    public void setFeePerHour(Double feePerHour) {
        this.feePerHour = feePerHour;
    }

    public Set<Timeslot> getTimeslots() {
        return this.timeslots;
    }

    public void setTimeslots(Set<Timeslot> timeslots) {
        if (this.timeslots != null) {
            this.timeslots.forEach(i -> i.setTeacher(null));
        }
        if (timeslots != null) {
            timeslots.forEach(i -> i.setTeacher(this));
        }
        this.timeslots = timeslots;
    }

    public Teacher timeslots(Set<Timeslot> timeslots) {
        this.setTimeslots(timeslots);
        return this;
    }

    public Teacher addTimeslots(Timeslot timeslot) {
        this.timeslots.add(timeslot);
        timeslot.setTeacher(this);
        return this;
    }

    public Teacher removeTimeslots(Timeslot timeslot) {
        this.timeslots.remove(timeslot);
        timeslot.setTeacher(null);
        return this;
    }

    public Subject getSubject() {
        return this.subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Teacher subject(Subject subject) {
        this.setSubject(subject);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Teacher)) {
            return false;
        }
        return getId() != null && getId().equals(((Teacher) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Teacher{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", feePerHour=" + getFeePerHour() +
            "}";
    }
}
