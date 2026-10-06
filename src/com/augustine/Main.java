package com.augustine;


import com.augustine.booking.CarBookingService;
import com.augustine.car.Car;
import com.augustine.user.User;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.UUID;



public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        CarBookingService carBookingService = new CarBookingService();
        User[] users = new User[0];
        User[] user1 = new User[1];

        int input = scanner.nextInt();
try {
    switch (input){
        case 1 -> carBookingService.bookCar(UUID.fromString("8ca51d2b-aaaf-4bf2-834a-e02964e10fc3"), UUID.fromString("550e8400-e29b-41d4-a716-446655440000"), LocalDate.now(), LocalDate.now());
//        case 2 -> carBookingService.deleteBooking();
//        case 3 -> carBookingService.showAllUserBookedCars();
//        case 4 -> carBookingService.showAllBookings();
//        case 5 -> carBookingService.viewAllAvailableCars();
//        case 6 -> carBookingService.viewAvailableElectricCars();
//        case 8 -> carBookingService.viewAllUsers();
    }
}catch (Exception e){
    System.out.println(e.getMessage());
}








    }
}
