package org.example.springboot.service;

import jakarta.annotation.Resource;
import org.example.springboot.entity.HerbInfo;
import org.example.springboot.entity.UserHistory;
import org.example.springboot.entity.UserInteraction;
import org.example.springboot.mapper.HerbInfoMapper;
import org.example.springboot.mapper.UserMapper;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HerbInfoService {

    @Resource
    private HerbInfoMapper herbInfoMapper;

    @Resource
    private UserMapper userMapper;

    public List<HerbInfo> selectAll(){
        return herbInfoMapper.selectAll();
    }

    public HerbInfo selectById(Integer id) {
        return herbInfoMapper.selectById(id);
    }

    public List<HerbInfo> searchHerbs(String keyword) {
        return herbInfoMapper.searchHerbs("%" + keyword + "%");
    }

    public List<HerbInfo> selectByLikes(int limit) {
        return herbInfoMapper.selectByLikes(limit);
    }

    public List<HerbInfo> selectByCollections(int limit) {
        return herbInfoMapper.selectByCollections(limit);
    }

}
