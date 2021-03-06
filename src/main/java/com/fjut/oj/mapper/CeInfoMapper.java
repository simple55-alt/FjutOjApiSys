package com.fjut.oj.mapper;

import com.fjut.oj.pojo.CeInfoPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author axiang [20190815]
 */
public interface CeInfoMapper {
    /**
     * 插入评测信息
     *
     * @param ce
     * @return
     */
    Integer insertCeInfo(@Param("ce") CeInfoPO ce);

    /**
     * 查询全部评测信息
     *
     * @return
     */
    List<CeInfoPO> queryAllCeInfo();

    /**
     * 查询评测信息
     *
     * @param rid
     * @return
     */
    CeInfoPO queryCeInfo(@Param("rid") Integer rid);


}
