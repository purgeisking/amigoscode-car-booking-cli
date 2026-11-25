package com.augustine.car;

import java.util.Optional;
import java.util.UUID;

public interface CarDAO {
    public void save(Car car);
    public Car[] findAll();
    public Optional<Car> findById(UUID id);
    public Optional<Car> deleteById(UUID id);
}
