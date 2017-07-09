package com.example.rxjavademo.model;

import com.example.rxjavademo.entity.Course;
import com.example.rxjavademo.entity.Student;
import com.example.rxjavademo.entity.Study;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Snow.ZhK on 2017/7/9.
 */

public class StudentModelImpl implements IStudentModel {

    private List<Student> students = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();
    private List<Study> studies = new ArrayList<>();

    public StudentModelImpl(){
        initDatas();
    }

    private void initDatas(){
        for(int i = 0; i < 10; i++) {
            students.add(createStudent( i + 1, "学生：" + (i + 1)));
        }
        courses.add(createCourse(1, "语文"));
        courses.add(createCourse(2, "数学"));
        courses.add(createCourse(3, "英语"));
        for(Student student : students) {
            int index = students.indexOf(student);
            switch (index % 3) {
                case 0:
                    studies.add(createStudy(student, courses.get(0)));
                    break;
                case 1:
                    studies.add(createStudy(student, courses.get(1)));
                    break;
                case 2:
                    studies.add(createStudy(student, courses.get(2)));
                    break;
            }
        }
    }

    private Student createStudent(int id, String name){
        Student student = new Student();
        student.setId(id);
        student.setName(name);
        return student;
    }

    private Course createCourse(int id, String name){
        Course course = new Course();
        course.setId(id);
        course.setCourseName(name);
        return course;
    }

    private Study createStudy(Student student, Course course){
        Study study = new Study();
        study.setStudentId(student.getId());
        study.setStudentName(student.getName());
        study.setCourseId(course.getId());
        study.setCourseName(course.getCourseName());
        return study;
    }

    @Override
    public Student[] getStudents() {
        return students.toArray(new Student[studies.size()]);
    }

    @Override
    public Student[] getStudentsByCourse(Course course) {
        List<Student> students = new ArrayList<>();
        if (course == null) {
            return new Student[0];
        }
        for (Study study : studies) {
            if (study.getCourseId() == course.getId()) {
                Student tmp = new Student();
                tmp.setId(study.getStudentId());
                tmp.setName(study.getStudentName());
                students.add(tmp);
            }
        }
        return students.toArray(new Student[students.size()]);
    }

    @Override
    public Course[] getCourses() {
        return courses.toArray(new Course[courses.size()]);
    }

    @Override
    public Course[] getCourseByStudent(Student student) {
        List<Course> courses = new ArrayList<>();
        if (student == null) {
            return new Course[0];
        }
        for (Study study : studies) {
            if (study.getStudentId() == student.getId()) {
                Course tmp = new Course();
                tmp.setId(study.getCourseId());
                tmp.setCourseName(study.getCourseName());
                courses.add(tmp);
            }
        }
        return courses.toArray(new Course[courses.size()]);
    }
}
