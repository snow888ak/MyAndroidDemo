package com.example.lib.utils;

import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Objects;

/**
 * Created by Snow.ZhK on 2017/7/9.
 */

public class JsonUtil {

    public static String toJson(Object o){
        return JSON.toJSONString(o);
    }

    public static <T> T parseObject(String json, Class<T> clazz){
        return JSON.parseObject(json, clazz);
    }

    public static <T> List<T> parseArrays(String json, Class<T> clazz){
        return JSON.parseArray(json, clazz);
    }

}
