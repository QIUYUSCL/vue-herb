package org.example.springboot.mapper;

import org.example.springboot.entity.HerbCategory;

import java.util.List;

public interface HerbCategoryMapper {
    List<HerbCategory> selectAll();
}
