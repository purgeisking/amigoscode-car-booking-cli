package com.augustine.car;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public class CarService {
    CarDAO carDAO = new CarDAO();
    // Register a new car
    public void registerCar(UUID id,
                            BigDecimal rentalPricePerDay){
        if (id == null){
            throw new IllegalArgumentException("Car Id cannot be null");
        }
        if (carDAO.findById(id).isPresent()){
            throw new IllegalArgumentException("this car exists already");
        }

        if (rentalPricePerDay.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("invalid price");
        }
        Car car = new Car(id, rentalPricePerDay);
        carDAO.save(car);
    }

    // Get all registered cars
    public Car[] findAllCars(){
        return getAll();
    }

    private Car[] getAll() {
        return carDAO.findAll();
    }

    // Find a car by ID
    public Optional<Car> findCarById(UUID id){
        if (id == null){
            throw new IllegalArgumentException("Id cannot be null");
        }
        return carDAO.findById(id);
    }

    // Remove a car
    public Optional<Car> removeCar(UUID id){
        if (id == null){
            throw new IllegalArgumentException("Id cannot be null");
        }
        return carDAO.deleteById(id);
    }
}
