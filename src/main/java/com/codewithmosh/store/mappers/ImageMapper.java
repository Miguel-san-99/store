package com.codewithmosh.store.mappers;

import com.codewithmosh.store.dtos.ImageDto;
import com.codewithmosh.store.entities.Image;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    ImageDto toDto(Image image);
}
