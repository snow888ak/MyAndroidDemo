package com.example.rxjavademo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.example.lib.utils.JsonUtil;
import com.example.rxjavademo.entity.Course;
import com.example.rxjavademo.entity.Student;
import com.example.rxjavademo.model.IStudentModel;
import com.example.rxjavademo.model.StudentModelImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Snow.ZhK on 2017/7/9.
 */

public class DemoActivity5 extends AppCompatActivity {

    public static final String TAG = DemoActivity5.class.getSimpleName();

    @BindView(R.id.content)
    TextView mTvContent;

    private IStudentModel studentModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        ButterKnife.bind(this);
        mTvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        setTitle("map()与flatMap()");
        studentModel = new StudentModelImpl();
    }

    public void startClick(View v) {
        printMsgToTextView("------打印学生信息------");
        Student[] students = studentModel.getStudents();
        Observable.from(students).subscribe(new Action1<Student>() {
            @Override
            public void call(Student student) {
                printMsgToTextView(JsonUtil.toJson(student));
            }
        });
        printMsgToTextView("------打印课程信息------");
        Course[] courses = studentModel.getCourses();
        Observable.from(courses).subscribe(new Action1<Course>() {
            @Override
            public void call(Course course) {
                printMsgToTextView(JsonUtil.toJson(course));
            }
        });
        printMsgToTextView("------打印学生的名字信息------");
        Observable.from(students)
                .map(new Func1<Student, String>() {
                    @Override
                    public String call(Student student) {
                        return student.getName();
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        printMsgToTextView(s);
                    }
                });
        printMsgToTextView("------打印学生的课程信息------");
        Observable.from(students)
                .flatMap(new Func1<Student, Observable<Course>>() {
                    @Override
                    public Observable<Course> call(Student student) {
                        return Observable.from(studentModel.getCourseByStudent(student));
                    }
                })
                .map(new Func1<Course, String>() {
                    @Override
                    public String call(Course course) {
                        return course.getCourseName();
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        printMsgToTextView(s);
                    }
                });
        printMsgToTextView("------打印课程的学生信息------");
        Observable.from(courses)
                .flatMap(new Func1<Course, Observable<Student>>() {
                    @Override
                    public Observable<Student> call(Course course) {
                        return Observable.from(studentModel.getStudentsByCourse(course));
                    }
                })
                .map(new Func1<Student, String>() {
                    @Override
                    public String call(Student student) {
                        return student.getName();
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        printMsgToTextView(s);
                    }
                });
    }

    private void printMsgToTextView(String msg) {
        mTvContent.append(msg);
        mTvContent.append("\n");
    }
}
