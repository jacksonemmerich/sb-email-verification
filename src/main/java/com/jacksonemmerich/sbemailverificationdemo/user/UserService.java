package com.jacksonemmerich.sbemailverificationdemo.user;

import com.jacksonemmerich.sbemailverificationdemo.exception.UserAlreadyExistsException;
import com.jacksonemmerich.sbemailverificationdemo.registration.RegistrationRequest;
import com.jacksonemmerich.sbemailverificationdemo.registration.token.VerificationToken;
import com.jacksonemmerich.sbemailverificationdemo.registration.token.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    //as classes AutoWired injetadas dessa forma devem ser do tipo final e usar @RequiredArgsConstructor do Lambok
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository tokenRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User registerUser(RegistrationRequest request) {
        Optional<User> user = this.findByEmail(request.email());
        if (user.isPresent()){
            throw new UserAlreadyExistsException("User with email "+ request.email() +" already exists");
        }
        var newUser = new User();
        newUser.setFirstName(request.fisrtName());
        newUser.setLastName(request.lastName());
        newUser.setEmail(request.email());
        newUser.setPassword(passwordEncoder.encode(request.password()));
        newUser.setRole(request.role());
        return userRepository.save(newUser);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUserVerificationToken(User theUser, String token) {
        var verificationToken = new VerificationToken(token, theUser);
        tokenRepository.save(verificationToken);
    }

    @Override
    public String validateToken(String verificationToken) {
        VerificationToken token = tokenRepository.findByToken(verificationToken);
        if(token == null){
            return "Invalid verification token";
        }

        User user = token.getUser();
        Calendar calendar = Calendar.getInstance();
        if (token.getTokenExpirationTime().getTime() - calendar.getTime().getTime() <= 0){
            tokenRepository.delete(token);
            return "Token already expired";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "valid";
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken = tokenRepository.findByToken(oldToken);
        var tokenExpirationTime = new VerificationToken();
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationToken.setExpirationTime(tokenExpirationTime.getTokenExpirationTime());
        return tokenRepository.save(verificationToken);
    }


}
