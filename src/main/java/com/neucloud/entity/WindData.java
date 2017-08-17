package com.neucloud.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by wangcj on 2017/8/16.
 */

@Entity
public class WindData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private float windSpeed;

    private float powerValid;

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

    public float getPowerValid() {
        return powerValid;
    }

    public void setPowerValid(float powerValid) {
        this.powerValid = powerValid;
    }
}
