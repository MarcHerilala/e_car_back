package com.codinftitans.backend.service;

import com.codinftitans.backend.dto.base.AppointmentBaseDTO;
import com.codinftitans.backend.dto.request.AppointmentRequestDTO;
import com.codinftitans.backend.dto.response.AppointmentResponseDTO;
import com.codinftitans.backend.dto.response.CarResponseDTO;
import com.codinftitans.backend.model.Appointment;
import com.codinftitans.backend.model.Car;
import com.codinftitans.backend.repository.AppointmentRepository;
import com.codinftitans.backend.repository.CarRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppointmentService {
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    ModelMapper mapper;
    @Autowired
    MailService mailService;
    @Autowired
    CarRepository carRepository;
    public List<AppointmentResponseDTO> findAll(){
        List<AppointmentResponseDTO> appointments=appointmentRepository.findAll()
                .stream().map(appointment ->{
                    AppointmentResponseDTO appointmentResponse=mapper.map(appointment,AppointmentResponseDTO.class);
                    return appointmentResponse;
                        }
                ).toList();
       return  appointments;
    }
    public Appointment newAppointment(AppointmentRequestDTO appointment){
        Appointment newAppointment=new Appointment();
        newAppointment.setIdUser(appointment.getIdUser());
        newAppointment.setIdCar(appointment.getIdCar());
        newAppointment.setAppointmentDate(appointment.getAppointmentDate());
        newAppointment.setMessage(appointment.getMessage());
        newAppointment.setStatus(appointment.getStatus());
        appointmentRepository.save(newAppointment);

        return newAppointment;
    }
    public List<AppointmentResponseDTO> getAppointmentByUser(UUID idUser){

        return appointmentRepository.getAppointmentByUser(idUser).stream()
                .map(appointment -> {
                    AppointmentResponseDTO appointmentResponseDTO=mapper.map(appointment,AppointmentResponseDTO.class);
                return  appointmentResponseDTO;
                }).toList();
    }
    @Transactional
    public String updateStatus(String value, UUID id){
        Optional<Appointment> appointment=appointmentRepository.findById(id);
        String email=appointment.get().getUser().getEmail();
        String textToSend="Chere "+appointment.get().getUser().getName()+"\n," +
                "Nous avons le plaisir de vous informé que le rendez-vous avec la voiture "+
                appointment.get().getCar().getModel()+" " +
                "prevu pour la " +
                "date de "+appointment.get().getAppointmentDate()+" a été validé ";
        if(value.equals("valide")){
            mailService.sendMessage(email,textToSend);
        }
        appointmentRepository.updateStatusById(value,id);
        return "updated successfully";
    }
}
