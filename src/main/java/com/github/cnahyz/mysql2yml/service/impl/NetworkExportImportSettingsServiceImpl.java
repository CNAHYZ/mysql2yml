package com.github.cnahyz.mysql2yml.service.impl;

import com.github.cnahyz.mysql2yml.dict.GlobalDict;
import com.github.cnahyz.mysql2yml.dto.SettingsStorageDTO;
import com.github.cnahyz.mysql2yml.service.ExportImportSettingsService;
import com.github.cnahyz.mysql2yml.tool.HttpUtils;
import com.github.cnahyz.mysql2yml.tool.JSON;
import com.github.cnahyz.mysql2yml.tool.ProjectUtils;
import com.github.cnahyz.mysql2yml.tool.StringUtils;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.ui.InputValidator;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.NonEmptyInputValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 网络导出导入设置服务实现
 *
 * @author makejava
 * @version 1.0.0
 * @date 2021/08/12 14:37
 */
public class NetworkExportImportSettingsServiceImpl implements ExportImportSettingsService {

    private static final Pattern TOKEN_PATTERN = Pattern.compile("[a-z0-9A-Z]{20,}+");

    /**
     * 导出设置
     *
     * @param settingsStorage 要导出的设置
     */
    @Override
    public void exportConfig(SettingsStorageDTO settingsStorage) {
        // 上传数据
        String result = HttpUtils.postJson("/template", settingsStorage);
        if (result != null) {
            // 利用正则提取token值
            String token = "error";
            Matcher matcher = TOKEN_PATTERN.matcher(result);
            if (matcher.find()) {
                token = matcher.group();
            }
            // 显示token
            Messages.showInputDialog(ProjectUtils.getCurrProject(), result, GlobalDict.TITLE_INFO, AllIcons.General.InformationDialog, token, new NonEmptyInputValidator(), null, "Easy Code官网地址：<a href='http://www.shujuhaiyang.com'>www.shujuhaiyang.com</a>");
        }
    }

    /**
     * 导入设置
     *
     * @return 设置信息
     */
    @Override
    public SettingsStorageDTO importConfig() {
        String token = Messages.showInputDialog("Token:", GlobalDict.TITLE_INFO, AllIcons.General.Tip, "", new InputValidator() {
            @Override
            public boolean checkInput(String inputString) {
                return !StringUtils.isEmpty(inputString);
            }

            @Override
            public boolean canClose(String inputString) {
                return this.checkInput(inputString);
            }
        });
        if (token == null) {
            return null;
        }
        String result = HttpUtils.get(String.format("/template?token=%s", token));
        if (result == null) {
            return null;
        }
        // 解析数据
        return JSON.parse(result, SettingsStorageDTO.class);
    }
}
