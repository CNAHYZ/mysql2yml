package com.github.cnahyz.mysql2yml.service;

import com.github.cnahyz.mysql2yml.dto.SettingsStorageDTO;

/**
 * 导出导入设置服务
 *
 * @author makejava
 * @version 1.0.0
 * @date 2021/08/11 17:24
 */
public interface ExportImportSettingsService {

    /**
     * 导出设置
     *
     * @param settingsStorage 要导出的设置
     */
    void exportConfig(SettingsStorageDTO settingsStorage);

    /**
     * 导入设置
     *
     * @return 设置信息
     */
    SettingsStorageDTO importConfig();

}
