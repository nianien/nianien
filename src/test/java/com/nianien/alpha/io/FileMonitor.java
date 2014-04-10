package com.nianien.alpha.io;

import com.nianien.core.exception.ExceptionHandler;
import com.nianien.core.io.Closer;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileMonitor {
    class FileProperty {
        private boolean exist;
        private byte[] content;
        private long lastModified;

        public FileProperty(File file) {
            this.exist = file.exists();
            if (this.exist) {
                this.lastModified = file.lastModified();
                this.content = getBytes(file);
            }
        }

        public FileProperty() {
        }

        public boolean existChanged(FileProperty property) {
            return this.exist ^ property.exist;
        }

        public boolean contentChanged(FileProperty property) {
            return !Arrays.equals(this.content, property.content);
        }

        public boolean lastModifiedChanged(FileProperty property) {
            return this.lastModified != property.lastModified;
        }

        /**
         * 获取InputStream对象指定长度的字节内容
         * 
         * @param file
         * @return 文件字节内容
         */
        protected byte[] getBytes(File file) {
            long size = file.length();
            ExceptionHandler.throwIf(size > Integer.MAX_VALUE, "file [" + file.getPath() + "] is to large!");
            byte[] bytes = new byte[(int) size];
            FileInputStream instream = null;
            try {
                instream = new FileInputStream(file);
                instream.read(bytes);
                return bytes;
            } catch (Exception e) {
                throw ExceptionHandler.throwException(e);
            } finally {
                Closer.close(instream);
            }
        }
    }

    FileProperty property;
    private File file;
    private List<FileListener> listeners = new ArrayList<FileListener>();
    private ExecutorService es = Executors.newSingleThreadExecutor();

    /**
     * 构造方法
     * 
     * @param file
     */
    public FileMonitor(File file) {
        this.file = file;
    }

    /**
     * 启动检测程序
     */
    public void start() {
        this.property = new FileProperty(file);
        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                synchronized (listeners) {
                    try {
                        doMonitor();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 0, 200);
    }

    /**
     * 添加监听器
     * 
     * @param listener
     */
    public void addListener(final FileListener listener) {
        es.execute(new Runnable() {

            @Override
            public void run() {
                synchronized (listeners) {
                    FileMonitor.this.listeners.add(listener);
                }
            }
        });

    }

    /**
     * 删除监听器
     * 
     * @param listener
     */
    public void removeListener(final FileListener listener) {
        es.execute(new Runnable() {

            @Override
            public void run() {
                synchronized (listeners) {
                    FileMonitor.this.listeners.remove(listener);
                }

            }
        });
    }

    /**
     * 检测文件
     */
    protected void doMonitor() {
        FileProperty property = new FileProperty(file);
        if (property.existChanged(this.property)) {
            if (property.exist) {
                this.fileCreated();
            } else {
                this.fileDeleted();
            }
        } else if (property.exist) {
            if (property.lastModifiedChanged(this.property)) {
                this.fileUpdated();
            }
            if (property.contentChanged(this.property)) {
                this.fileChanged();
            }
        }
        this.property = property;
    }

    /**
     * 内容变化
     */
    protected void fileChanged() {
        for (FileListener listener : listeners) {
            listener.onChanged();
        }

    }

    /**
     * 文件删除
     */
    protected void fileDeleted() {
        for (FileListener listener : listeners) {
            listener.onDeleted();
        }
    }

    /**
     * 文件创建
     */
    protected void fileCreated() {
        for (FileListener listener : listeners) {
            listener.onCreated();
        }
    }

    /**
     * 文件更新
     */
    protected void fileUpdated() {
        for (FileListener listener : listeners) {
            listener.onUpdated();
        }
    }

    /**
     * 获取InputStream对象指定长度的字节内容
     * 
     * @param file
     * @return 文件字节内容
     */
    protected static byte[] getBytes(File file) {
        long size = file.length();
        ExceptionHandler.throwIf(size > Integer.MAX_VALUE, "file [" + file.getPath() + "] is to large!");
        byte[] bytes = new byte[(int) size];
        FileInputStream instream = null;
        try {
            instream = new FileInputStream(file);
            instream.read(bytes);
            return bytes;
        } catch (Exception e) {
            throw  ExceptionHandler.throwException(e);
        } finally {
            Closer.close(instream);
        }
    }

    public static void main(String[] args) {
        File file = new File("D:\\test");
        FileMonitor fm = new FileMonitor(file);
        fm.addListener(new FileListener() {

            @Override
            public void onUpdated() {
                System.out.println("onUpdated");

            }

            @Override
            public void onDeleted() {
                System.out.println("onDeleted");
            }

            @Override
            public void onCreated() {
                System.out.println("onCreated");
            }

            @Override
            public void onChanged() {
                System.out.println("onChanged");
            }
        });
        fm.start();

    }

}
