package com.augustine.car;


import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public class CarArrayDataAccessService implements CarDAO {

    private Car[] cars = new Car[10];
    private int size = 0;

    @Override
    public void save(Car car) {

        if (car == null) {
            throw new IllegalArgumentException("Car cannot be null");
        }

        if (findById(car.getId()).isPresent()) {
            throw new IllegalArgumentException("Car already exists");
        }

        validateSize();

        cars[size] = car;
        size++;
    }

    @Override
    public Car[] findAll() {
        return Arrays.copyOf(cars, size);
    }

    @Override
    public Optional<Car> findById(UUID id) {

        if (id == null) {
            return Optional.empty();
        }

        for (int i = 0; i < size; i++) {

            if (id.equals(cars[i].getId())) {
                return Optional.of(cars[i]);
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<Car> deleteById(UUID id) {

        if (id == null) {
            return Optional.empty();
        }

        for (int i = 0; i < size; i++) {

            Car car = cars[i];

            if (id.equals(car.getId())) {

                for (int j = i; j < size - 1; j++) {
                    cars[j] = cars[j + 1];
                }

                cars[size - 1] = null;
                size--;

                return Optional.of(car);
            }
        }

        return Optional.empty();
    }

    private void validateSize() {

        if (size == cars.length) {
            cars = Arrays.copyOf(cars, cars.length * 2);
        }
    }
}
