package com.augustine.car;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public class CarDAO {
    private Car[] cars = new  Car[10];
    private int size = 0;


    // Register a new car
    public void save(Car car){
        if (car == null){
            throw new IllegalArgumentException("Car cannot be null");
        }

        validateSize();
        cars[size] = car;
        size++;
    }

    // Return all registered cars
    public Car[] findAll(){
        return Arrays.copyOf(cars, size);
    }

    // Find a car using its ID
    public Optional<Car> findById(UUID id){
        if (id == null){
            return Optional.empty();
        }

        for (int i = 0; i < size; i++) {
            Car car = cars[i];
            if (car.getId() != null && id.equals(car.getId())){
                return Optional.of(car);
            }
        }
        return Optional.empty();
    }



    // Delete a car using its ID
    public Optional<Car> deleteById(UUID id){
        if (id == null){
            return Optional.empty();
        }

        for (int i = 0; i < size; i++) {
            Car car = cars[i];
            if (car.getId() != null && id.equals(car.getId())){
                Car deletedCar = car;

                for (int j = i; j < size - 1; j++) {
                    cars[j] = cars[j +1];
                }
                cars[size - 1] = null;
                size--;
                return Optional.of(deletedCar);
            }
        }
        return Optional.empty();
    }

    private void validateSize(){
        if (size == cars.length){
            cars = Arrays.copyOf(cars, cars.length * 2);
        }
    }
}
