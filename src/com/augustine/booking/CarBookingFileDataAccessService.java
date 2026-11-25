package com.augustine.booking;



import java.io.*;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public class CarBookingFileDataAccessService implements CarBookingDAO {

    private final String filepath;

    public CarBookingFileDataAccessService(String filepath) {
        this.filepath = filepath;
    }

    // Read all bookings from the file
    private CarBooking[] readBookings() {

        try (FileInputStream fileInput = new FileInputStream(filepath);
             ObjectInputStream input = new ObjectInputStream(fileInput)) {

            return (CarBooking[]) input.readObject();

        } catch (FileNotFoundException e) {

            return new CarBooking[0];

        } catch (IOException | ClassNotFoundException e) {

            throw new RuntimeException("Could not read bookings", e);
        }
    }

    // Write all bookings to the file
    private void writeBookings(CarBooking[] bookings) {

        try (FileOutputStream fileOutput = new FileOutputStream(filepath);
             ObjectOutputStream output = new ObjectOutputStream(fileOutput)) {

            output.writeObject(bookings);

        } catch (IOException e) {

            throw new RuntimeException("Could not save bookings", e);
        }
    }

    // Save a booking
    @Override
    public void save(CarBooking booking) {

        if (booking == null) {
            throw new IllegalArgumentException("Booking cannot be null");
        }

        CarBooking[] existingBookings = readBookings();

        for (CarBooking existingBooking : existingBookings) {

            if (booking.getId() != null
                    && booking.getId().equals(existingBooking.getId())) {

                throw new IllegalArgumentException("Booking already exists");
            }
        }

        CarBooking[] updatedBookings = Arrays.copyOf(
                existingBookings,
                existingBookings.length + 1
        );

        updatedBookings[updatedBookings.length - 1] = booking;

        writeBookings(updatedBookings);
    }

    // Find all bookings
    @Override
    public CarBooking[] findAll() {
        return readBookings();
    }

    // Find booking by ID
    @Override
    public Optional<CarBooking> findById(UUID id) {

        if (id == null) {
            return Optional.empty();
        }

        CarBooking[] bookings = readBookings();

        for (CarBooking booking : bookings) {

            if (id.equals(booking.getId())) {
                return Optional.of(booking);
            }
        }

        return Optional.empty();
    }

    // Find bookings belonging to a user
    @Override
    public CarBooking[] findByUserId(UUID userId) {

        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        CarBooking[] bookings = readBookings();

        CarBooking[] matches = new CarBooking[bookings.length];

        int count = 0;

        for (CarBooking booking : bookings) {

            if (userId.equals(booking.getUserId())) {
                matches[count] = booking;
                count++;
            }
        }

        return Arrays.copyOf(matches, count);
    }

    // Find bookings belonging to a car
    @Override
    public CarBooking[] findByCarId(UUID carId) {

        if (carId == null) {
            throw new IllegalArgumentException("Car ID cannot be null");
        }

        CarBooking[] bookings = readBookings();

        CarBooking[] matches = new CarBooking[bookings.length];

        int count = 0;

        for (CarBooking booking : bookings) {

            if (carId.equals(booking.getCarId())) {
                matches[count] = booking;
                count++;
            }
        }

        return Arrays.copyOf(matches, count);
    }

    // Delete booking by ID
    @Override
    public Optional<CarBooking> deleteById(UUID id) {

        if (id == null) {
            return Optional.empty();
        }

        CarBooking[] bookings = readBookings();

        for (int i = 0; i < bookings.length; i++) {

            CarBooking booking = bookings[i];

            if (id.equals(booking.getId())) {

                CarBooking[] updatedBookings = new CarBooking[
                        bookings.length - 1
                        ];

                System.arraycopy(
                        bookings, 0,
                        updatedBookings, 0,
                        i
                );

                System.arraycopy(
                        bookings, i + 1,
                        updatedBookings, i,
                        bookings.length - i - 1
                );

                writeBookings(updatedBookings);

                return Optional.of(booking);
            }
        }

        return Optional.empty();
    }
}
