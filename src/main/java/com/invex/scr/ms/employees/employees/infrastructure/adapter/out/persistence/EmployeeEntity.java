package com.invex.scr.ms.employees.employees.infrastructure.adapter.out.persistence;

import com.invex.scr.ms.employees.employees.domain.model.Gender;
import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "employee")
public class EmployeeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "first_name", nullable = false, length = 50)
  private String firstName;

  @Column(name = "middle_name", length = 50)
  private String middleName;

  @Column(name = "paternal_surname", nullable = false, length = 50)
  private String paternalSurname;

  @Column(name = "maternal_surname", length = 50)
  private String maternalSurname;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 10)
  private Gender gender;

  @Column(name = "birth_date", nullable = false)
  private LocalDate birthDate;

  @Column(name = "job_title", nullable = false, length = 100)
  private String jobTitle;

  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;

  @Column(nullable = false)
  private boolean active;

  protected EmployeeEntity() {}

  public EmployeeEntity(
      Long id,
      String firstName,
      String middleName,
      String paternalSurname,
      String maternalSurname,
      Gender gender,
      LocalDate birthDate,
      String jobTitle,
      Instant createdAt,
      boolean active) {
    this.id = id;
    this.firstName = firstName;
    this.middleName = middleName;
    this.paternalSurname = paternalSurname;
    this.maternalSurname = maternalSurname;
    this.gender = gender;
    this.birthDate = birthDate;
    this.jobTitle = jobTitle;
    this.createdAt = createdAt;
    this.active = active;
  }

  public Long getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public String getPaternalSurname() {
    return paternalSurname;
  }

  public String getMaternalSurname() {
    return maternalSurname;
  }

  public Gender getGender() {
    return gender;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public String getJobTitle() {
    return jobTitle;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public boolean isActive() {
    return active;
  }
}
