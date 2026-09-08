package com.panthars.blue_reserve.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.panthars.blue_reserve.dto.BookingRequest;
import com.panthars.blue_reserve.exception.BookingConflictException;
import com.panthars.blue_reserve.mapper.BookingMapper;
import com.panthars.blue_reserve.model.Cafeteria;
import com.panthars.blue_reserve.model.Location;
import com.panthars.blue_reserve.model.Seat;
import com.panthars.blue_reserve.model.Slot;
import com.panthars.blue_reserve.model.User;
import com.panthars.blue_reserve.repository.BookingRepository;
import com.panthars.blue_reserve.repository.CafeteriaRepository;
import com.panthars.blue_reserve.repository.LocationRepository;
import com.panthars.blue_reserve.repository.SeatRepository;
import com.panthars.blue_reserve.repository.SlotRepository;
import com.panthars.blue_reserve.repository.UserRepository;
import com.panthars.blue_reserve.utils.BookingStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private SeatRepository seatRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private SlotRepository slotRepository;
    @Mock
    private CafeteriaRepository cafeteriaRepository;
    @Mock
    private LocationRepository locationRepository;
    @Mock
    private BookingMapper bookingMapper;

    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        bookingService = new BookingService(
            bookingRepository,
            seatRepository,
            userRepository,
            slotRepository,
            cafeteriaRepository,
            locationRepository,
            bookingMapper
        );
    }

    @Test
    void rejectsDuplicateReservationForSameSeatDateAndSlot() {
        BookingRequest request = request();
        User employee = new User();
        User manager = new User();
        Seat seat = new Seat();
        Cafeteria cafeteria = new Cafeteria();
        Location location = new Location();
        Slot slot = new Slot();

        seat.setId(10L);
        seat.setCafeteria(cafeteria);
        cafeteria.setId(20L);
        cafeteria.setLocation(location);
        location.setId(30L);
        slot.setId(40L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(userRepository.findById(2L)).thenReturn(Optional.of(manager));
        when(seatRepository.findById(10L)).thenReturn(Optional.of(seat));
        when(slotRepository.findById(40L)).thenReturn(Optional.of(slot));
        when(cafeteriaRepository.findById(20L)).thenReturn(Optional.of(cafeteria));
        when(locationRepository.findById(30L)).thenReturn(Optional.of(location));
        when(bookingRepository.existsReservationForSlot(
            10L,
            request.getDate(),
            40L,
            List.of(BookingStatus.PENDING, BookingStatus.APPROVED)
        )).thenReturn(true);

        assertThrows(BookingConflictException.class, () -> bookingService.createBooking(request));
    }

    private BookingRequest request() {
        BookingRequest request = new BookingRequest();
        request.setUserId(1L);
        request.setManagerId(2L);
        request.setSeatId(10L);
        request.setSlotId(40L);
        request.setCafeteriaId(20L);
        request.setLocationId(30L);
        request.setDate(LocalDate.now().plusDays(1));
        return request;
    }
}
