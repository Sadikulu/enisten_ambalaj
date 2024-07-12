package com.kss.controller;

import com.kss.domains.ImageFile;
import com.kss.dto.ImageFileDTO;
import com.kss.dto.response.KSSResponse;
import com.kss.dto.response.ImageSavedResponse;
import com.kss.dto.response.ResponseMessage;
import com.kss.service.ImageFileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/image")
@AllArgsConstructor
public class ImageFileController {

    private ImageFileService imageFileService;

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<ImageSavedResponse> uploadFile(@RequestParam("image") MultipartFile[] image){
       Set<String> images = imageFileService.saveImage(image);
       ImageSavedResponse response=new ImageSavedResponse
               (images,ResponseMessage.IMAGE_SAVED_RESPONSE_MESSAGE,true);
       return ResponseEntity.ok(response);
    }

    @PatchMapping("/showcase")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<KSSResponse> setShowcaseImage(@RequestParam("productId") Long productId,
                                                        @RequestParam("imageId") String imageId){
        imageFileService.setShowcaseImage(productId,imageId);
        KSSResponse response = new KSSResponse(ResponseMessage.IMAGE_SHOWCASE_RESPONSE_MESSAGE,true,null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> getImageFile(@PathVariable String id){
        ImageFile imageFile=imageFileService.getImageById(id);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename="+imageFile.getName()).body(imageFile.getImageData().getData());
    }

    @GetMapping("/display/{id}")
    public ResponseEntity<byte[]>displayImage(@PathVariable String id){
        ImageFile imageFile= imageFileService.getImageById(id);
        HttpHeaders header=new HttpHeaders();
        header.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(imageFile.getImageData().getData(), header, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ImageFileDTO>> getAllImages(){
        List<ImageFileDTO> imageList=imageFileService.getAllImages();
        return ResponseEntity.ok(imageList);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<KSSResponse>deleteImageFile(@PathVariable String id){
        imageFileService.removeById(id);
        KSSResponse response =new KSSResponse(ResponseMessage.IMAGE_DELETE_RESPONSE_MESSAGE,true);
                return ResponseEntity.ok(response);
    }
}
