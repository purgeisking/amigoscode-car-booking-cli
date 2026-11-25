package com.augustine.booking;

import com.augustine.car.Brand;
import com.augustine.car.Car;
import com.augustine.car.CarDAO;
import com.augustine.car.CarService;
import com.augustine.user.User;
import com.augustine.user.UserDAO;
import com.augustine.user.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

public class CarBookingService {
    CarBookingDAO carBookingDAO = new CarBookingDAO();

    UserDAO userDAO = new UserDAO();

    CarDAO carDAO = new CarDAO();


    // Book a car for a customer
    public void bookCar(UUID id, UUID userId, UUID carId,
                        LocalDate startDate, LocalDate endDate){

        if (id == null){
            throw new IllegalArgumentException("Id cannot be null");
        }

        if (userId == null){
            throw new IllegalArgumentException("costumer Id cannot be null");
        }

        if (carId == null){
            throw new IllegalArgumentException("Car Id cannot be null");
        }

        if (startDate.isAfter(endDate)){
            throw new IllegalArgumentException("Invalid Date");
        }

        Optional<User> user = userDAO.findById(userId);

        if (user.isEmpty()){
            throw new IllegalArgumentException("User not found");
        }

        Optional<Car> car = carDAO.findById(carId);
        if (car.isEmpty()){
            throw new IllegalArgumentException("Car not found");
        }

        if (!isCarAvailable(carId, startDate, endDate)) {
            throw new IllegalArgumentException("Car is not available for these dates");
        }


        long day = ChronoUnit.DAYS.between(startDate, endDate);

        BigDecimal price = car.get().getRentalPricePerDay().multiply(BigDecimal.valueOf(day));

        CarBooking booking = new CarBooking(
                id,
                userId,
                carId,
                startDate,
                endDate,
                price,
                BookingStatus.ACTIVE,
                LocalDateTime.now()
        );

        carBookingDAO.save(booking);

        System.out.println("Car booked successfully!");

    }

    // Get all bookings
    public CarBooking[] findAllBookings(){
        return carBookingDAO.findAll();
    }

    // Find booking by ID
    public Optional<CarBooking> findBookingById(UUID id){
        if (id == null){
            throw new IllegalArgumentException("Id cannot be null");
        }
        return carBookingDAO.findById(id);
    }

    // Find all bookings belonging to a customer
    public CarBooking[] findBookingsByUserId(UUID userId){
        if (userId == null){
            throw new IllegalArgumentException("User Id cannot be null");
        }
        return carBookingDAO.findByUserId(userId);
    }

    // Find all bookings for a particular car
    public CarBooking[] findBookingsByCarId(UUID carId){
        if (carId == null){
            throw new IllegalArgumentException("Car Id cannot be null");
        }
        return carBookingDAO.findByCarId(carId);
    }

    // 6. Check car availability
    public boolean isCarAvailable(UUID carId,
                                  LocalDate startDate,
                                  LocalDate endDate) {

        CarBooking[] bookings = carBookingDAO.findByCarId(carId);

        for (int i = 0; i < bookings.length; i++) {

            CarBooking existingBooking = bookings[i];

            if (existingBooking.getStatus() == BookingStatus.ACTIVE) {

                boolean datesOverlap =
                        startDate.isBefore(existingBooking.getEndDate())
                                && endDate.isAfter(existingBooking.getStartDate());

                if (datesOverlap) {
                    return false;
                }
            }
        }

        return true;
    }

}
