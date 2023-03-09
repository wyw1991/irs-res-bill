package com.dtzhejiang.irs.res.bill.app.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 操作
 */
@Data
public class OperationDTO implements Serializable {

    private static final long serialVersionUID = 5916659110387553701L;

    /**
     * 操作id
     */
    private String id;
    /**
     * 操作名
     */
    private String name;
    /**
     * 操作值
     */
    private String value;
    /**
     * 操作项列表
     */
    private List<Option> options = new CopyOnWriteArrayList<>();

    public OperationDTO add(Option option) {
        if(!options.contains(option)) {
            options.add(option);
        }
        return this;
    }

    @Getter
    @Setter
    public static class Option implements Serializable {
        private static final long serialVersionUID = -1314068313525012995L;
        /**
         * 选项名称
         */
        private String name;
        /**
         * 选项id
         */
        private String id;
        /**
         * 关联的表单
         */
        private String refFormKey;
        /**
         * 关联的json
         */
        private Map<String,Object> refJson;
        /**
         * 关联的状态
         */
        private String refStatus;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Option option = (Option) o;
            return id.equals(option.id) && name.equals(option.name) && refFormKey.equals(option.refFormKey);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }
}
