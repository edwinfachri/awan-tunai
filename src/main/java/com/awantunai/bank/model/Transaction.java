package com.awantunai.bank.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
@Table(name = "transactions")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},allowGetters = true)
public class Transaction{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Min(0)
    @Max(10)
    private Integer type;

    @NotNull(message = "amount can not be null")
    @Min(0)
    private Integer amount;

    @Size(max = 50)
    private String destination;

    private String note = "";

    private Boolean status = false;

    private String accNumber;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

    private Transaction() {

    }

    public Transaction(
              Account account,
              Integer type,
              Integer amount,
              Boolean status
    ) {
      this.account = account;
      this.type = type;
      this.amount = amount;
      this.status = status;
    }

    public Transaction(
              Account account,
              Integer type,
              Integer amount,
              String destination,
              String note,
              Boolean status
    ) {
      this.account = account;
      this.type = type;
      this.amount = amount;
      this.destination = destination;
      this.note = note;
      this.status = status;
    }

    public Transaction(
              Account account,
              Integer type,
              Integer amount,
              String destination,
              Boolean status
    ) {
      this.account = account;
      this.type = type;
      this.amount = amount;
      this.destination = destination;
      this.status = status;
    }

    public Account getAccount() {
      return account;
    }

    public void setAccount(Account account) {
      this.account = account;
    }

    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
      return type;
    }

    public void setType(Integer type) {
      this.type = type;
    }

    public Integer getAmount() {
      return amount;
    }

    public void setAmount(Integer amount) {
      this.amount = amount;
    }

    public String getDestination() {
      return destination;
    }

    public void setDestination(String destination) {
      this.destination = destination;
    }

    public String getNote() {
      return note;
    }

    public void setNote(String note) {
      this.note = note;
    }

    public Boolean getStatus() {
      return status;
    }

    public void setStatus(Boolean status) {
      this.status = status;
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

    public String getAccNumber() {
      return accNumber;
    }

    public void setAccNumber(String accNumber) {
      this.accNumber = accNumber;
    }
}
