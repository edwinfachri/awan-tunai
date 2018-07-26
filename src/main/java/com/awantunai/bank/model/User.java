package com.awantunai.bank.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},allowGetters = true)
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // private Set<Account> accounts;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthDate;

    @NotBlank
    private String phone;

    @NotBlank
    private String address;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

    // @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    // public Set<Account> getAccounts() {
    //   return accounts;
    // }
    //
    // public void setAccounts(Set<Account> accounts) {
    //   this.accounts = accounts;
    // }

    public String getFirstName() {
      return firstName;
    }

    public void setFirstName(String firstName) {
      this.firstName = firstName;
    }

    public String getLastName() {
      return lastName;
    }

    public void setLastName(String lastName) {
      this.lastName = lastName;
    }

    public void setPhone(String phone) {
      this.phone = phone;
    }

    public String getPhone() {
      return phone;
    }

    public void setAddress(String address) {
      this.address = address;
    }

    public String getAddress() {
      return address;
    }

    public Date getBirthDate() {
      return birthDate;
    }

    public void setBirthDate(Date birthDate) {
      this.birthDate = birthDate;
    }

    public Date getCreatedAt() {
      return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
      this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
      return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
      this.updatedAt = updatedAt;
    }
}
