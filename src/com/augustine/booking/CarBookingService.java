package com.augustine.booking;

import com.augustine.car.Brand;
import com.augustine.car.Car;
import com.augustine.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class CarBookingService {
    private Car car;
    private User user;
    private CarBooking[] carBooking;



    public CarBookingService() {
    }

    public CarBookingService(Car car, User user, CarBooking carBooking) {
        this.car = car;
        this.user = user;
        this.carBooking = new CarBooking[]{carBooking};
    }

    public CarBookingService(Car car, User user) {
        this.car = car;
        this.user = user;
    }

    public CarBookingService(User user) {
        this.user = user;
    }

    public CarBookingService(Car car) {
        this.car = car;
    }

    // delete booking on return

    public void deleteBooking(UUID id, LocalDate endDate) {

        for (int i = 0; i < carBooking.length; i++) {

            CarBooking booking = carBooking[i];

            if (booking != null
                    && booking.getId().equals(id)
                    && booking.getEndDate().isEqual(endDate)
                    && booking.getStatus() == BookingStatus.ACTIVE) {

                carBooking[i] = null;
                return;
            }
        }
    }

    //3 - View All User Booked Cars
    public void showAllUserBookedCars(Car car, User user){
        for (CarBooking carBooking : carBooking) {
            if (carBooking.getUser() != null
                    && carBooking.getCar() != null
                    && car.equals(carBooking.getCar())
                    && user.equals(carBooking.getUser())
            ){
                System.out.println(carBooking);
            }
        }
    }

    // 4 - View All Bookings
    public void showAllBookings(Car car, BookingStatus bookingStatus){
        for (CarBooking booking : carBooking) {
            if(booking.getCar() != null && car.equals(booking.getCar()) && bookingStatus.equals(BookingStatus.ACTIVE)){
                System.out.println(booking);
            }
        }
    }


//    5- View Available Cars
    public void viewAllAvailableCars(Car[] car){
        for (Car car1 : car) {
            if (car1.getId() != null){
                System.out.println(car1);
            }
        }
    }

//    6 - View Available Electric Cars
    public void viewAvailableElectricCars(Car[] cars){
        for (Car car1 : cars) {
            if (car1.isElectric()){
                System.out.println(car1);
            }
        }
    }
//    7 - View All Users
    public void viewAllUsers(User[] users){
        for (User user1 : users) {
            System.out.println(user1);
        }
    }



    public CarBooking[] bookCar(UUID userId, UUID carId, LocalDate startDate, LocalDate endDate) {
// 1. Look up the user by userId
        if (userId == null) {
            throw new NullPointerException("UserId cannot be a null value");
        }
        try {
            if (userId.equals(user.getId())) {
                System.out.println("User available");
            } else {
                System.out.println("This user does not exist");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("this user does not exit");
        }

// - if not found, throw an exception or print an error


// 2. Look up the car by carId
        try {
            if (carId == car.getId()) {
                System.out.println(car);
            }
        }
        // - if not found, throw an exception or print an error
        catch (IllegalArgumentException e) {
            System.out.println("This car does not exist");
        }


// 3. Validate the dates:
// - startDate must not be in the past
// - endDate must be after startDate
// - if invalid, throw IllegalArgumentException or print an error
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Dates cannot be null");
        }

        if (startDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Start date cannot be in the past");
        }

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }


// 4. Get all current bookings
        for (CarBooking value : carBooking) {
            if (value != null) {
                System.out.println(value);
            }
        }
//        CarBooking[] bookings = new CarBooking[bookingNumber];
//        for (CarBooking booking : bookings) {
//            if (booking != null) {
//                System.out.println(booking);
//            }
//        }


// 5. Check whether an active booking already holds this car
// - if it does, reject: the car is not available
        for (CarBooking booking : carBooking) {

            if (booking != null
                    && booking.getId().equals(carId)
                    && booking.getStatus() == BookingStatus.ACTIVE) {

                throw new IllegalStateException(
                        "This car has already been booked. Kindly choose another."
                );
            }
        }

// 6. Count the days with ChronoUnit.DAYS.between(startDate, endDate)
        int numberOfDays = Math.toIntExact(ChronoUnit.DAYS.between(startDate, endDate));
        System.out.println("Number of days booked: " + numberOfDays);


// 7. Calculate the price: car.getRentalPricePerDay() x numberOfDays
        BigDecimal price = car.getRentalPricePerDay().multiply(new BigDecimal(numberOfDays));
        System.out.println("This is your booking price base on the duration" + price);


// 8. Build a CarBooking with a UUID, user, car, dates, price,
// BookingStatus.ACTIVE and bookedAt = LocalDateTime.now()
// 9. Save the booking through the DAO


// 10. Return the saved booking
        return carBooking;
    }

    }
