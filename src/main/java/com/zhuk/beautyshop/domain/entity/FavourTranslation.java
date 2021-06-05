package com.zhuk.beautyshop.domain.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "favour_translation")
@Builder
@AllArgsConstructor
public class FavourTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "favour_id", referencedColumnName = "id")
    private Favour favour;

    @NotNull
    private String language;

    @NotNull
    private String text;
}
