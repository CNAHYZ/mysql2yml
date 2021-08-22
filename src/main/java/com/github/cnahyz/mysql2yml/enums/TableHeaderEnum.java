package com.github.cnahyz.mysql2yml.enums;

/**
 * 自定义表头名称枚举类
 *
 * @author yz
 * @className TableHeaderEnum
 * @date 2021/8/22 20:37
 */
public enum TableHeaderEnum {
    /**
     * 自定义表头名称枚举类
     */
    FIELD_NAME("字段"),
    FIELD_TYPE("类型"),
    FIELD_COMMENT("注释"),
    IS_CONVERT("是否转换"),
    ADD_OR_MODIFY("新增/修改");
    private final String name;


    public String getName() {
        return name;
    }

    TableHeaderEnum(String name) {
        this.name = name;
    }
}
