package com.asiainfo.mybatis.springboot.annotation.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.asiainfo.mybatis.entity.Card;
import com.asiainfo.mybatis.entity.Course;
import com.asiainfo.mybatis.entity.User;
import com.asiainfo.mybatis.springboot.annotation.provider.UserSqlProvider;

/**
 * @Description: 可以每个dao加 @Mapper 注解，或者在application上加 @MapperScan("com.asiainfo.mybatis.springboot.annotation.dao")
 *              - 注解不支持给ResultMap设置id，这样多个相同的ResultMap需要重复定义，也不能使用extends
 *              - 目前版本@One/@Many 还没有支持嵌套查询，只能用select进行关联查询(N + 1)，或者定义Mapper.xml再引用Mapper的ResultMap
 * 
 * @author chenzq  
 * @date 2019年5月2日 下午8:39:20
 * @version V1.0
 * @Copyright: Copyright(c) 2019 jaesonchen.com Inc. All rights reserved.
 */
@Repository
//@Mapper
public interface UserDao {
    
    @SelectProvider(type = UserSqlProvider.class, method = "findAllSql")
    @Results({
        @Result(id = true, property = "userId", column = "user_id"),
        @Result(property = "userName", column = "user_name"),
        @Result(property = "card", column = "card_id", javaType = Card.class, 
                one = @One(select = "findCard"))
    })
    List<User> findAll();

    @Options(useCache = false)
    @Select("select * from user where user_id=#{id}")
    @Results({
            @Result(id = true, property = "userId", column = "user_id"),
            @Result(property = "userName", column = "user_name"), 
            @Result(property = "card", column = "card_id", 
                    one = @One(select = "findCard")), 
            @Result(property = "courses", column = "user_id", 
            many = @Many(select = "findCourse"))
    })
    User findById(@Param("id") long userId);
    
    @Select("select * from card where card_id=#{id}")
    @Results({
            @Result(id = true, property = "cardId", column = "card_id"),
            @Result(property = "cardAgent", column = "card_agent"), 
            @Result(property = "authDate", column = "auth_date")
    })
    Card findCard(@Param("id") String cardId);
    
    @Select("select c.course_id, c.course_name from user_course uc join course c on uc.course_id=c.course_id where uc.user_id=#{id}")
    @Results({
            @Result(id = true, property = "courseId", column = "course_id"),
            @Result(property = "courseName", column = "course_name")
    })
    List<Course> findCourse(@Param("id") String userId);
    
    // oracle sequence
    //@SelectKey(statement="SELECT STUD_ID_SEQ.NEXTVAL FROM DUAL", keyProperty="user_id", resultType=int.class, before=true)  
    @Options(useGeneratedKeys = true, keyProperty="userId", keyColumn="user_id")
    @Insert("insert into user(user_name, card_id) values(#{userName}, #{card.cardId})")
    int insert(User user);
    
    @Insert("insert into card(card_id, card_agent, auth_date) values (#{cardId}, #{cardAgent}, #{authDate})")
    int insertCard(Card card);
    
    @Insert("insert into user(user_name, card_id) values(#{userName, jdbcType=VARCHAR}, #{cardId, jdbcType=VARCHAR})")
    int insertByMap(Map<String, Object> map);
    
    @Update("update user set user_name=#{userName} where user_id=#{userId}")
    int update(User user);

    @Delete("delete from user where user_id=#{id}")
    int delete(@Param("id") long userId);
    
    @Delete("delete from user where user_name like CONCAT(#{name}, '%')")
    int deleteByName(@Param("name") String userName);
    
    @Delete("delete from card where card_id=#{id}")
    int deleteCard(@Param("id") String cardId);
}
