package com.github.cnahyz.mysql2yml.dto;

import com.github.cnahyz.mysql2yml.entity.TypeMapper;
import com.github.cnahyz.mysql2yml.enums.MatchType;
import com.github.cnahyz.mysql2yml.tool.CurrGroupUtils;
import com.github.cnahyz.mysql2yml.tool.DocCommentUtils;
import com.github.cnahyz.mysql2yml.tool.NameUtils;
import com.intellij.database.model.DasColumn;
import com.intellij.psi.PsiField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 列信息传输对象
 *
 * @author makejava
 * @version 1.0.0
 * @date 2021/08/14 17:29
 */
@Data
@NoArgsConstructor
public class ColumnInfoDTO {

    public ColumnInfoDTO(PsiField field) {
        this.name = field.getName();
        this.comment = DocCommentUtils.getComment(field.getDocComment());
        this.type = field.getType().getCanonicalText();
        this.custom = false;
        this.ext = new HashMap<>();
    }

    public ColumnInfoDTO(DasColumn column) {
        this.name = NameUtils.getInstance().getJavaName(column.getName());
        this.comment = column.getComment();
        this.type = getJavaType(column.getDataType().toString());
        this.custom = false;
        this.ext = new HashMap<>();
    }

    private String getJavaType(String dbType) {
        for (TypeMapper typeMapper : CurrGroupUtils.getCurrTypeMapperGroup().getElementList()) {
            if (typeMapper.getMatchType() == MatchType.ORDINARY) {
                if (dbType.equalsIgnoreCase(typeMapper.getColumnType())) {
                    return typeMapper.getJavaType();
                }
            } else {
                // 不区分大小写的正则匹配模式
                if (Pattern.compile(typeMapper.getColumnType(), Pattern.CASE_INSENSITIVE).matcher(dbType).matches()) {
                    return typeMapper.getJavaType();
                }
            }
        }
        return "java.lang.Object";
    }

    /**
     * 名称
     */
    private String name;
    /**
     * 注释
     */
    private String comment;
    /**
     * 全类型
     */
    private String type;
    /**
     * 标记是否为自定义附加列
     */
    private Boolean custom;
    /**
     * 扩展数据
     */
    private Map<String, Object> ext;
}
