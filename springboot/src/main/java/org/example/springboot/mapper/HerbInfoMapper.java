package org.example.springboot.mapper;

import org.example.springboot.entity.HerbInfo;

import java.util.List;

public interface HerbInfoMapper {
    List<HerbInfo> selectAll();

    HerbInfo selectById(Integer id);

    List<HerbInfo> searchHerbs(String keyword);

    /**
     * 更新药材点赞数
     * @param herbId 药材 ID
     * @return 更新成功的记录数
     */
    int updateLikes(int herbId);

    int decreaseLikes(int herbId);

    int decreaseCollections(int herbId);

    /**
     * 更新药材收藏数
     * @param herbId 药材 ID
     * @return 更新成功的记录数
     */
    int updateCollections(int herbId);

    int updateViews(int herbId);
}
