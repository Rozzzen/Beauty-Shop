package com.zhuk.beautyshop.domain.model;

import com.zhuk.beautyshop.domain.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserModel {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String password2;
    private UserRole role;
    private List<AppointmentModel> appointments;
}
