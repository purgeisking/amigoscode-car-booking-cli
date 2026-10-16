package com.augustine.car;

import java.util.Optional;
import java.util.UUID;

public class CarService {
    private CarDAO carDAO;

    public CarService() {
        this.carDAO = new CarDAO();
    }

    public Car[] getAllCars(){
        return carDAO.findAllCars();
    }

    public Optional<Car> findCarById(UUID carId){
        Car[] cars = getAllCars();
        for (Car car : cars) {
            if (carId.equals(car.getId())){
                return Optional.of(car);
            }
        }
        return Optional.empty();
    }
}
