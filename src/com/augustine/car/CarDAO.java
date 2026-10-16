package com.augustine.car;

import java.math.BigDecimal;
import java.util.UUID;

public class CarDAO {
    private final static Car[] cars ;

    static {
        cars = new Car[]{
                new Car(
                        UUID.fromString("a12f4b8c-3d91-4e67-8a25-6c9d0f1e2b43"),
                        "LT-001-AA",
                        new BigDecimal("25000"),
                        Brand.TOYOTA,
                        false
                ),

                new Car(
                        UUID.fromString("b23a5c9d-4e82-4f16-9b37-7d0e1a2c3f54"),
                        "LT-002-BB",
                        new BigDecimal("30000"),
                        Brand.MERCEDES,
                        false
                ),

                new Car(
                        UUID.fromString("c34b6d0e-5f93-4027-ac48-8e1f2b3d4a65"),
                        "LT-003-CC",
                        new BigDecimal("35000"),
                        Brand.MERCEDES,
                        false
                ),

                new Car(
                        UUID.fromString("d45c7e1f-6a04-4138-bd59-9f2a3c4e5b76"),
                        "LT-004-DD",
                        new BigDecimal("40000"),
                        Brand.TESLA,
                        true
                )
        };
    }
    public Car[] findAllCars(){
        return cars;
    }
}
