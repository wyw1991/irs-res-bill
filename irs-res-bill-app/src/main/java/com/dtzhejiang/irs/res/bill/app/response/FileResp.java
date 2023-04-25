package com.dtzhejiang.irs.res.bill.app.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileResp implements Serializable {

    private static final long serialVersionUID = 3027616347560764512L;
    private String code;
    private String msg;
    private Object data;

    public static FileResp success() {
        return new FileResp("200", "success", null);
    }

    public static FileResp success(Object obj) {
        return new FileResp("200", "success", obj);
    }

    public static FileResp error(String code, String msg) {
        return new FileResp(code, msg, null);
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FileResult {
        /**
         * 文件路径
         */
        private String url;
        /**
         * 文件名
         */
        @JsonAlias("name")
        private String fileName;
        /**
         * 文件id
         */
        private String fileId;
    }
}
