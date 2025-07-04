package org.Scsp.com.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.Scsp.com.Enum.BookingStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingID;

    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "ScheduleID", nullable = false)
    @JsonIgnore
    private Schedule schedule;

    @Column(name = "BookingDate")
    private LocalDate bookingDate = LocalDate.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    private BookingStatus status;

    @Column(name = "MeetingLink")
    private String meetingLink;

    @Column(name = "Notes", columnDefinition = "NVARCHAR(200)")
    private String notes;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "scheduled_time")
    private LocalDateTime scheduledTime; // 🟢 Chỉ giữ cột này, xóa cột "ScheduledTime" nếu có

    @PrePersist
    protected void onCreate() {
        if (bookingDate == null) bookingDate = LocalDate.now();
        if (createdAt == null) createdAt = LocalDateTime.now();
    }
}
