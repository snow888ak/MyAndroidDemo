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
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

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
        Observable.fromArray(students).subscribe(new Consumer<Student>() {
            @Override
            public void accept(@NonNull Student student) throws Exception {
                printMsgToTextView(JsonUtil.toJson(student));
            }
        });
        printMsgToTextView("------打印课程信息------");
        Course[] courses = studentModel.getCourses();
        Observable.fromArray(courses).subscribe(new Consumer<Course>() {
            @Override
            public void accept(@NonNull Course course) throws Exception {
                printMsgToTextView(JsonUtil.toJson(course));
            }
        });
        printMsgToTextView("------打印学生的名字信息------");
        Observable.fromArray(students)
                .map(new Function<Student, String>() {
                    @Override
                    public String apply(@NonNull Student student) throws Exception {
                        return student.getName();
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        printMsgToTextView(s);
                    }
                });
        printMsgToTextView("------打印学生的课程信息------");
        Observable.fromArray(students)
                .flatMap(new Function<Student, ObservableSource<Course>>() {
                    @Override
                    public ObservableSource<Course> apply(@NonNull Student student) throws Exception {
                        return Observable.fromArray(studentModel.getCourseByStudent(student));
                    }
                })
                .map(new Function<Course, String>() {
                    @Override
                    public String apply(@NonNull Course course) throws Exception {
                        return course.getCourseName();
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        printMsgToTextView(s);
                    }
                });
        printMsgToTextView("------打印课程的学生信息------");
        Observable.fromArray(courses)
                .flatMap(new Function<Course, ObservableSource<Student>>() {
                    @Override
                    public ObservableSource<Student> apply(@NonNull Course course) throws Exception {
                        return Observable.fromArray(studentModel.getStudentsByCourse(course));
                    }
                })
                .map(new Function<Student, String>() {
                    @Override
                    public String apply(@NonNull Student student) throws Exception {
                        return student.getName();
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        printMsgToTextView(s);
                    }
                });
    }

    private void printMsgToTextView(String msg) {
        mTvContent.append(msg);
        mTvContent.append("\n");
    }
}
