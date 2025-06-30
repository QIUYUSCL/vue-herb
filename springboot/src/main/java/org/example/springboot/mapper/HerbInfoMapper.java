package org.example.springboot.mapper;

import org.example.springboot.entity.HerbInfo;

import java.util.List;

public interface HerbInfoMapper {
    List<HerbInfo> selectAll();

    HerbInfo selectById(Integer id);
}
