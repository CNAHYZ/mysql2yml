package com.github.cnahyz.mysql2yml.action;

import com.github.cnahyz.mysql2yml.tool.CacheDataUtils;
import com.intellij.database.psi.DbTable;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 操作按钮分组
 *
 * @author makejava
 * @version 1.0.0
 * @since 2018/07/17 13:10
 */
public class MainActionGroup extends ActionGroup {
    /**
     * 缓存数据工具类
     */
    private CacheDataUtils cacheDataUtils = CacheDataUtils.getInstance();

    /**
     * 是否不存在子菜单
     */
    private boolean notExistsChildren;

    /**
     * 是否分组按钮
     *
     * @return 是否隐藏
     */
    @Override
    public boolean hideIfNoVisibleChildren() {
        return this.notExistsChildren;
    }


    /**
     * 根据右键在不同的选项上展示不同的子菜单
     *
     * @param event 事件对象
     * @return 动作组
     */
    @NotNull
    @Override
    public AnAction[] getChildren(@Nullable AnActionEvent event) {
        // 获取当前项目
        Project project = getEventProject(event);
        if (project == null) {
            return getEmptyAnAction();
        }

        //获取选中的PSI元素
        PsiElement psiElement = event.getData(LangDataKeys.PSI_ELEMENT);
        DbTable selectDbTable = null;
        if (psiElement instanceof DbTable) {
            selectDbTable = (DbTable) psiElement;
        }
        if (selectDbTable == null) {
            return getEmptyAnAction();
        }
        //获取选中的所有表
        PsiElement[] psiElements = event.getData(LangDataKeys.PSI_ELEMENT_ARRAY);
        if (psiElements == null || psiElements.length == 0) {
            return getEmptyAnAction();
        }
        List<DbTable> dbTableList = new ArrayList<>();
        for (PsiElement element : psiElements) {
            if (!(element instanceof DbTable)) {
                continue;
            }
            DbTable dbTable = (DbTable) element;
            dbTableList.add(dbTable);
        }
        if (dbTableList.isEmpty()) {
            return getEmptyAnAction();
        }

        //保存数据到缓存
        cacheDataUtils.setDbTableList(dbTableList);
        cacheDataUtils.setSelectDbTable(selectDbTable);
        System.out.println(dbTableList);
        this.notExistsChildren = false;
        return getMenuList();
    }

    /**
     * 初始化注册子菜单项目
     *
     * @return 子菜单数组
     */
    private AnAction[] getMenuList() {
        String tableConvertActionId = "tableConvertAction";
        String fieldConvertActionId = "fieldConvertAction";
        ActionManager actionManager = ActionManager.getInstance();
        // 全表转yml菜单
        AnAction tableConvertAction = actionManager.getAction(tableConvertActionId);
        if (tableConvertAction == null) {
            tableConvertAction = new TableConvertAction("全表转yml");
            actionManager.registerAction(tableConvertActionId, tableConvertAction);
        }
        // 字段转yml菜单
        AnAction fieldConvertAction = actionManager.getAction(fieldConvertActionId);
        if (fieldConvertAction == null) {
            fieldConvertAction = new FieldConvertAction("字段转yml");
            actionManager.registerAction(fieldConvertActionId, fieldConvertAction);
        }
        // AnAction clearConfigAction = new AnAction("Clear Config") {
        //     @Override
        //     public void actionPerformed(@NotNull AnActionEvent e) {
        //         DbTable dbTable = CacheDataUtils.getInstance().getSelectDbTable();
        //         if (dbTable == null) {
        //             return;
        //         }
        //         TableInfoSettingsService.getInstance().resetTableInfo(dbTable);
        //         Messages.showInfoMessage(dbTable.getName() + "表配置信息已重置成功", GlobalDict.TITLE_INFO);
        //     }
        // };
        // 返回所有菜单
        return new AnAction[]{tableConvertAction, fieldConvertAction};
    }


    /**
     * 获取空菜单组
     *
     * @return 空菜单组
     */
    private AnAction[] getEmptyAnAction() {
        this.notExistsChildren = true;
        return AnAction.EMPTY_ARRAY;
    }
}
