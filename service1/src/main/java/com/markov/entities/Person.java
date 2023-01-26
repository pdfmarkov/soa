package com.markov.entities;

import com.markov.entities.enums.EyeColor;
import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "person")
public class Person extends AbstractEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(name = "eye_color")
    private EyeColor eyeColor;
    @Column(name = "height")
    private Integer height;
    @Column(name = "passport_id")
    private String passportID;

    @OneToOne(cascade =  CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;
}
