package com.codinftitans.backend.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentBaseDTO {

    private String message;
    private Instant appointmentDate;
    private String status;
}
