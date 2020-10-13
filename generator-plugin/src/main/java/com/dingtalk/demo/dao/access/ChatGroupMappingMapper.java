package com.dingtalk.demo.dao.access;

import com.dingtalk.demo.domain.access.ChatGroupMapping;
import com.dingtalk.demo.domain.access.ChatGroupMappingCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ChatGroupMappingMapper {
    long countByExample(ChatGroupMappingCriteria example);

    int deleteByExample(ChatGroupMappingCriteria example);

    int deleteByPrimaryKey(@Param("orgId") Long orgId, @Param("code") String code);

    int insert(ChatGroupMapping record);

    int insertSelective(ChatGroupMapping record);

    List<ChatGroupMapping> selectByExample(ChatGroupMappingCriteria example);

    ChatGroupMapping selectByPrimaryKey(@Param("orgId") Long orgId, @Param("code") String code);

    int updateByExampleSelective(@Param("record") ChatGroupMapping record, @Param("example") ChatGroupMappingCriteria example);

    int updateByExample(@Param("record") ChatGroupMapping record, @Param("example") ChatGroupMappingCriteria example);

    int updateByPrimaryKeySelective(ChatGroupMapping record);

    int updateByPrimaryKey(ChatGroupMapping record);
}