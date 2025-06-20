package org.Scsp.com.service.impl;

import org.Scsp.com.Enum.BookingStatus;
import org.Scsp.com.dto.BookingDTO;
import org.Scsp.com.dto.ScheduleDTO;
import org.Scsp.com.model.Booking;
import org.Scsp.com.model.Schedule;
import org.Scsp.com.model.Slot;
import org.Scsp.com.model.User;
import org.Scsp.com.repository.BookingRepository;
import org.Scsp.com.repository.ScheduleRepository;
import org.Scsp.com.repository.SlotRepository;
import org.Scsp.com.repository.UsersRepository;
import org.Scsp.com.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private ScheduleRepository scheduleRepo;
    @Autowired private BookingRepository bookingRepo;
    @Autowired private UsersRepository userRepo;
    @Autowired private SlotRepository slotRepository;

    @Override
    public List<ScheduleDTO> getAvailableSchedules(Long coachId, LocalDate date) {
        List<Schedule> schedules = scheduleRepo.findByCoachUserIdAndDateAndIsAvailableTrue(coachId, date);
        return schedules.stream().map(s -> new ScheduleDTO(
                s.getSchedulesID(),
                s.getCoach().getName(),
                s.getSlot().getLabel(),
                s.getDate(),
                s.isAvailable()
        )).collect(Collectors.toList());
    }


    @Override
    public Booking createBooking(Long userId, Long scheduleId, String note) {
        User user = userRepo.findById(userId).orElseThrow();
        Schedule schedule = scheduleRepo.findById(scheduleId).orElseThrow();

        if (!schedule.isAvailable()) throw new IllegalStateException("Schedule is not available!");

        // Đánh dấu schedule đã được đặt
        schedule.setAvailable(false);
        scheduleRepo.save(schedule);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setSchedule(schedule);
        booking.setMeetingLink("https://meet.google.com/abc-defg-hij");
        booking.setStatus(BookingStatus.BOOKED);
        booking.setNotes(note);
        return bookingRepo.save(booking);
    }
    @Override
    public Booking finishBooking(Long bookingId) {
        Booking booking = bookingRepo.findById(bookingId).orElseThrow();
        booking.setStatus(BookingStatus.FINISHED);
        return bookingRepo.save(booking);
    }

    @Override
    public List<BookingDTO> getBookingsByUserId(Long userId) {
        List<Booking> bookings = bookingRepo.findBookingByUser_UserId(userId);

        return bookings.stream().map(b -> {
            Slot slot = b.getSchedule().getSlot();
            return new BookingDTO(
                    b.getSchedule().getDate(),
                    b.getMeetingLink(),
                    b.getNotes(),
                    b.getStatus(),
                    slot.getStartTime(),
                    slot.getEndTime(),
                    b.getSchedule().getCoach().getName()
            );
        }).collect(Collectors.toList());
    }



}
