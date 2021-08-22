package com.github.cnahyz.mysql2yml.tool;

import com.github.cnahyz.mysql2yml.dto.TableOptions;
import com.github.cnahyz.mysql2yml.entity.TableInfo;
import com.github.cnahyz.mysql2yml.entity.Template;
import com.github.cnahyz.mysql2yml.service.impl.CodeGenerateServiceImpl;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * 额外的代码生成工具
 *
 * @author makejava
 * @version 1.0.0
 * @since 2018/11/01 10:11
 */
public class ExtraCodeGenerateUtils {
    /**
     * 代码生成服务
     */
    private CodeGenerateServiceImpl codeGenerateService;
    /**
     * 表信息对象
     */
    private TableInfo tableInfo;
    /**
     * 生成配置
     */
    private TableOptions tableOptions;

    public ExtraCodeGenerateUtils(CodeGenerateServiceImpl codeGenerateService, TableInfo tableInfo, TableOptions tableOptions) {
        this.codeGenerateService = codeGenerateService;
        this.tableInfo = tableInfo;
        this.tableOptions = tableOptions;
    }

    /**
     * 生成代码
     *
     * @param templateName 模板名称
     * @param param        附加参数
     */
    public void run(String templateName, Map<String, Object> param) {
        // 获取到模板
        Template currTemplate = null;
        for (Template template : CurrGroupUtils.getCurrTemplateGroup().getElementList()) {
            if (Objects.equals(template.getName(), templateName)) {
                currTemplate = template;
            }
        }
        if (currTemplate == null) {
            return;
        }
        // 生成代码
        codeGenerateService.generate(Collections.singletonList(currTemplate), Collections.singletonList(this.tableInfo), this.tableOptions, param);
    }
}
