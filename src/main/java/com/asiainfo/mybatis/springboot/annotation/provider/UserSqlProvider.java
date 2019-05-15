package com.asiainfo.mybatis.springboot.annotation.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

/**   
 * @Description: mybatis provider 提供动态sql处理
 * 
 * 1. Mapper接口方法只有一个参数，那么可以定义SQLProvider方法，它接受一个与Mapper接口方法相同类型的参数。
 * 2. Mapper接口有多个输入参数，可以使用参数类型为java.util.Map的方法作为SQLprovider方法。
 *  - 映射器Mapper接口方法所有的输入参数将会被放到map中，以param1,param2等等作为key，将输入参数按序作为value。也可以使用0，1，2等作为key值来取的输入参数。
 * 
 * @author chenzq  
 * @date 2019年5月10日 上午10:01:41
 * @version V1.0
 * @Copyright: Copyright(c) 2019 jaesonchen.com Inc. All rights reserved. 
 */
public class UserSqlProvider {

    // @SelectProvider(type = UserSqlProvider.class, method = "findAllSql")
    public String findAllSql() {
        return "select * from user";
    }
    
    // @SelectProvider(type = UserSqlProvider.class, method = "findByIdSql")
    public String findByIdSql(long userId) {
        return "select * from user where user_id=" + userId;
    }
    
    // 使用SQL拼接语句
    public String findByIdSql1(long userId) {
        return new SQL() {
            {
                SELECT("*");
                FROM("user");  
                WHERE("user_id=" + userId); 
            }
        }.toString();
    }
    
    // 使用Map传入多个参数
    public String findByIdSql2(Map<String, Object> map) {
        return new SQL() {
            {
                SELECT("*");
                FROM("user");  
                WHERE("user_id=" + map.get("0")); 
            }
        }.toString();
    }
}
