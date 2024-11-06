package com.escass.advancedbbs.mappers;

import com.escass.advancedbbs.entities.ImageEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageMapper {
    int insertImage(ImageEntity image);
}
