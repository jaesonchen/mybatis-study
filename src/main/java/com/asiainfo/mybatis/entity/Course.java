package com.asiainfo.mybatis.entity;

import java.io.Serializable;

/**   
 * @Description: TODO
 * 
 * @author chenzq  
 * @date 2019年5月7日 下午3:06:31
 * @version V1.0
 * @Copyright: Copyright(c) 2019 jaesonchen.com Inc. All rights reserved. 
 */
@SuppressWarnings("serial")
public class Course implements Serializable {

    private long courseId;
    private String courseName;
    public long getCourseId() {
        return courseId;
    }
    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }
    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    @Override
    public String toString() {
        return "Course [courseId=" + courseId + ", courseName=" + courseName + "]";
    }
}
