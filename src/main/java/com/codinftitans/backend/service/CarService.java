package com.codinftitans.backend.service;

import com.codinftitans.backend.dto.request.CarRequestDTO;
import com.codinftitans.backend.dto.response.BrandResponseDTO;
import com.codinftitans.backend.dto.response.CarResponseDTO;
import com.codinftitans.backend.model.Car;
import com.codinftitans.backend.repository.CarPicRepository;
import com.codinftitans.backend.repository.CarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CarService {
    @Autowired
   private CarRepository carRepository;

    private CarPicRepository carPicRepository;

    @Autowired
    private ModelMapper mapper;

    public CarRequestDTO saveNewCar(CarRequestDTO car){

        Car newCar=this.mapper.map(car,Car.class);
        carRepository.save(newCar);
        return  car;
    }
    public List<CarResponseDTO> findAllCar(){
        List<CarResponseDTO> cars=carRepository.findAll().stream()
                .map(car -> this.mapper.map(car,CarResponseDTO.class)).toList();
        return cars;
    }
    public String deleteById(UUID idCar){
        carRepository.deleteById(idCar);
        return "deleted successfullly";
    }

   /* public List<ProductDTO> filterByName(String nameChar){
        List<ProductDTO> products=findAllProduct().stream().filter(product ->
                product.getName().toLowerCase().contains(nameChar)
                ).toList();
        return products;
    }*/
    public List<BrandResponseDTO> findALlBrand(){

       return carRepository.findAllBrand().stream()
               .map(car->mapper.map(car,BrandResponseDTO.class)).toList();
    }
}
