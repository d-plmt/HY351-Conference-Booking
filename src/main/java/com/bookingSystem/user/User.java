package com.bookingSystem.user;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @Column(name = "id")
    private Integer id;

    // @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    // private Employee employee;

    // @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    // private Admin admin;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String phone;

    public User(String firstName, String lastName, String email, String password, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", firstName=" + firstName
                + ", lastName=" + lastName + ", email=" + email + ", password=" + password + ", phone=" + phone + "]";
    }

    
}
