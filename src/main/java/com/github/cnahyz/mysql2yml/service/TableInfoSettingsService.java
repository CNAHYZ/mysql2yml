package com.github.cnahyz.mysql2yml.service;

import com.github.cnahyz.mysql2yml.dto.TableInfoSettingsDTO;
import com.github.cnahyz.mysql2yml.entity.TableInfo;
import com.github.cnahyz.mysql2yml.service.impl.TableInfoSettingsServiceImpl;
import com.github.cnahyz.mysql2yml.tool.ProjectUtils;
import com.intellij.database.psi.DbTable;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.psi.PsiClass;

/**
 * @author makejava
 * @version 1.0.0
 * @date 2021/08/14 15:16
 */
public interface TableInfoSettingsService extends PersistentStateComponent<TableInfoSettingsDTO> {
    /**
     * 获取实例
     *
     * @return {@link com.github.cnahyz.mysql2yml.service.SettingsStorageService}
     */
    static TableInfoSettingsService getInstance() {
        return ServiceManager.getService(ProjectUtils.getCurrProject(), TableInfoSettingsServiceImpl.class);
    }

    /**
     * 获取表格信息
     *
     * @param dbTable 数据库表
     * @return {@link TableInfo}
     */
    TableInfo getTableInfo(DbTable dbTable);

    /**
     * 获取表信息
     *
     * @param psiClass psi类
     * @return {@link TableInfo}
     */
    TableInfo getTableInfo(PsiClass psiClass);

    /**
     * 保存表信息
     *
     * @param tableInfo 表信息
     */
    void saveTableInfo(TableInfo tableInfo);

    /**
     * 重置表信息
     *
     * @param dbTable 数据库表
     */
    void resetTableInfo(DbTable dbTable);
}
