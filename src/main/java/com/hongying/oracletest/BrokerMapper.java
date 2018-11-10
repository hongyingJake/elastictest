package com.hongying.oracletest;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BrokerMapper {
	@Select("SELECT brokername FROM B_BROKER b where brokerid=#{brokerid}")
	String GetBrokerName(@Param("brokerid") String brokerid);
}
