package com.augustine;


import com.augustine.booking.CarBooking;
import com.augustine.booking.CarBookingDAO;
import com.augustine.booking.CarBookingFileDataAccessService;
import com.augustine.booking.CarBookingService;
import com.augustine.car.Brand;
import com.augustine.car.CarArrayDataAccessService;
import com.augustine.car.CarDAO;
import com.augustine.car.CarService;
import com.augustine.user.UserArrayDataAccessService;
import com.augustine.user.UserDAO;
import com.augustine.user.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Scanner;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {

        // DAO implementations
        CarBookingDAO carBookingDAO =
                new CarBookingFileDataAccessService("CarBookings.dat");

        CarDAO carDAO = new CarArrayDataAccessService();

        UserDAO userDAO = new UserArrayDataAccessService();

        // Service classes with dependency injection
        UserService userService = new UserService(userDAO);

        CarService carService = new CarService(carDAO);

        CarBookingService bookingService =
                new CarBookingService(carBookingDAO, userDAO, carDAO);

        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        while (running) {

            System.out.println("\n===== CAR BOOKING SYSTEM =====");

            System.out.println("1. Register User");
            System.out.println("2. Register Car");
            System.out.println("3. Book Car");
            System.out.println("4. View All Bookings");
            System.out.println("5. View User Bookings");
            System.out.println("6. View Car Bookings");
            System.out.println("7. Find Booking By ID");
            System.out.println("8. Delete Booking");
            System.out.println("9. Exit");

            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            try {

                switch (choice) {

                    // REGISTER USER
                    case "1" -> {

                        System.out.print("Enter User ID: ");
                        UUID id = UUID.fromString(scanner.nextLine().trim());

                        System.out.print("Enter User Name: ");
                        String name = scanner.nextLine();

                        userService.registerUser(id, name);

                        System.out.println("User registered successfully!");
                    }

                    // REGISTER CAR
                    case "2" -> {

                        System.out.print("Enter Car ID: ");
                        UUID id = UUID.fromString(scanner.nextLine().trim());

                        System.out.print("Enter Registration Number: ");
                        String regNumber = scanner.nextLine();

                        System.out.print("Enter Rental Price Per Day: ");
                        BigDecimal price = new BigDecimal(scanner.nextLine().trim());

                        System.out.println("Available Brands:");
                        System.out.println(Arrays.toString(Brand.values()));

                        System.out.print("Enter Brand: ");
                        Brand brand = Brand.valueOf(
                                scanner.nextLine().trim().toUpperCase()
                        );

                        System.out.print("Is the car electric? (true/false): ");
                        boolean isElectric = Boolean.parseBoolean(
                                scanner.nextLine().trim()
                        );

                        carService.registerCar(
                                id,
                                regNumber,
                                price,
                                brand,
                                isElectric
                        );

                        System.out.println("Car registered successfully!");
                    }

                    // BOOK CAR
                    case "3" -> {

                        System.out.print("Enter Booking ID: ");
                        UUID id = UUID.fromString(scanner.nextLine().trim());

                        System.out.print("Enter User ID: ");
                        UUID userId = UUID.fromString(scanner.nextLine().trim());

                        System.out.print("Enter Car ID: ");
                        UUID carId = UUID.fromString(scanner.nextLine().trim());

                        System.out.print("Enter Start Date (yyyy-MM-dd): ");
                        LocalDate startDate = LocalDate.parse(
                                scanner.nextLine().trim()
                        );

                        System.out.print("Enter End Date (yyyy-MM-dd): ");
                        LocalDate endDate = LocalDate.parse(
                                scanner.nextLine().trim()
                        );

                        bookingService.bookCar(
                                id,
                                userId,
                                carId,
                                startDate,
                                endDate
                        );
                    }

                    // VIEW ALL BOOKINGS
                    case "4" -> {

                        CarBooking[] bookings =
                                bookingService.findAllBookings();

                        if (bookings.length == 0) {
                            System.out.println("No bookings found.");
                        } else {
                            System.out.println("All Bookings:");

                            for (CarBooking booking : bookings) {
                                System.out.println(booking);
                            }
                        }
                    }

                    // VIEW USER BOOKINGS
                    case "5" -> {

                        System.out.print("Enter User ID: ");

                        UUID userId = UUID.fromString(
                                scanner.nextLine().trim()
                        );

                        CarBooking[] bookings =
                                bookingService.findBookingsByUserId(userId);

                        if (bookings.length == 0) {
                            System.out.println("No bookings found for this user.");
                        } else {
                            System.out.println(Arrays.toString(bookings));
                        }
                    }

                    // VIEW CAR BOOKINGS
                    case "6" -> {

                        System.out.print("Enter Car ID: ");

                        UUID carId = UUID.fromString(
                                scanner.nextLine().trim()
                        );

                        CarBooking[] bookings =
                                bookingService.findBookingsByCarId(carId);

                        if (bookings.length == 0) {
                            System.out.println("No bookings found for this car.");
                        } else {
                            System.out.println(Arrays.toString(bookings));
                        }
                    }

                    // FIND BOOKING BY ID
                    case "7" -> {

                        System.out.print("Enter Booking ID: ");

                        UUID id = UUID.fromString(
                                scanner.nextLine().trim()
                        );

                        CarBooking booking = bookingService
                                .findBookingById(id)
                                .orElseThrow(() ->
                                        new IllegalArgumentException(
                                                "Booking not found"
                                        )
                                );

                        System.out.println(booking);
                    }

                    // DELETE BOOKING
                    case "8" -> {

                        System.out.print("Enter Booking ID: ");

                        UUID id = UUID.fromString(
                                scanner.nextLine().trim()
                        );

                        CarBooking deletedBooking = bookingService
                                .removeBooking(id)
                                .orElseThrow(() ->
                                        new IllegalArgumentException(
                                                "Booking not found"
                                        )
                                );

                        System.out.println("Booking deleted successfully!");
                        System.out.println(deletedBooking);
                    }

                    // EXIT
                    case "9" -> {

                        running = false;

                        System.out.println("End of session.");
                    }

                    default -> System.out.println(
                            "Invalid input. Choose from 1 - 9."
                    );
                }

            } catch (Exception e) {

                System.out.println("ERROR: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
