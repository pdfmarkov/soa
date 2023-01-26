package com.markov.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "coordinates")
public class Coordinates extends AbstractEntity{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//, generator="vehicle-gen")
    private int id;

    @Column(name = "x_coord")
    private Integer x; //Максимальное значение поля: 271
    @Column(name = "y_coord")
    private Float y; //Максимальное значение поля: 57, Поле не может быть null

    public Coordinates(Integer x, Float y){
        this.x = x;
        this.y = y;
    }
}
