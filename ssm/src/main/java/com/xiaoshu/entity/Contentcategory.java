package com.xiaoshu.entity;

import java.util.Date;

public class Contentcategory {
    private Integer contentcategoryid;

    private String categoryname;

    private Integer status;

    private Date createtime;

    public Integer getContentcategoryid() {
        return contentcategoryid;
    }

    public void setContentcategoryid(Integer contentcategoryid) {
        this.contentcategoryid = contentcategoryid;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname == null ? null : categoryname.trim();
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