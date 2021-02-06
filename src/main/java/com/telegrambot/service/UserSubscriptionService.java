package com.telegrambot.service;

import com.telegrambot.model.UserSubscription;
import com.telegrambot.repository.UserSubscriptionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserSubscriptionService {

    private final UserSubscriptionRepository subscriptionsRepository;

    public List<UserSubscription> getAllSubscriptions() {
        return subscriptionsRepository.findAll();
    }

    public void saveUserSubscription(UserSubscription usersSubscription) {
        subscriptionsRepository.save(usersSubscription);
    }

    public void deleteUserSubscription(Long id) {
        subscriptionsRepository.deleteById(id);
    }

    public Optional<UserSubscription> getUserSubscriptionByIdAndLink(Long chatId, String link) {
        return subscriptionsRepository.findByChatIdAndLink(chatId, link);
    }

    public Optional<List<UserSubscription>> getUserSubscriptions(long chatId) {
        return subscriptionsRepository.findByChatId(chatId);
    }
}
