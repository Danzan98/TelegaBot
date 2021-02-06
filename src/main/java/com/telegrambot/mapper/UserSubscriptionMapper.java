package com.telegrambot.mapper;

import com.telegrambot.model.Item;
import com.telegrambot.model.UserSubscription;
import org.springframework.stereotype.Service;

@Service
public class UserSubscriptionMapper {

    public UserSubscription toSubscription(Item item) {
        UserSubscription subscription = new UserSubscription();
        subscription.setBrand(item.getBrand());
        subscription.setLink(item.getLink());
        subscription.setName(item.getName());
        subscription.setPrice(item.getPrice());
        return subscription;
    }
}
