package com.augustine.booking;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public class CarBookingDAO {
    private static CarBooking[] carBookings;
    private static int capacity = 2;

    static {
        carBookings = new CarBooking[capacity];
    }

    public CarBooking[] getAll(){
        return Arrays.copyOf(carBookings, findAvailableIndex());
    }

    public void save(CarBooking booking){
        int nextAvailableIndex = findAvailableIndex();
        if (nextAvailableIndex == -1){
            int newCapacity = capacity*2;
            carBookings = Arrays.copyOf(carBookings, newCapacity);
            nextAvailableIndex = capacity;
            capacity = newCapacity;
        }
        carBookings[nextAvailableIndex] = booking;

    }


    private int findAvailableIndex(){
        for (int i = 0; i < carBookings.length; i++) {
            if (carBookings[i] == null){
                return i;
            }
        }
        return -1;
    }

    public Optional<CarBooking> findCarBookingById(UUID carBookingId){
        for (CarBooking carBooking : carBookings) {
            if (carBooking != null && carBookingId != null && carBooking.getId() != null && carBookingId.equals(carBooking.getId())){
                return Optional.of(carBooking);
            }

        }
        return Optional.empty();
    }


    public Optional<CarBooking> deleteCarBookingById(UUID carBookingId){
        if (carBookingId == null){
            return Optional.empty();
        }
        for (int i = 0; i < carBookings.length; i++) {
            CarBooking carBooking = carBookings[i];
            if (carBooking != null && carBookingId.equals(carBooking.getId()));

            CarBooking deleteBooking = carBooking;
            carBookings[i] = null;
            return Optional.of(deleteBooking);

        }
        return Optional.empty();
    }

}
