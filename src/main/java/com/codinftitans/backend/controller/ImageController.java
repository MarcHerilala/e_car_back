package com.codinftitans.backend.controller;

import com.codinftitans.backend.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
public class ImageController {
    @Autowired
ImageService imageService;
    @PostMapping("/upload/image/{idProduct}")
public String uploadImages(@RequestPart MultipartFile image,@PathVariable UUID idProduct) throws IOException {
/*for(MultipartFile image:images){
    imageService.saveImageToStorage(image);

}*/
return  imageService.saveImageToStorage(image,idProduct);


}
@GetMapping("/view/image")
public byte [] getImages(@RequestParam(name = "name") String imageName) throws IOException {
        return  imageService.getImage(imageName);
}

}
