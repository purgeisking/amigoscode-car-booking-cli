package com.augustine;


import com.augustine.booking.CarBooking;
import com.augustine.booking.CarBookingDAO;
import com.augustine.booking.CarBookingService;
import com.augustine.car.Car;
import com.augustine.car.CarDAO;
import com.augustine.car.CarService;
import com.augustine.user.User;
import com.augustine.user.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;



public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        CarService carService = new CarService();
        UserService userService = new UserService();
        CarBookingDAO carBookingDAO = new CarBookingDAO();
        CarDAO carDAO = new CarDAO();
        CarBookingService carBookingService = new CarBookingService(carService, userService, carBookingDAO);
        boolean loopIsActive = true;

        while (loopIsActive){
            System.out.println("""
                    1 - Book Car
                    2 - Delete Booking
                    3 - View All User Booked Cars
                    4 - View All Bookings
                    5 - View Available Cars
                    6 - View Available Electric Cars
                    7 - View All Users
                    8 - Exit
                    """
            );

            try {
                String choice = scanner.nextLine().trim();
                switch (choice){
                    case "1" -> {
                        System.out.print("Input User id: ");
                        UUID userId = UUID.fromString(scanner.nextLine().trim());

                        System.out.print("Input car Id: ");
                        UUID carId = UUID.fromString(scanner.nextLine().trim());

                        System.out.print("Booking start date: ");
                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyy");
                        LocalDate startDate = LocalDate.parse(scanner.nextLine(), dateTimeFormatter);

                        System.out.print("Booking end date: ");
                        LocalDate endDate = LocalDate.parse(scanner.nextLine(), dateTimeFormatter);

                        CarBooking booking = carBookingService.bookCar(userId, carId, startDate, endDate);
                        System.out.println("This is the Booked Car info");
                        System.out.println(booking);
                    }

                    case "2" -> {
                        System.out.println("Enter Id of booking you wish to delete: ");
                        UUID id = UUID.fromString(scanner.nextLine());
                       CarBooking deletedCarbooking = carBookingService.deleteBookingById(id);
                       System.out.println("This car has successfully been deleted");
                        System.out.println(deletedCarbooking);
                    }

                    case "3" -> {
                        System.out.println("enter Id of booked cars: ");
                        UUID id = UUID.fromString(scanner.nextLine());
                        CarBooking[] userBookedCars = carBookingService.getAllUserBookedCars(id);

                        System.out.println("This are the following cars booked by user: ");
                        System.out.println(Arrays.toString(userBookedCars));
                    }

                    case "4" -> {
                        System.out.println("this is a list of all bookings: ");
                        CarBooking[] listOfAllBookings = carBookingService.getAllBookings();
                        System.out.println(Arrays.toString(listOfAllBookings));
                    }

                    case "5" -> {
                        System.out.println("Here is a list of available cars for booking");
                        Car[] availableCars = carDAO.findAllCars();
                        System.out.println(Arrays.toString(availableCars));
                    }

                    case "6" -> {
                        System.out.println("Here is a list of Electric cars for booking");
                        Car[] availableElectricCars = carBookingService.getAllAvailableElectricCars();
                        System.out.println(Arrays.toString(availableElectricCars));
                    }

                    case "7" -> {
                        System.out.println("This is a list of all Users");
                        User[] allUsers = userService.findAllUsers();
                        System.out.println(Arrays.toString(allUsers));
                    }
                    case "8" -> {
                        loopIsActive = false;
                        System.out.println("This loop exits here");
                    }

                    default -> System.out.println("Invalid input... choose from 1-8");

                }
            }catch (InputMismatchException e) {
                System.out.println("Invalid input!");
                scanner.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println(
                        "Error: " + e.getMessage()
                );
            }

        }


scanner.close();
    }

    }
