package com.codinftitans.backend.controller;

import com.codinftitans.backend.dto.request.CarRequestDTO;
import com.codinftitans.backend.dto.response.CarResponseDTO;
import com.codinftitans.backend.dto.response.NonDetailedCarDTO;
import com.codinftitans.backend.model.Brand;
import com.codinftitans.backend.model.Car;
import com.codinftitans.backend.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController

public class CarController {
    @Autowired
    private CarService carService;
    @GetMapping("/car/{id}")
    public Optional<CarResponseDTO> findById(@PathVariable UUID id){
        return carService.findCarById(id);
    }
    @GetMapping("cars")
    public ResponseEntity<List<NonDetailedCarDTO>> filter(@RequestParam (name = "query",required = false) String query){
        List<NonDetailedCarDTO>cars= carService.filterCar(query);
        long carCount=cars.size();
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.add("X-Total-Count",String.valueOf(carCount));
        return new ResponseEntity<>(cars,httpHeaders,HttpStatus.OK);
    }


    @GetMapping("cars/page")
    public ResponseEntity<List<NonDetailedCarDTO>> findcarsBypage(@RequestParam int pageNumber){
        List<NonDetailedCarDTO> cars=carService.findAllByPage(pageNumber);
        long carCount=cars.size();
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.add("X-Total-Count",String.valueOf(carCount));
        return new ResponseEntity<>(carService.findAllByPage(pageNumber),httpHeaders, HttpStatus.OK);
    }

    @GetMapping("cars/{brand}")
    public List<NonDetailedCarDTO> findCarsByBrand(@PathVariable String brand, @RequestParam int pageNumber){
        return carService.findNonDetailedCars(brand,pageNumber);
    }
    @GetMapping("/brands")
    public List<Brand> findAllBrand(){
        return carService.findALlBrand();
    }
    @GetMapping("/cars/pinned")
    public List<CarResponseDTO> pinnedCars(){
        return carService.pinnedCars();
    }
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    @PostMapping("/car/new")
    public CarResponseDTO newCar(@RequestBody CarRequestDTO carDTO){
        return carService.saveNewCar(carDTO);
    }
    @PutMapping("/car/pin/{id}")
    public String pinCar(@PathVariable UUID id,@RequestParam("pin") boolean pin){
        return carService.pinCar(pin,id);
    }
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    @DeleteMapping("car/delete")
    public String deleteCar(@RequestParam("id") UUID carId){
        return carService.deleteById(carId);
    }

}
