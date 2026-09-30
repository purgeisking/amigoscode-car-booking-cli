package com.augustine.booking;

import com.augustine.car.Car;
import com.augustine.user.User;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class CarBookingService {
    User user = new User();
    Car car = new Car();
    public CarBooking bookCar(UUID userId, UUID carId, LocalDate startDate, LocalDate endDate) {
// 1. Look up the user by userId
        if (userId.equals(user.getId()))
            System.out.println("User available");
// - if not found, throw an exception or print an error
        if (userId == null) {
            throw new IllegalArgumentException("this user does not exit");
        }

// 2. Look up the car by carId
        Car car = new Car();
        if(carId.equals(car.getId())){
            System.out.println("Car available");
        }

// - if not found, throw an exception or print an error
        else{
            throw new IllegalArgumentException("This cat does not exist here");
        }

// 3. Validate the dates:
// - startDate must not be in the past
        if (startDate.isBefore(LocalDate.now())) {
            throw new DateTimeException("Starting date for a transaction cannot be a passed date");
        }

// - endDate must be after startDate
            if (endDate.isBefore(startDate)) {
                throw new DateTimeException("you can't return a car on a passed date bro 😂😂😂");
            }

// - if invalid, throw IllegalArgumentException or print an error
            if (startDate instanceof LocalDate) {
                System.out.println(startDate);
            } else if (endDate instanceof LocalDate) {
                System.out.println(endDate);
            } else {
                throw new IllegalArgumentException("Invalid dates");
            }

// 4. Get all current bookings
        int bookingNumber =100;
        CarBooking[] bookings = new CarBooking[bookingNumber];
        for (CarBooking booking : bookings) {
            if (booking != null){
                System.out.println(booking);
            }



// 5. Check whether an active booking already holds this car
// - if it does, reject: the car is not available
            if (booking != null && booking.getId().equals(carId) && new CarBooking().getStatus() == BookingStatus.ACTIVE){
                System.out.println("""
                        This car has been booked and cannot be booked twice🤕... Kindly choose another
                        """);
            }
        }
// 6. Count the days with ChronoUnit.DAYS.between(startDate, endDate)
             int numberOfDays = Math.toIntExact(ChronoUnit.DAYS.between(startDate, endDate));

// 7. Calculate the price: car.getRentalPricePerDay() x numberOfDays
        BigDecimal price = car.getRentalPricePerDay().multiply(new BigDecimal(numberOfDays));

// 8. Build a CarBooking with a UUID, user, car, dates, price,
// BookingStatus.ACTIVE and bookedAt = LocalDateTime.now()
// 9. Save the booking through the DAO
// 10. Return the saved booking
            return null;
        }

    }
