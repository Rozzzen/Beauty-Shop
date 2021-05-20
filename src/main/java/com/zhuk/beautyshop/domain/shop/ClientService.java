package com.zhuk.beautyshop.domain.shop;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "services")
public class ClientService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Double price;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ServiceCategory category;

    @NotBlank
    @Column(name = "title_ua")
    private String titleUa;

    @NotBlank
    @Column(name = "title_en")
    private String titleEn;

    @Transient
    private String title;

    @OneToMany(mappedBy="service")
    @ToString.Exclude
    private List<Appointment> appointments;

}
