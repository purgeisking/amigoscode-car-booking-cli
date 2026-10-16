package com.augustine.booking;

import com.augustine.car.Brand;
import com.augustine.car.Car;
import com.augustine.car.CarService;
import com.augustine.user.User;
import com.augustine.user.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class CarBookingService {
    private CarService carService;
    private UserService userService;
    private CarBookingDAO carBookingDAO;

    public CarBookingService(CarService carService, UserService userService, CarBookingDAO carBookingDAO) {
        this.carService = carService;
        this.userService = userService;
        this.carBookingDAO = carBookingDAO;
    }

    public CarBooking bookCar(UUID userId, UUID carId, LocalDate startDate, LocalDate endDate) {
        // 1. Look up the user by userId
        // - if not found, throw an exception or print an error
        User user = userService.findUserById(userId).orElseThrow(() -> new RuntimeException("No User found"));

        // 2. Look up the car by carId
        // - if not found, throw an exception or print an error
        Car car = carService.findCarById(carId).orElseThrow(() -> new RuntimeException("No Car found"));

        // 3. Validate the dates:
        // - startDate must not be in the past
        // - if invalid, throw IllegalArgumentException or print an error
        if (startDate.isBefore(LocalDate.now()) ||
                startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Invalid start date");
        }
        // 4. Get all current bookings
        CarBooking[] carBookings = carBookingDAO.getAll();
        for (CarBooking carBooking : carBookings) {
            // 5. Check whether an active booking already holds this car
            // - if it does, reject: the car is not available
            if (carBooking.getCar() != null &&
                    carBooking.getCar().equals(car) &&
                    carBooking.getStatus().equals(BookingStatus.ACTIVE)) {
                throw new RuntimeException("Car %s is already booked!".formatted(car.getRegNumber()));
            }
        }


        // 6. Count the days with ChronoUnit.DAYS.between(startDate, endDate)
        long numberOfDays = ChronoUnit.DAYS.between(startDate, endDate);
        // 7. Calculate the price: car.getRentalPricePerDay() x numberOfDays
        BigDecimal price = car.getRentalPricePerDay().multiply(BigDecimal.valueOf(numberOfDays));
        // 8. Build a CarBooking with a UUID, user, car, dates, price,
        // BookingStatus.ACTIVE and bookedAt = LocalDateTime.now()
        CarBooking carBooking = new CarBooking(UUID.randomUUID(), user, car, startDate, endDate, price, BookingStatus.ACTIVE, LocalDateTime.now());
        // 9. Save the booking through the DAO
        carBookingDAO.save(carBooking);
        // 10. Return the saved booking
        return carBooking;
    }


    public CarBooking[] getAllUserBookedCars(UUID userId) {
        User user =
                userService.findUserById(userId)
                        .orElseThrow(() -> new RuntimeException("No user found with the id %s".formatted(userId)));

        CarBooking[] carBookings = carBookingDAO.getAll();
        int numberOfUserBookings = 0;
        for (int i = 0; i < carBookings.length; i++) {
            if (carBookings[i].getUser() != null &&
                    carBookings[i].getUser().equals(user)) {
                ++numberOfUserBookings;
            }
        }
        if (numberOfUserBookings == 0) {
            return new CarBooking[0];
        }
        CarBooking[] userBookings = new CarBooking[numberOfUserBookings];
        int index = 0;
        for (CarBooking carBooking : carBookings) {
            if (carBooking != null && carBooking.getUser().equals(user)){
                userBookings[index++] = carBooking;
            }
        }
        return userBookings;
    }



    public Car[] getAllAvailableElectricCars() {
        Car[] cars = carService.getAllCars();
        CarBooking[] bookings = carBookingDAO.getAll();
        int numAvailableCars = 0;
        for (Car c : cars) {
            if (!c.isElectric()) continue;
            boolean isBooked = false;
            for (CarBooking cb : bookings) {
                if (cb != null && cb.getCar().equals(c))
                    isBooked = true;
            }
            if (!isBooked) {
                numAvailableCars++;
            }
        }

        if (numAvailableCars == 0) {
            return new Car[0];
        }

        Car[] availableCars = new Car[numAvailableCars];
        int index = 0;
        for (Car c : cars) {
            if (!c.isElectric()) continue;
            boolean isBooked = false;
            for (CarBooking cb : bookings) {
                if (cb != null && cb.getCar().equals(c))
                    isBooked = true;
            }
            if (!isBooked) {
                availableCars[index++] = c;
            }
        }

        return availableCars;
    }





    public CarBooking[] getAllBookings(){
        CarBooking[] carBookings = carBookingDAO.getAll();
        int activeBooking = 0;
        for (CarBooking booking : carBookings) {
            if(booking != null){
                ++activeBooking;
            }
            if (activeBooking == 0){
                return new CarBooking[0];
            }
        }
        CarBooking[] actualBookings = new CarBooking[activeBooking];
        int index = 0;
        for (CarBooking booking : carBookings) {
            if(booking != null){
                actualBookings[index++] = booking;
            }
        }
        return actualBookings;
    }

    public Car[] getAvailableCars() {
        Car[] cars = carService.getAllCars();
        CarBooking[] carBookings = carBookingDAO.getAll();
        int numberOfAvailableCars = 0;
        for (Car car : cars) {
            boolean isBooked = false;
            for (CarBooking carBooking : carBookings) {
                if (carBooking.getCar().equals(car))
                    isBooked = true;
            }
                if (!isBooked) {
                    numberOfAvailableCars++;
                }
            }
            if (numberOfAvailableCars == 0) {
                return new Car[0];
            }
            Car[] availableCars = new Car[numberOfAvailableCars];
            int index = 0;

            for (Car car : cars) {
                boolean isBooked = false;
                for (CarBooking carBooking : carBookings) {
                    if (carBooking.getCar().equals(car)) {
                        isBooked = true;
                    }
                    if (!isBooked) {
                        availableCars[index] = car;
                    }

                }


            }




        return availableCars;
    }



    public CarBooking deleteBookingById(UUID id){
        return carBookingDAO.deleteCarBookingById(id)
                .orElseThrow(()-> new RuntimeException("No booking found with id %d".formatted(id)));

    }


}
