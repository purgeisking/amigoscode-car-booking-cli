package com.augustine.booking;


import com.augustine.car.Car;
import com.augustine.car.CarDAO;
import com.augustine.user.User;
import com.augustine.user.UserDAO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

public class CarBookingService {

    private final CarBookingDAO carBookingDAO;
    private final UserDAO userDAO;
    private final CarDAO carDAO;

    public CarBookingService(
            CarBookingDAO carBookingDAO,
            UserDAO userDAO,
            CarDAO carDAO) {

        this.carBookingDAO = carBookingDAO;
        this.userDAO = userDAO;
        this.carDAO = carDAO;
    }

    // Book a car
    public void bookCar(
            UUID id,
            UUID userId,
            UUID carId,
            LocalDate startDate,
            LocalDate endDate) {

        if (id == null) {
            throw new IllegalArgumentException("Booking ID cannot be null");
        }

        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        if (carId == null) {
            throw new IllegalArgumentException("Car ID cannot be null");
        }

        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Dates cannot be null");
        }

        if (startDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Start date cannot be in the past");
        }

        if (!endDate.isAfter(startDate)) {
            throw new IllegalArgumentException("End date must be after start date");
        }

        if (carBookingDAO.findById(id).isPresent()) {
            throw new IllegalArgumentException("Booking ID already exists");
        }

        // Check whether user exists
        User user = userDAO.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found"));

        // Check whether car exists
        Car car = carDAO.findById(carId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Car not found"));

        // Check availability
        if (!isCarAvailable(carId, startDate, endDate)) {
            throw new IllegalArgumentException(
                    "Car is not available for these dates"
            );
        }

        // Calculate inclusive rental days
        long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        BigDecimal price = car.getRentalPricePerDay()
                .multiply(BigDecimal.valueOf(days));

        CarBooking booking = new CarBooking(
                id,
                user.getId(),
                car.getId(),
                startDate,
                endDate,
                price,
                BookingStatus.ACTIVE,
                LocalDateTime.now()
        );

        carBookingDAO.save(booking);

        System.out.println("Car booked successfully!");
        System.out.println(booking);
    }

    // Find all bookings
    public CarBooking[] findAllBookings() {
        return carBookingDAO.findAll();
    }

    // Find booking by ID
    public Optional<CarBooking> findBookingById(UUID id) {

        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        return carBookingDAO.findById(id);
    }

    // Find bookings by user ID
    public CarBooking[] findBookingsByUserId(UUID userId) {

        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        return carBookingDAO.findByUserId(userId);
    }

    // Find bookings by car ID
    public CarBooking[] findBookingsByCarId(UUID carId) {

        if (carId == null) {
            throw new IllegalArgumentException("Car ID cannot be null");
        }

        return carBookingDAO.findByCarId(carId);
    }

    // Delete booking
    public Optional<CarBooking> removeBooking(UUID id) {

        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        return carBookingDAO.deleteById(id);
    }

    // Check car availability
    public boolean isCarAvailable(
            UUID carId,
            LocalDate startDate,
            LocalDate endDate) {

        if (carId == null || startDate == null || endDate == null) {
            throw new IllegalArgumentException("Invalid availability arguments");
        }

        if (!endDate.isAfter(startDate)) {
            throw new IllegalArgumentException("Invalid booking dates");
        }

        CarBooking[] bookings = carBookingDAO.findByCarId(carId);

        for (CarBooking existingBooking : bookings) {

            if (existingBooking.getStatus() == BookingStatus.ACTIVE) {

                boolean datesOverlap =
                        !startDate.isAfter(existingBooking.getEndDate())
                                && !endDate.isBefore(existingBooking.getStartDate());

                if (datesOverlap) {
                    return false;
                }
            }
        }

        return true;
    }
}
