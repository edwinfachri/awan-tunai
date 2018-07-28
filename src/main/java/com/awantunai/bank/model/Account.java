package com.awantunai.bank.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
import com.fasterxml.jackson.annotation.JsonBackReference;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "accounts")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},allowGetters = true)
public class Account{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(unique = true)
    @NotNull(message = "Account Number can not be null")
    @Size(min = 10, max = 10)
    private String accNumber;

    @NotNull(message = "AccPin can not be null")
    @Size(min = 6, max = 6)
    private String accPin;

    @NotNull(message = "Initial Balance can not be null")
    @Min(0)
    private Integer balance;

    private String sessionId;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

    public User getUser() {
      return user;
    }

    public void setUser(User user) {
      this.user = user;
    }

    // @OneToMany(mappedBy="transaction")
    // private List<Transaction> transactions;
    //
    // public List<Transaction> getTransactions() {
    //   return transactions;
    // }

    // public void setTransactions(List<Transaction> transactions) {
    //   this.transactions = transactions;
    // }

    private Account() { } // JPA only

    public Account(final User user,
                   final String accNumber,
                   final String accPin,
                   final Integer balance
    ) {
        this.user = user;
        this.accNumber = accNumber;
        this.accPin = accPin;
        this.balance = balance;
    }

    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
      // PasswordEncoder encoder = new BCryptPasswordEncoder(12);
      // this.accPin = encoder.encode(accPin);
      this.accPin = accPin;
    }

    public String getSessionId() {
      return sessionId;
    }

    public void setSessionId(String sessionId) {
      this.sessionId = sessionId;
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
