package com.codinftitans.backend.dto.request;

import com.codinftitans.backend.dto.base.AppointmentBaseDTO;
import lombok.Data;

import java.util.UUID;
@Data
public class AppointmentRequestDTO extends AppointmentBaseDTO {
    private UUID idUser;
    private UUID idCar;
}
