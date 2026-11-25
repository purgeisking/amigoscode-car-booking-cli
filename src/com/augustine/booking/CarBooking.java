package com.augustine.booking;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class CarBooking implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;
    private UUID userId;
    private UUID carId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal price;
    private BookingStatus status;
    private LocalDateTime bookedAt;

    public CarBooking(UUID id,
                      UUID userId,
                      UUID carId,
                      LocalDate startDate,
                      LocalDate endDate,
                      BigDecimal price,
                      BookingStatus status,
                      LocalDateTime bookedAt) {

        this.id = id;
        this.userId = userId;
        this.carId = carId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.status = status;
        this.bookedAt = bookedAt;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getCarId() {
        return carId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarBooking that = (CarBooking) o;

        return Objects.equals(id, that.id)
                && Objects.equals(userId, that.userId)
                && Objects.equals(carId, that.carId)
                && Objects.equals(startDate, that.startDate)
                && Objects.equals(endDate, that.endDate)
                && Objects.equals(price, that.price)
                && status == that.status
                && Objects.equals(bookedAt, that.bookedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id, userId, carId, startDate,
                endDate, price, status, bookedAt
        );
    }

    @Override
    public String toString() {
        return "CarBooking{" +
                "id=" + id +
                ", userId=" + userId +
                ", carId=" + carId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", price=" + price +
                ", status=" + status +
                ", bookedAt=" + bookedAt +
                '}';
    }
}
