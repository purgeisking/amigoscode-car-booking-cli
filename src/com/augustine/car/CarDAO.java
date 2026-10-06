package com.augustine.car;

import java.math.BigDecimal;
import java.util.UUID;

public class CarDAO {
    private final static Car[] cars;

    static {
        cars = new Car[]{
                new Car(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"), "10", new BigDecimal(String.valueOf(1000)), Brand.TESLA, true)
        };
    }
}
