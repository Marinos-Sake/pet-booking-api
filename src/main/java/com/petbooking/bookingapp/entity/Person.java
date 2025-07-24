package com.petbooking.bookingapp.entity;

import com.petbooking.bookingapp.core.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name= "persons")
public class Person  extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String surname;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "place_of_birth", nullable = false, length = 100)
    private String placeOfBirth;

    @Column(name= "father_name", nullable = false, length = 100)
    private String fatherName;

    @Column(name = "identity_number", nullable = false, unique = true, length = 9)
    private String identityNumber;

    /**
     * The uploaded identity document used to verify this person's identity
     */

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "identity_number_file_id")
    private Attachment identityNumberFile;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender gender;

    @OneToOne(mappedBy = "person")
    private User user;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pet> pets = new ArrayList<>();
}
