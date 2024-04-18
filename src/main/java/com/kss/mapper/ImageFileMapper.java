package com.kss.mapper;

import com.kss.domains.ImageFile;
import com.kss.dto.ShowcaseImageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ImageFileMapper {

    @Mapping(source = "id",target = "imageId")
    ShowcaseImageDTO imageFileToShowcaseImageDTO(ImageFile imageFile);
}
