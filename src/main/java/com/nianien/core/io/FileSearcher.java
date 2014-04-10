package com.nianien.core.io;

import com.nianien.core.exception.ExceptionHandler;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件查找类,查找匹配的文件
 *
 * @author skyfalling
 */
public class FileSearcher {

    /**
     * 返回指定路径下匹配的全部文件
     *
     * @param path
     * @param filter
     * @return 匹配的文件列表
     */
    public static List<File> find(File path, FileFilter filter) {
        ExceptionHandler.throwIf(!path.isDirectory(), "the path must be a directory:"
                + path.getAbsolutePath());
        return find(path, filter, new ArrayList<File>());
    }

    /**
     * 在指定目录下查找第一个匹配的文件
     *
     * @param path
     * @param filter
     * @return 匹配的文件
     */
    public static File findOne(File path, final FileFilter filter) {
        ExceptionHandler.throwIf(!path.isDirectory(), "the path must be a directory:"
                + path.getAbsolutePath());
        final File[] find = new File[1];
        File[] files = path.listFiles(new FileFilter() {

            @Override
            public boolean accept(File file) {
                if (find[0] == null && filter.accept(file)) {
                    find[0] = file;
                }
                return find[0] == null && file.isDirectory();
            }
        });
        for (File file : files) {
            if (find[0] != null)
                break;
            find[0] = findOne(file, filter);
        }
        return find[0];
    }

    /**
     * 返回指定路径下的所有文件
     *
     * @param path
     * @return 文件列表
     */
    public static List<File> listAllFiles(File path) {
        return find(path, new FileFilter() {
            @Override
            public boolean accept(File file) {
                return true;
            }
        });
    }

    /**
     * 按照名称查找全部匹配正则表达式的文件
     *
     * @param path
     * @param regex
     * @return 匹配的文件列表
     */
    public static List<File> findByName(File path, final String regex) {
        return find(path, new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().matches(regex);
            }
        });
    }

    /**
     * 在指定目录下查找所有匹配的文件
     *
     * @param path
     * @param filter
     * @param list   匹配的文件列表
     */
    private static List<File> find(File path, final FileFilter filter,
                                   final List<File> list) {
        if (path.isDirectory()) {
            path.listFiles(new FileFilter() {

                @Override
                public boolean accept(File file) {
                    if (filter.accept(file))
                        list.add(file);
                    find(file, filter, list);
                    return false;
                }
            });
        }
        return list;
    }

}
