package com.telegrambot.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Item {
    private String link;
    private String brand;
    private String name;
    private Double price;
}
