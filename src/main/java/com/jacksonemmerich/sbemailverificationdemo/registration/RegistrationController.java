package com.jacksonemmerich.sbemailverificationdemo.registration;

import com.jacksonemmerich.sbemailverificationdemo.event.RegistrationCompleteEvent;
import com.jacksonemmerich.sbemailverificationdemo.registration.token.VerificationToken;
import com.jacksonemmerich.sbemailverificationdemo.registration.token.VerificationTokenRepository;
import com.jacksonemmerich.sbemailverificationdemo.user.User;
import com.jacksonemmerich.sbemailverificationdemo.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;
    private final ApplicationEventPublisher publisher;
    private final VerificationTokenRepository verificationTokenRepository;

    @PostMapping
    public String registerUser(@RequestBody RegistrationRequest registrationRequest, final HttpServletRequest request){
        User user = userService.registerUser(registrationRequest);
        publisher.publishEvent(new RegistrationCompleteEvent(user,applicationUrl(request)));
        return "Sucess! Please, check your email for to complete your registration";
    }

    @GetMapping("/verifyEmail")
    public String verifyYourEmail(@RequestParam("token") String token){
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken.getUser().isEnabled()){
            throw new IllegalStateException("Email already verified, please login to continue");
        }
        String verificationResult = userService.validateToken(token);
        if(verificationResult.equalsIgnoreCase("valid")){
            return "Email verified successfully. Now you can login to continue";
        }
        return "Invalid verification token";
    }

    public String applicationUrl(HttpServletRequest request) {
        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }


}
