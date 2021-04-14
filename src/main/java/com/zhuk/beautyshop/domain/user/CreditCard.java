package com.zhuk.beautyshop.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
//@Entity
@AllArgsConstructor
//@Table(name = "credit_cards")
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreditCardNumber(message = "Invalid credit card number")
    private String CCNumber;

    @NotBlank(message = "Owner name should not be empty")
    private String ownerName;

    @Pattern(regexp = "^(0[1-9]|1[0-2])\\/?([0-9]{4}|[0-9]{2})$", message = "Invalid expiration date")
    private String expirationDate;

    @Pattern(regexp = "^[0-9]{3,4}$", message = "Invalid CVV")
    private String CVV;
}
