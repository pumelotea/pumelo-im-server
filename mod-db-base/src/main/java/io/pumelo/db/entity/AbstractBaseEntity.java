package io.pumelo.db.entity;



import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 实体初始化数据，包括操作人，更新时间
 */
@MappedSuperclass
public abstract class AbstractBaseEntity implements Serializable {

    @Column(updatable = false)
    @CreatedDate
    protected Long createdAt; // 创建时间，UNIX时间戳，秒
    @Column
    @LastModifiedDate
    protected Long updatedAt;  // 修改时间，UNIX时间戳，秒
    @Column(updatable = false, length = 200)
    @CreatedBy
    protected String createdBy;
    @Column(length = 200)
    @LastModifiedBy
    protected String updateBy;
    @Column
    protected String remark; // 备注
    @Version
    protected int version;

    @Column
    protected Boolean isTrash;

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Boolean getTrash() {
        return isTrash;
    }

    public void setTrash(Boolean trash) {
        isTrash = trash;
    }

    @PrePersist
    public void prePersist() {
        this.isTrash = false;
        this.updatedAt = this.createdAt = System.currentTimeMillis()/1000;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = System.currentTimeMillis()/1000;
    }

}
