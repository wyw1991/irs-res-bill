package com.dtzhejiang.irs.res.bill.common.util;

import com.dtzhejiang.irs.res.bill.common.util.gson.DateTypeAdapter;
import com.dtzhejiang.irs.res.bill.common.util.gson.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.ToNumberPolicy;
import com.google.gson.reflect.TypeToken;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JsonUtil {

    private static final Gson gson;

    static {
        //数字类型或者object转数字时 先尝试转成long 要其他规则自己去写 默认时转成double
        GsonBuilder gb = new GsonBuilder().setNumberToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE).setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE);
        gb.registerTypeAdapter(Date.class,new DateTypeAdapter());
        gb.registerTypeAdapter(LocalDate.class,new LocalDateAdapter());
        gson = gb.create();
    }

    public static Gson getGson() {
        return gson;
    }

    public static String toJsonString(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T parseObject(String json, Class<T> classOfT) {
        return gson.fromJson(json,classOfT);
    }

    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        Type type = new TypeToken<List<JsonObject>>() {}.getType();
        List<JsonObject> objects = gson.fromJson(json, type);
        if(CollectionUtils.isEmpty(objects)) {
            return new ArrayList<>();
        }
        List<T> list = new ArrayList<>();
        for (JsonObject object : objects) {
            list.add(gson.fromJson(object,clazz));
        }
        return list;
    }

}
