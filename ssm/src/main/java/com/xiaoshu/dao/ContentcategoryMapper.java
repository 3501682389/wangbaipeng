package com.xiaoshu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xiaoshu.entity.Contentcategory;
import com.xiaoshu.entity.ContentcategoryExample;


public interface ContentcategoryMapper  {
	
    int countByExample(ContentcategoryExample example);

    int deleteByExample(ContentcategoryExample example);

    int deleteByPrimaryKey(Integer contentcategoryid);

    int insert(Contentcategory record);

    int insertSelective(Contentcategory record);

    List<Contentcategory> selectByExample(ContentcategoryExample example);

    Contentcategory selectByPrimaryKey(Integer contentcategoryid);

    int updateByExampleSelective(@Param("record") Contentcategory record, @Param("example") ContentcategoryExample example);

    int updateByExample(@Param("record") Contentcategory record, @Param("example") ContentcategoryExample example);

    int updateByPrimaryKeySelective(Contentcategory record);

    int updateByPrimaryKey(Contentcategory record);

	Contentcategory fandcategory(Contentcategory contentcategory);


	
}