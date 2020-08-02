package com.xiaoshu.entity;

import java.util.Date;

import javax.persistence.Temporal;

import org.springframework.format.annotation.DateTimeFormat;

public class Content {
    private Integer contentid;

    private Integer contentcategoryid;

    private String contenttitle;

    private String cantenturl;

    private String picpath;

    private Integer price;

    private Integer status;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createtime;
    
    private Contentcategory contentcategory;
    
    public Contentcategory getContentcategory() {
		return contentcategory;
	}

	public void setContentcategory(Contentcategory contentcategory) {
		this.contentcategory = contentcategory;
	}

	public Integer getContentid() {
        return contentid;
    }

    public void setContentid(Integer contentid) {
        this.contentid = contentid;
    }

    public Integer getContentcategoryid() {
        return contentcategoryid;
    }

    public void setContentcategoryid(Integer contentcategoryid) {
        this.contentcategoryid = contentcategoryid;
    }

    public String getContenttitle() {
        return contenttitle;
    }

    public void setContenttitle(String contenttitle) {
        this.contenttitle = contenttitle == null ? null : contenttitle.trim();
    }

    public String getCantenturl() {
        return cantenturl;
    }

    public void setCantenturl(String cantenturl) {
        this.cantenturl = cantenturl == null ? null : cantenturl.trim();
    }

    public String getPicpath() {
        return picpath;
    }

    public void setPicpath(String picpath) {
        this.picpath = picpath == null ? null : picpath.trim();
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

	
}