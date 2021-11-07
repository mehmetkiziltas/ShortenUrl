package com.tapu.shorterurl.unique;

import com.tapu.shorterurl.model.User;
import com.tapu.shorterurl.repository.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final UserRepository userRepository;

    public UniqueUsernameValidator(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(final UniqueUsername constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(final String username,
                           final ConstraintValidatorContext context) {
        if (userRepository != null) {
            User user = userRepository.findByUsername(username);
            if (user != null) {
                return false;
            }
        }
        return true;
    }
}
