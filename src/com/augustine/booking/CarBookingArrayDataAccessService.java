package com.augustine.booking;



import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public class CarBookingArrayDataAccessService implements CarBookingDAO {

    private CarBooking[] carBookings = new CarBooking[10];
    private int size = 0;

    @Override
    public void save(CarBooking booking) {

        if (booking == null) {
            throw new IllegalArgumentException("Booking cannot be null");
        }

        if (findById(booking.getId()).isPresent()) {
            throw new IllegalArgumentException("Booking already exists");
        }

        validateSize();

        carBookings[size] = booking;
        size++;
    }

    @Override
    public CarBooking[] findAll() {
        return Arrays.copyOf(carBookings, size);
    }

    @Override
    public Optional<CarBooking> findById(UUID id) {

        if (id == null) {
            return Optional.empty();
        }

        for (int i = 0; i < size; i++) {

            if (id.equals(carBookings[i].getId())) {
                return Optional.of(carBookings[i]);
            }
        }

        return Optional.empty();
    }

    @Override
    public CarBooking[] findByUserId(UUID userId) {

        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        CarBooking[] matches = new CarBooking[size];
        int count = 0;

        for (int i = 0; i < size; i++) {

            if (userId.equals(carBookings[i].getUserId())) {
                matches[count] = carBookings[i];
                count++;
            }
        }

        return Arrays.copyOf(matches, count);
    }

    @Override
    public CarBooking[] findByCarId(UUID carId) {

        if (carId == null) {
            throw new IllegalArgumentException("Car ID cannot be null");
        }

        CarBooking[] matches = new CarBooking[size];
        int count = 0;

        for (int i = 0; i < size; i++) {

            if (carId.equals(carBookings[i].getCarId())) {
                matches[count] = carBookings[i];
                count++;
            }
        }

        return Arrays.copyOf(matches, count);
    }

    @Override
    public Optional<CarBooking> deleteById(UUID id) {

        if (id == null) {
            return Optional.empty();
        }

        for (int i = 0; i < size; i++) {

            CarBooking booking = carBookings[i];

            if (id.equals(booking.getId())) {

                for (int j = i; j < size - 1; j++) {
                    carBookings[j] = carBookings[j + 1];
                }

                carBookings[size - 1] = null;
                size--;

                return Optional.of(booking);
            }
        }

        return Optional.empty();
    }

    private void validateSize() {

        if (size == carBookings.length) {
            carBookings = Arrays.copyOf(
                    carBookings,
                    carBookings.length * 2
            );
        }
    }
}

