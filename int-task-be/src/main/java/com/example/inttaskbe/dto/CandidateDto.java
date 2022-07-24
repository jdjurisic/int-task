package com.example.inttaskbe.dto;

import com.example.inttaskbe.entity.Skill;

import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

public class CandidateDto implements Dto{
    private Long id;

    @NotBlank
    private String fullName;

    @NotNull
    private Date birthdate;

    @NotBlank
    private String contactNumber;

    @NotBlank
    private String email;

    private Set<Skill> skills;

    public CandidateDto() {
    }

    public CandidateDto(Long id, String fullName, Date birthdate, String contactNumber, String email, Set<Skill> skills) {
        this.id = id;
        this.fullName = fullName;
        this.birthdate = birthdate;
        this.contactNumber = contactNumber;
        this.email = email;
        this.skills = skills;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CandidateDto that = (CandidateDto) o;
        return Objects.equals(id, that.id) && Objects.equals(fullName, that.fullName) && Objects.equals(birthdate, that.birthdate) && Objects.equals(contactNumber, that.contactNumber) && Objects.equals(email, that.email) && Objects.equals(skills, that.skills);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, birthdate, contactNumber, email, skills);
    }

    @Override
    public String toString() {
        return "CandidateDto{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", birthdate=" + birthdate +
                ", contactNumber='" + contactNumber + '\'' +
                ", email='" + email + '\'' +
                ", skills=" + skills +
                '}';
    }
}
