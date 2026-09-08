package com.panthars.blue_reserve.controller;

import com.panthars.blue_reserve.dto.BookingRequest;
import com.panthars.blue_reserve.dto.BookingResponse;
import com.panthars.blue_reserve.mapper.BookingMapper;
import com.panthars.blue_reserve.model.Booking;
import com.panthars.blue_reserve.service.BookingService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final BookingMapper bookingMapper;

    @Autowired
    public BookingController(BookingService bookingService, BookingMapper bookingMapper) {
        this.bookingService = bookingService;
        this.bookingMapper = bookingMapper;
    }

    // Fetch all bookings
    @GetMapping
    public ResponseEntity<List<BookingResponse>> getAllBookings() {
        List<BookingResponse> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    // Create booking
    @PostMapping
    public ResponseEntity<Void> createBooking(@Valid @RequestBody BookingRequest request) {
        bookingService.createBooking(request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{bookingId}/approve")
    public ResponseEntity<BookingResponse> approveBooking(@PathVariable Long bookingId) {
        Booking booking = bookingService.approveBooking(bookingId);
        return ResponseEntity.ok(bookingMapper.toResponse(booking));
    }

    @PatchMapping("/{bookingId}/reject")
    public ResponseEntity<BookingResponse> rejectBooking(@PathVariable Long bookingId) {
        Booking booking = bookingService.rejectBooking(bookingId);
        return ResponseEntity.ok(bookingMapper.toResponse(booking));
    }



    // Check seat availability
    @GetMapping("/checkAvailability")
    public ResponseEntity<Boolean> checkSeatAvailability(@RequestParam Long seatId) {
        boolean available = bookingService.isSeatAvailable(seatId);
        return ResponseEntity.ok(available);
    }

    // Get bookings by user
    @GetMapping("/userBookings")
    public ResponseEntity<List<BookingResponse>> getBookingsByUser(@RequestParam String email) {
        List<BookingResponse> bookings = bookingService.getBookingsByUser(email);
        return ResponseEntity.ok(bookings);
    }

    // Get bookings by seat
    @GetMapping("/seatBookings")
    public ResponseEntity<List<Booking>> getBookingsBySeat(@RequestParam Long seatId) {
        List<Booking> bookings = bookingService.getBookingsBySeat(seatId);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/manager/{managerId}/approved")
    public List<BookingResponse> getApprovedBookings(@PathVariable Long managerId) {
        return bookingService.getApprovedBookingsForManager(managerId);
    }
    
    @GetMapping("/manager/{managerId}/pending")
    public List<BookingResponse> getPendingApprovals(@PathVariable Long managerId) {
        return bookingService.getPendingApprovals(managerId);
    }
}
