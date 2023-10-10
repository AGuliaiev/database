package lesson7;

import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue
    private Integer id;

    private String city;

    private String street;
}