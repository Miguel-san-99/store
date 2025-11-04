package com.codewithmosh.store.services;

import com.codewithmosh.store.dtos.ImageDto;
import com.codewithmosh.store.dtos.ProductDto;
import com.codewithmosh.store.entities.Image;
import com.codewithmosh.store.mappers.ImageMapper;
import com.codewithmosh.store.mappers.ProductMapper;
import com.codewithmosh.store.repositories.ImageRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@RequiredArgsConstructor
@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final ProductService productService;
    private final ImageMapper imageMapper;
    private final ProductMapper productMapper;
    
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElse(null);
    }

    
    public List<ImageDto> createImages(List<MultipartFile> files, Long productId){
        ProductDto product = productService.getProductById(productId);
        List<ImageDto> imagesDto = new ArrayList<>();
        
        for (MultipartFile file : files){
            Image image = new Image();
            try {
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(file.getBytes());
                image.setProduct(productMapper.toEntity(product));
                image = imageRepository.save(image);
                String downloadUrl = "/api/images/download/";
                image.setDownloadUrl(downloadUrl + image.getId());
                imageRepository.save(image);
                imagesDto.add(imageMapper.toDto(image));
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return imagesDto;
    }
    
    public void updateImage(MultipartFile file, Long id) {
        var image = imageRepository.findById(id).orElse(null);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(file.getBytes());
            imageRepository.save(image);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    public boolean deleteImageById(Long id){
        var image = imageRepository.findById(id).orElse(null);
        if (image == null){
            return false;
        }
        imageRepository.delete(image);
        return true;
    }
}
