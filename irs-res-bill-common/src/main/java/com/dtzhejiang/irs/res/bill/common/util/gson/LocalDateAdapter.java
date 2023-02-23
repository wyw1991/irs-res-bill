package com.dtzhejiang.irs.res.bill.common.util.gson;

import com.dtzhejiang.irs.res.bill.common.util.DateUtil;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;

public class LocalDateAdapter extends TypeAdapter<LocalDate> {

    @Override
    public void write(JsonWriter out, LocalDate value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(DateUtil.convertLocalDate(value));
        }
    }

    @Override
    public LocalDate read(JsonReader in) throws IOException {
        if(in != null) {
            return DateUtil.toLocalDate(in.nextString());
        }
        return null;
    }
}
