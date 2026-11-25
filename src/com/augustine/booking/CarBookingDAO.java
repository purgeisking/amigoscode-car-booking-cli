package com.augustine.booking;

import java.util.Optional;
import java.util.UUID;

public interface CarBookingDAO {
    public void save(CarBooking booking);
    public CarBooking[] findAll();
    public Optional<CarBooking> findById(UUID id);
    public CarBooking[] findByUserId(UUID userId);
    public CarBooking[] findByCarId(UUID carId);
    public Optional<CarBooking> deleteById(UUID id);
}
