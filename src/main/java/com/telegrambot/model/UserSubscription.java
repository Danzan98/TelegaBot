package com.telegrambot.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "user_subscription")
public class UserSubscription {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private long chatId;

    private String link;

    private String brand;

    private String name;

    private Double price;
}
