package com.bookingSystem.timeSlot;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "time_slot")
public class TimeSlot {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "timeslot_seq")
    @Column(name = "id")
    private Integer id;

    private Integer startTime;
    private Integer endTime;
}
