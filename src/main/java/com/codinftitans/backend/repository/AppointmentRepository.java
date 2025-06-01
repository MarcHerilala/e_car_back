package com.codinftitans.backend.repository;

import com.codinftitans.backend.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    @Modifying
    @Query(value = "update appointment set status=:status where id=:id",nativeQuery = true)
     void updateStatusById(@Param("status")String status,@Param("id") UUID id);
    @Modifying
    @Query(value = "delete from appointment where id_car=?",nativeQuery = true)
    void deleteAppointmentByCar(@Param("idCar") UUID idCar);
    @Query(value = "select * from appointment where id_user=?",nativeQuery = true)
    List<Appointment> getAppointmentByUser(@Param("idUser") UUID idUser);

}
