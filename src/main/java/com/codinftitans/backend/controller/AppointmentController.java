package com.codinftitans.backend.controller;

import com.amazonaws.Response;
import com.codinftitans.backend.dto.request.AppointmentRequestDTO;
import com.codinftitans.backend.dto.response.AppointmentResponseDTO;
import com.codinftitans.backend.model.Appointment;
import com.codinftitans.backend.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class AppointmentController {
    @Autowired
    AppointmentService appointmentService;
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentResponseDTO>>findAll(){
        List<AppointmentResponseDTO> appointments=appointmentService.findAll();
        long appointCount=appointments.size();
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.add("X-Total-Count",String.valueOf(appointCount));
        return new ResponseEntity<>(appointments,httpHeaders, HttpStatus.OK);
    }
    @GetMapping("/appointment/{idUser}")
    public List<AppointmentResponseDTO> getAppointmentByUser(@PathVariable UUID idUser){
        return appointmentService.getAppointmentByUser(idUser);
    }
    @PreAuthorize("hasAuthority('SCOPE_CLIENT')")
    @PostMapping("/appointment/new")
    public Appointment newAppointment(@RequestBody AppointmentRequestDTO appointment){
        return appointmentService.newAppointment(appointment);
    }
    @PutMapping("/appointment/update/{id}")
    public String updateStatus(@PathVariable UUID id,@RequestBody String status){
        return appointmentService.updateStatus(status,id);
    }
}
