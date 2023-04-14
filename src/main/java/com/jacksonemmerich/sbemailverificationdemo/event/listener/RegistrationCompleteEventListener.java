package com.jacksonemmerich.sbemailverificationdemo.event.listener;

import com.jacksonemmerich.sbemailverificationdemo.event.RegistrationCompleteEvent;
import com.jacksonemmerich.sbemailverificationdemo.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        //1. Get tho new registered user
        User theUser = event.getUser();
        //2. Create a verification token for the user
        String verificationToken = UUID.randomUUID().toString();
        //3. Save the verification token for the user

        //4. build the verification url to be sent to the user
        String url = event.getApplicationUrl()+"/register/verifyYourEmail?token="+verificationToken;
        //5. Send the email.
    }
}
