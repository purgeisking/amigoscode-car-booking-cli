package com.augustine.car;


import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public class CarService {

    private final CarDAO carDAO;

    public CarService(CarDAO carDAO) {
        this.carDAO = carDAO;
    }

    public void registerCar(UUID id,
                            String regNumber,
                            BigDecimal rentalPricePerDay,
                            Brand brand,
                            boolean isElectric) {

        if (id == null) {
            throw new IllegalArgumentException("Car ID cannot be null");
        }

        if (regNumber == null || regNumber.isBlank()) {
            throw new IllegalArgumentException("Registration number is required");
        }

        if (rentalPricePerDay == null
                || rentalPricePerDay.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid rental price");
        }

        if (brand == null) {
            throw new IllegalArgumentException("Brand cannot be null");
        }

        if (carDAO.findById(id).isPresent()) {
            throw new IllegalArgumentException("Car already exists");
        }

        Car car = new Car(
                id,
                regNumber.trim(),
                rentalPricePerDay,
                brand,
                isElectric
        );

        carDAO.save(car);
    }

    public Car[] findAllCars() {
        return carDAO.findAll();
    }

    public Optional<Car> findCarById(UUID id) {

        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        return carDAO.findById(id);
    }

    public Optional<Car> removeCar(UUID id) {

        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        return carDAO.deleteById(id);
    }
}
