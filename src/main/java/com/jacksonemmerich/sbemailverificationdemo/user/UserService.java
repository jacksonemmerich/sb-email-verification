package com.jacksonemmerich.sbemailverificationdemo.user;

import com.jacksonemmerich.sbemailverificationdemo.exception.UserAlreadyExistsException;
import com.jacksonemmerich.sbemailverificationdemo.registration.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    //as classes AutoWired injetadas dessa forma devem ser do tipo final e usar @RequiredArgsConstructor do Lambok
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User registerUser(RegistrationRequest request) {
        Optional<User> user = this.findByEmail(request.email());
        if (user.isPresent()){
            throw new UserAlreadyExistsException("User with email "+ request.email() + "already exists");
        }
        var newUser = new User();
        newUser.setFisrtName(request.fisrtName());
        newUser.setLastName(request.lastName());
        newUser.setEmail(request.email());
        newUser.setPassword(passwordEncoder.encode(request.password()));
        newUser.setRole(request.role());
        return userRepository.save(newUser);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }
}
