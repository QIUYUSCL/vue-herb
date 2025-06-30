package org.example.springboot.service;

import jakarta.annotation.Resource;
import org.example.springboot.entity.HerbCategory;
import org.example.springboot.mapper.HerbCategoryMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HerbCategoryService {

    @Resource
    private HerbCategoryMapper herbCategoryMapper;


    public List<HerbCategory> selectAll() {
        return herbCategoryMapper.selectAll();
    }
}
