package com.jacksonemmerich.sbemailverificationdemo.user;

import com.jacksonemmerich.sbemailverificationdemo.registration.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepository;

    @Override
    public List<User> getUsers(RegistrationRequest request) {
        Optional<User> user = this.findByEmail(request.email());
        if (user.isPresent()){
            throw new UserAlreadyExistsException("User with email "+ request.email() + "already exists");
        }
        var newUser = new User();
        newUser.setFisrtName(request.fisrtName());
        newUser.setLastName(request.lastName());
        newUser.setEmail(request.email());
        newUser.setPassword();
        newUser.setRole(request.role());
        return userRepository.save(newUser);
    }

    @Override
    public User registerUser(RegistrationRequest request) {
        return null;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }
}
