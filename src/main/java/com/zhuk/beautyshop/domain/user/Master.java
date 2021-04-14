package com.zhuk.beautyshop.domain.user;

import com.zhuk.beautyshop.domain.shop.Appointment;
import com.zhuk.beautyshop.domain.shop.ServiceCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ElementCollection(targetClass = ServiceCategory.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "master_specialities", joinColumns = @JoinColumn(name = "master_id"))
    @Enumerated(EnumType.STRING)
    private Set<ServiceCategory> specialities;

    @OneToMany(mappedBy = "master")
    private List<Review> reviews;

    @Column(columnDefinition = "varchar(255) default 'default.jpg'")
    private String image = "default.jpg";

    @OneToMany(mappedBy="master", fetch = FetchType.EAGER)
    private List<Appointment> appointments;
}
