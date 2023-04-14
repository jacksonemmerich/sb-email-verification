package com.jacksonemmerich.sbemailverificationdemo.registration;

import org.hibernate.annotations.NaturalId;

public record RegistrationRequest(
        String fisrtName,
        String lastName,
        String email,
        String password,
        String role) {
}
