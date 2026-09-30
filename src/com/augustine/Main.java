package com.augustine;

import com.augustine.booking.BookingStatus;
import com.augustine.booking.CarBooking;
import com.augustine.car.Brand;
import com.augustine.car.Car;
import com.augustine.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        System.out.println("Car Booking CLI");

        User john = new User("John");
        Car toyota = new Car(Brand.TOYOTA);



        CarBooking carBooking = new CarBooking(john,
                toyota,
                LocalDate.of(2026, 10, 20),
                LocalDate.of(2026, 10, 20).plusDays(5),
                BigDecimal.valueOf(500),
                BookingStatus.ACTIVE,
                LocalDateTime.now());

        System.out.println(carBooking);
    }
}
