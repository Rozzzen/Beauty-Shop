package com.zhuk.beautyshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "favours")
@Builder
@AllArgsConstructor
public class Favour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Double price;

    @NotNull
    @Enumerated(EnumType.STRING)
    private FavourCategory category;

    @OneToMany(mappedBy= "favourTranslation")
    @ToString.Exclude
    @JsonIgnore
    private List<Appointment> appointments;
}