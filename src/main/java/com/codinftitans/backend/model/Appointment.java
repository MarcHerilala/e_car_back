package com.codinftitans.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
            @GeneratedValue(strategy = GenerationType.UUID)
   private UUID id;
   @ManyToOne
   @JoinColumn(name = "id_user",insertable = false,updatable = false,referencedColumnName = "id")
   private User user;
   @Column(name = "id_user")
   private UUID idUser;
    private String message;
   private Instant appointmentDate;
   private String Status;
    @ManyToOne
    @JoinColumn(name = "id_car",insertable = false,updatable = false,referencedColumnName = "id")
   private Car car;
    @Column(name = "id_car")
    private UUID idCar;

}
