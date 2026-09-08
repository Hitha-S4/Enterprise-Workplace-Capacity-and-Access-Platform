package com.panthars.blue_reserve.dto;

import lombok.Data;
import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.FutureOrPresent;

@Data
public class BookingRequest {
    @NotNull
    private Long userId;
    @NotNull
    private Long managerId;
    @NotNull
    private Long seatId;
    @NotNull
    private Long slotId;
    @NotNull
    private Long cafeteriaId;
    @NotNull
    private Long locationId;
    @NotNull
    @FutureOrPresent
    private LocalDate date;
    
}