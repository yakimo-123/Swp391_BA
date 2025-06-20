package org.Scsp.com.repository;

import org.Scsp.com.dto.BookingDTO;
import org.Scsp.com.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Book;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findBookingByUser_UserId(Long userId);
}
