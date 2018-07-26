package com.awantunai.bank.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "accounts")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},allowGetters = true)
public class Account{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // private User user;

    @NotNull(message = "Account Number can not be null")
    private String accNumber;

    @NotNull(message = "Pin can not be null")
    private String accPin;

    @NotNull(message = "Initial Balance can not be null")
    private Integer balance;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;


    // @ManyToOne
    // @JoinColumn(name = "user_id")
    // public User getUser() {
    //   return user;
    // }

    // public void setUser(User user) {
    //   this.user = user;
    // }

    public String getAccNumber() {
      return accNumber;
    }

    public void setAccNumber(String accNumber) {
      this.accNumber = accNumber;
    }

    public String getAccPin() {
      return accPin;
    }

    public void setAccPin(String accPin) {
      this.accPin = accPin;
    }

    public Integer getBalance() {
      return balance;
    }

    public void setBalance(Integer balance) {
      this.balance = balance;
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
