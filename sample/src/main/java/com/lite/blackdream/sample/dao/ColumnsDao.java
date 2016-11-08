package com.lite.blackdream.sample.dao;

import com.lite.blackdream.sample.MyBatisRepository;
import com.lite.blackdream.sample.po.ColumnsPo;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * @author LaineyC
 */
@MyBatisRepository
public interface ColumnsDao {

    List<ColumnsPo> selectList(@Param("po")ColumnsPo po);

}
