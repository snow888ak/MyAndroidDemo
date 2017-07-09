package com.example.rxjavademo.model;

import com.example.rxjavademo.entity.Course;
import com.example.rxjavademo.entity.Student;

/**
 * Created by Snow.ZhK on 2017/7/9.
 */

public interface IStudentModel {

    /**
     * 获取所有学生
     * @return
     */
    Student[] getStudents();

    /**
     * 通过课程查找该课程下的所有学生
     * @param course
     * @return
     */
    Student[] getStudentsByCourse(Course course);

    /**
     * 获取所有课程
     * @return
     */
    Course[] getCourses();

    /**
     * 通过学生查找学生的课程
     * @param student
     * @return
     */
    Course[] getCourseByStudent(Student student);

}
