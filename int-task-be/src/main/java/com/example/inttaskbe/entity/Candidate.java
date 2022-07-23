package com.example.inttaskbe.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "candidates")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private Date birthdate;

    private String contactNumber;

    private String email;

    @OneToMany
    private Set<Skill> skills;

    public Candidate() {
    }

    public Candidate(Long id, String fullName, Date birthdate, String contactNumber, String email, Set<Skill> skills) {
        this.id = id;
        this.fullName = fullName;
        this.birthdate = birthdate;
        this.contactNumber = contactNumber;
        this.email = email;
        this.skills = skills;
    }

    public void addSkill(Skill skill){
        this.skills.add(skill);
    }

    public void removeSkill(Skill skill){
        this.skills.remove(skill);
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
        Candidate candidate = (Candidate) o;
        return Objects.equals(id, candidate.id) && Objects.equals(fullName, candidate.fullName) && Objects.equals(birthdate, candidate.birthdate) && Objects.equals(contactNumber, candidate.contactNumber) && Objects.equals(email, candidate.email) && Objects.equals(skills, candidate.skills);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, birthdate, contactNumber, email, skills);
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", birthdate=" + birthdate +
                ", contactNumber='" + contactNumber + '\'' +
                ", email='" + email + '\'' +
                ", skills=" + skills +
                '}';
    }
}
