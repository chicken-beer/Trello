package com.example.trello.domain.activity;

import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.card.repository.CardRepository;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.repository.UserRepository;
import com.example.trello.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;
    private final CardRepository cardRepository;


    public void makeActivityByAddUser(Long cardId, Long userId, UserDetailsImpl userDetails) {
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new IllegalArgumentException("aop, search by cardId. 없을리가 없습니다."));
        User addedUser = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("aop, search by userId. 없을리가 없습니다."));
        User addingUser = userDetails.getUser();

        String contents = addingUser.getUsername()+"가 "+addedUser.getUsername()+"을 추가했습니다.";

        Activity activity = new Activity(contents, card, addingUser);

        activityRepository.save(activity);
    }
}
