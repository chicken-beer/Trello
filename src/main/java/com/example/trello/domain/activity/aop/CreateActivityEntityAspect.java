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
    public void getAddUserToCardExecution(Long cardId, Long userId, UserDetailsImpl userDetails) {}

    @Pointcut("execution(public * com.example.trello.domain.card.controller.CardController.deleteUserFromCard(..)) && args(.., cardId, userId, userDetails)")
    public void getDeleteUserFromCardExecution(Long cardId, Long userId, UserDetailsImpl userDetails) {}

    @AfterReturning("getAddUserToCardExecution(cardId, userId, userDetails)")
    public void addUserToCardActivityExecution(Long cardId, Long userId, UserDetailsImpl userDetails) {
        activityService.makeActivityByAddUser(cardId, userId, userDetails);
    }

    @AfterReturning("getDeleteUserFromCardExecution(cardId, userId, userDetails)")
    public void deleteUserFromCardActivityExecution(Long cardId, Long userId, UserDetailsImpl userDetails) {
        activityService.makeActivityByDeleteUser(cardId, userId, userDetails);
    }
}
