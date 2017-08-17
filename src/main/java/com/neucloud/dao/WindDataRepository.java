package com.neucloud.dao;

import com.neucloud.entity.WindData;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wangcj on 2017/8/16.
 */

public interface WindDataRepository extends JpaRepository<WindData, Long> {
}
