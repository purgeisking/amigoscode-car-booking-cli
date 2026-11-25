package com.augustine.booking;


import com.augustine.car.Car;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public class CarBookingDAO {
    private CarBooking[] carBookings = new CarBooking[10];
    private int size = 0;
    // Save a new booking
    public void save(CarBooking booking){
        if (booking == null){
            throw new IllegalArgumentException("Booking cannot be null");
        }

        validateSize();

        carBookings[size] = booking;
        size++;

    }

    // Return all bookings
    public CarBooking[] findAll(){
        return Arrays.copyOf(carBookings, size);
    }

    // Find booking using ID
    public Optional<CarBooking> findById(UUID id){
        if (id == null){
            return Optional.empty();
        }

        for (int i = 0; i < size; i++) {
            if (carBookings[i].getId() != null && id.equals(carBookings[i].getId())){
                return Optional.of(carBookings[i]);
            }
        }
        return Optional.empty();
    }

    // Find all bookings belonging to a customer
    public CarBooking[] findByUserId(UUID userId){
        if (userId == null){
            throw new IllegalArgumentException("User Id cannot be null");
        }
        CarBooking[] costumerBookings = new CarBooking[size];
        int count = 0;

        for (int i = 0; i < size; i++) {
            if (carBookings[i].getUserId() != null && userId.equals(carBookings[i].getUserId())){
                costumerBookings[count] = carBookings[i];
                count++;
            }

        }
        return Arrays.copyOf(costumerBookings, count);
    }

    // Find all bookings for a particular car
    public CarBooking[] findByCarId(UUID carId){
        if (carId == null){
            throw new IllegalArgumentException("Car id cannot be null");
        }
        CarBooking[] carBookingsForSingleCar = new CarBooking[size];
        int count = 0;

        for (int i = 0; i < size; i++) {
            if (carBookings[i].getCarId() != null && carId.equals(carBookings[i].getCarId())){
                carBookingsForSingleCar[count] = carBookings[i];
                count++;
            }

        }
        return Arrays.copyOf(carBookingsForSingleCar, count);
    }

    // Delete booking using ID
    public Optional<CarBooking> deleteById(UUID id){
        if (id == null){
            throw new IllegalArgumentException("Id cannot be null");
        }
        for (int i = 0; i < size; i++) {
            CarBooking booking = carBookings[i];
            if (booking.getId() != null && id.equals(booking.getId())){
                CarBooking deletedBooking = booking;

                for (int j = i; j < size -1; j++) {
                    carBookings[j] = carBookings[j + 1];
                }
                carBookings[size-1] = null;
                size--;
                return Optional.of(deletedBooking);
            }
        }
        return Optional.empty();
    }


    private void validateSize(){
        if (size == carBookings.length){
            carBookings = Arrays.copyOf(carBookings, carBookings.length*2);
        }
    }

}
