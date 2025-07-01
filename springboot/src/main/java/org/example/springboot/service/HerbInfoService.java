package org.example.springboot.service;

import jakarta.annotation.Resource;
import org.example.springboot.entity.HerbInfo;
import org.example.springboot.mapper.HerbInfoMapper;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class HerbInfoService {

    @Resource
    private HerbInfoMapper herbInfoMapper;

    public List<HerbInfo> selectAll(){
        return herbInfoMapper.selectAll();
    }

    public HerbInfo selectById(Integer id) {
        return herbInfoMapper.selectById(id);
    }

    public List<HerbInfo> searchHerbs(String keyword) {
        return herbInfoMapper.searchHerbs("%" + keyword + "%");
    }
}
