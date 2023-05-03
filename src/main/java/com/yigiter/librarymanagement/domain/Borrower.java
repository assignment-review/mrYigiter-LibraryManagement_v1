package com.yigiter.librarymanagement.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "tbl_borrowers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Borrower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;


    @Column(unique = true,nullable = false)
    private String email;

    @Column(unique = true,nullable = false)
    private String phoneNumber;

}