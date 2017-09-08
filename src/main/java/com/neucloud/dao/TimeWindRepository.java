package com.neucloud.dao;

import com.neucloud.entity.TimeWind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wangcj on 2017/9/7.
 */

@Repository("timeWindRepository")
public interface TimeWindRepository extends JpaRepository<TimeWind, Long>{

    @Query(value = "select id,time,wind_speed,power,label from label_traindata order by time asc limit 1000" ,nativeQuery = true)
    List<TimeWind> findTop1000();

    @Query(value = "select id,time,wind_speed,power,label from label_traindata order by time asc limit ?, 10" ,nativeQuery = true)
    List<TimeWind> findNext(int offset);

}
