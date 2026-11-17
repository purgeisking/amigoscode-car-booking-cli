package com.augustine;


import com.augustine.booking.CarBooking;
import com.augustine.booking.CarBookingDAO;
import com.augustine.booking.CarBookingService;
import com.augustine.car.Car;
import com.augustine.car.CarDAO;
import com.augustine.car.CarService;
import com.augustine.user.User;
import com.augustine.user.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;



public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        CarService carService = new CarService();
        CarBookingService bookingService = new CarBookingService();
        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        try {



        while (running){
            System.out.println("\n===== CAR BOOKING SYSTEM =====");
            System.out.println("1. Register User");
            System.out.println("2. Register Car");
            System.out.println("3. Book Car");
            System.out.println("4. View All Bookings");
            System.out.println("5. View User Bookings");
            System.out.println("6. View Car Bookings");
            System.out.println("7. Exit");

            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice){
                case "1" -> {
                    System.out.print("Enter user id: ");
                    UUID id = UUID.fromString(scanner.nextLine());

                    System.out.print("Enter user name: ");
                    String name = scanner.nextLine();

                    userService.registerUser(id, name);
                }
                case "2" -> {
                    System.out.print("Enter car Id: ");
                    UUID id = UUID.fromString(scanner.nextLine());

                    System.out.print("Enter rental price per day: ");
                    BigDecimal rentalPricePerDay = scanner.nextBigDecimal();
                }

                case "3" -> {
                    System.out.print("Enter Booking Id: ");
                    UUID id = UUID.fromString(scanner.nextLine());

                    System.out.print("Enter Car Id: ");
                    UUID carId = UUID.fromString(scanner.nextLine());

                    System.out.print("Enter User Id: ");
                    UUID userId = UUID.fromString(scanner.nextLine());

                    System.out.print("Enter start date (yyyy-MM-dd): ");
                    LocalDate startDate = LocalDate.parse(scanner.nextLine());

                    System.out.print("Enter end date (yyyy-MM-dd): ");
                    LocalDate endDate = LocalDate.parse(scanner.nextLine());

                    bookingService.bookCar(
                            id,
                            userId,
                            carId,
                            startDate,
                            endDate
                    );
                }
                case "4" -> {
                    System.out.println("This is a list of all bookings: ");
                    System.out.println(Arrays.toString(carService.findAllCars()));
                }

                case "5" -> {
                    System.out.println("this is a list of bookings for this user: ");
                    UUID id = UUID.fromString(scanner.nextLine());
                    System.out.println(Arrays.toString(bookingService.findBookingsByUserId(id)));
                }

                case "6" -> {
                    System.out.println("this is a list of all car Bookings with Id: ");
                    UUID id = UUID.fromString(scanner.nextLine());
                    System.out.println(Arrays.toString(bookingService.findBookingsByCarId(id)));
                }

                case "7" -> {
                    running = false;
                    System.out.println("End of session");
                }
                default -> {
                    System.out.println("Invalid input: choose from 1 - 7");
                }
            }
        }
    }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    }
