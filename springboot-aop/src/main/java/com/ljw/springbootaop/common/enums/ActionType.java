package com.ljw.springbootaop.common.enums;

/**
 * @Description: 动作类别
 * @Author: jianweil
 * @date: 2021/12/6 14:30
 */
public enum ActionType {
    /**
     * 其它
     */
    OTHER,

    /**
     * 查询
     */
    QUERY,

    /**
     * 新增
     */
    INSERT,

    /**
     * 修改
     */
    UPDATE,

    /**
     * 删除
     */
    DELETE,

    /**
     * 授权
     */
    GRANT,

    /**
     * 导出
     */
    EXPORT,

    /**
     * 导入
     */
    IMPORT,

    /**
     * 强退
     */
    FORCE,

    /**
     * 生成代码
     */
    GENCODE,

    /**
     * 清空数据
     */
    CLEAN;
}
