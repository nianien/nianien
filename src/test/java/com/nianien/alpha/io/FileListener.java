package com.nianien.alpha.io;

public interface FileListener {

    /**
     * 内容变化
     */
    void onChanged();

    /**
     * 文件删除
     */
    void onDeleted();

    /**
     * 文件创建
     */
    void onCreated();

    /**
     * 文件更新
     */
    void onUpdated();
}
