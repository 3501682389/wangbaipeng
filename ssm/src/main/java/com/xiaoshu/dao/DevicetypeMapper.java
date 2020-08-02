package com.xiaoshu.dao;

import com.xiaoshu.entity.Devicetype;
import com.xiaoshu.entity.DevicetypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DevicetypeMapper {
    int countByExample(DevicetypeExample example);

    int deleteByExample(DevicetypeExample example);

    int deleteByPrimaryKey(Integer devicetypeid);

    int insert(Devicetype record);

    int insertSelective(Devicetype record);

    List<Devicetype> selectByExample(DevicetypeExample example);

    Devicetype selectByPrimaryKey(Integer devicetypeid);

    int updateByExampleSelective(@Param("record") Devicetype record, @Param("example") DevicetypeExample example);

    int updateByExample(@Param("record") Devicetype record, @Param("example") DevicetypeExample example);

    int updateByPrimaryKeySelective(Devicetype record);

    int updateByPrimaryKey(Devicetype record);
}