package com.darakay.micro689.mappers;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper {

    String testMethod(int id);
}
