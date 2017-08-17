package com.neucloud.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by wangcj on 2017/8/16.
 */

@Entity
public class WindResult {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private float windSpeed;

    private float validPower;

    private float lineUp;

    private float lineDown;

    private Date createTime;

    public WindResult(float windSpeed, float validPower, float lineUp, float lineDown) {
        this.windSpeed = windSpeed;
        this.validPower = validPower;
        this.lineUp = lineUp;
        this.lineDown = lineDown;
        this.createTime = new Date();
    }

    public WindResult() {
        this.createTime = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public float getValidPower() {
        return validPower;
    }

    public void setValidPower(float validPower) {
        this.validPower = validPower;
    }

    public float getLineUp() {
        return lineUp;
    }

    public void setLineUp(float lineUp) {
        this.lineUp = lineUp;
    }

    public float getLineDown() {
        return lineDown;
    }

    public void setLineDown(float lineDown) {
        this.lineDown = lineDown;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
