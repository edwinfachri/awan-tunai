package com.awantunai.bank.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "accounts")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},allowGetters = true)
public class Account{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @NotBlank
    private String acc_number;

    // @NotBlank
    private Integer balance;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

    public String getAccNumber() {
      return acc_number;
    }

    public void setAccNumber(String acc_number) {
      this.acc_number = acc_number;
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
