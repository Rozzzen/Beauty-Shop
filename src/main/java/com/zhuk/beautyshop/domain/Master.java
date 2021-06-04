package com.zhuk.beautyshop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Table(name = "masters")
public class Master {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userInfo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rating_id", referencedColumnName = "id")
    private MasterRating rating;

    @ElementCollection(targetClass = FavourCategory.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "master_specialities", joinColumns = @JoinColumn(name = "master_id"))
    @Enumerated(EnumType.STRING)
    private Set<FavourCategory> specialities;

    @OneToMany(mappedBy = "master")
    @ToString.Exclude
    private List<Review> reviews;

    @Column(columnDefinition = "varchar(255) default 'default.jpg'")
    private String image = "default.jpg";

    @OneToMany(mappedBy="master", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Appointment> appointments;
}
