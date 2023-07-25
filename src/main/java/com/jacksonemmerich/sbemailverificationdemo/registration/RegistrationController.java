package com.jacksonemmerich.sbemailverificationdemo.registration;

import com.jacksonemmerich.sbemailverificationdemo.event.RegistrationCompleteEvent;
import com.jacksonemmerich.sbemailverificationdemo.user.User;
import com.jacksonemmerich.sbemailverificationdemo.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;
    private final ApplicationEventPublisher publisher;
    @PostMapping
    public String registerUser(RegistrationRequest registrationRequest, final HttpServletRequest request){
        User user = userService.registerUser(registrationRequest);
        publisher.publishEvent(new RegistrationCompleteEvent(user,applicationUrl(request)));
        return "Sucess! Please, check your email for to complete your registration";
    }

    public String applicationUrl(HttpServletRequest request) {
        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }


}
