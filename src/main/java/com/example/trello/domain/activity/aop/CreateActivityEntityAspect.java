package com.example.trello.domain.activity.aop;


import com.example.trello.domain.activity.ActivityService;
import com.example.trello.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class CreateActivityEntityAspect {


    private final ActivityService activityService;

    @Pointcut("execution(public * com.example.trello.domain.card.controller.CardController.addUserToCard(..)) && args(.., cardId, userId, userDetails)")
    public void getaddUserToCardExecution(Long cardId, Long userId, UserDetailsImpl userDetails) {}

    @AfterReturning("getaddUserToCardExecution(cardId, userId, userDetails)")
    public void makeActivityExecution(Long cardId, Long userId, UserDetailsImpl userDetails) {
        activityService.makeActivityByAddUser(cardId, userId, userDetails);
    }
}
