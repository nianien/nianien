package com.nianien.core.io;

import com.nianien.core.exception.ExceptionHandler;
import com.nianien.core.function.Function;
import com.nianien.core.util.StringUtils;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 处理文件操作的工具类
 *
 * @author skyfalling
 */
public class Files {


    /**
     * 换行符, windows:"\r\n", linux: "\n"
     */
    public final static String newLine = System.getProperty("line.separator");
    /**
     * 缓冲区大小
     */
    private final static int bufferSize = 1024 * 8;


    /**
     * 移动src文件为dest文件<br/>
     * 注: dest是移动后的目标文件,而不是目标文件所在的目录.
     *
     * @param src  原文件
     * @param dest 目标文件
     */
    public static boolean move(File src, File dest) {
        File dir = dest.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (dest.exists()) {
            delete(dest);
        }
        return src.renameTo(dest);
    }

    /**
     * 复制src文件为dest文件,支持目录拷贝<br/>
     * 注: dest是复制后的目标文件,而不是目标文件所在的目录.目标文件如果存在,则会被覆盖
     *
     * @param src
     * @param dest
     */
    public static void copy(File src, File dest) {
        File dir = dest.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (dest.exists()) {
            delete(dest);
        }
        if (src.isDirectory()) {
            dest.mkdirs();
            File[] files = src.listFiles();
            for (File file : files) {
                copy(file, new File(dest, file.getName()));
            }
        } else {
            FileChannel inChannel = null;
            FileChannel outChannel = null;
            try {
                inChannel = new FileInputStream(src).getChannel();
                outChannel = new FileOutputStream(dest).getChannel();
                // 8M缓冲区
                ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
                while (inChannel.read(buffer) != -1) {
                    buffer.flip();
                    outChannel.write(buffer);
                    buffer.clear();//prepare for reading;清空缓冲区；
                }
            } catch (Exception e) {
                ExceptionHandler.throwException(e);
            } finally {
                Closer.close(inChannel, outChannel);
            }
        }
    }


    /**
     * 删除指定文件,支持目录操作<br>
     * 如果指定路径为文件夹,则递归删除子文件夹及其内容
     *
     * @param file
     */
    public static boolean delete(File file) {
        if (file.isDirectory()) {
            for (File f : file.listFiles())
                if (!delete(f))
                    return false;
        }
        return file.delete();
    }

    /**
     * 获取文件的字节数组
     *
     * @param file
     * @return 文件字节内容
     */
    public static byte[] getBytes(File file) {
        try {
            return getBytes(new FileInputStream(file));
        } catch (Exception e) {
            throw ExceptionHandler.throwException(e);
        }
    }


    /**
     * 获取InputStream对象指定长度的字节内容
     *
     * @param inputStream
     * @return 文件字节内容
     */
    public static byte[] getBytes(InputStream inputStream) {
        try {
            List<byte[]> buffers = new ArrayList<byte[]>();
            int size = 0;
            int read;
            do {
                byte[] buffer = new byte[bufferSize];
                read = inputStream.read(buffer);
                if (read > 0) {
                    size += read;
                    buffers.add(Arrays.copyOf(buffer, read));
                }
            } while (read == bufferSize);
            byte[] bytes = new byte[size];
            int position = 0;
            for (byte[] buffer : buffers) {
                System.arraycopy(buffer, 0, bytes, position, buffer.length);
                position += buffer.length;
            }
            return bytes;
        } catch (Exception e) {
            throw ExceptionHandler.throwException(e);
        } finally {
            Closer.close(inputStream);
        }
    }


    /**
     * 读取文件内容
     *
     * @param file
     * @return
     */
    public static String read(File file) {
        try {
            return read(new FileReader(file));
        } catch (Exception e) {
            throw ExceptionHandler.throwException(e);
        }
    }

    /**
     * 以指定编码格式读取文件内容
     *
     * @param file
     * @param charset
     * @return
     */
    public static String read(File file, String charset) {
        try {
            return read(new InputStreamReader(new FileInputStream(file),
                    charset));
        } catch (Exception e) {
            throw ExceptionHandler.throwException(e);
        }
    }

    /**
     * 读取InputStream对象内容,读取后关闭InputStream对象
     *
     * @param inputStream
     * @return
     */
    public static String read(InputStream inputStream) {
        return read(new InputStreamReader(inputStream));
    }

    /**
     * 以指定编码格式读取InputStream对象内容,读取后关闭InputStream对象
     *
     * @param inputStream
     * @param charset
     * @return
     */
    public static String read(InputStream inputStream, String charset) {
        try {
            return read(new InputStreamReader(inputStream, charset));
        } catch (UnsupportedEncodingException e) {
            throw ExceptionHandler.throwException(e);
        }
    }

    /**
     * 读取Reader对象内容,读取后关闭Reader对象
     *
     * @param reader
     * @return
     */
    public static String read(Reader reader) {
        final StringBuilder sb = new StringBuilder();
        readLines(reader, new Function<String, String>() {
            @Override
            public String apply(String s) {
                sb.append(s);
                return null;
            }
        });
        return sb.toString();
    }

    /**
     * 以指定字符编码格式读取文件的每行内容
     *
     * @param file
     * @param charset
     */
    public static List<String> readLines(File file, String charset) {
        try {
            return readLines(
                    new InputStreamReader(new FileInputStream(file), charset));
        } catch (Exception e) {
            throw ExceptionHandler.throwException(e);
        }
    }

    /**
     * 以指定字符编码格式读取文件的每行内容
     *
     * @param file
     * @param function
     * @param charset
     */
    public static void readLines(File file, Function<String, String> function,
                                 String charset) {
        try {
            readLines(
                    new InputStreamReader(new FileInputStream(file), charset),
                    function);
        } catch (Exception e) {
            throw ExceptionHandler.throwException(e);
        }
    }


    /**
     * 以指定编码格式读取文件的每行内容
     *
     * @param file
     */
    public static List<String> readLines(File file) {
        try {
            return readLines(new FileReader(file));
        } catch (Exception e) {
            throw ExceptionHandler.throwException(e);
        }
    }


    /**
     * 读取文件对象的每行内容
     *
     * @param file
     * @param function
     */
    public static void readLines(File file, Function<String, String> function) {
        try {
            readLines(new FileReader(file), function);
        } catch (Exception e) {
            throw ExceptionHandler.throwException(e);
        }
    }

    /**
     * 以指定编码格式读取InputStream对象的每行内容, 然后关闭InputStream对象
     *
     * @param inputStream
     * @param charset
     */
    public static List<String> readLines(InputStream inputStream,
                                         String charset) {
        try {
            return readLines(new InputStreamReader(inputStream, charset));
        } catch (Exception e) {
            throw ExceptionHandler.throwException(e);
        }
    }

    /**
     * 以指定编码格式读取InputStream对象的每行内容, 然后关闭InputStream对象
     *
     * @param inputStream
     * @param function
     * @param charset
     */
    public static void readLines(InputStream inputStream, Function<String, String> function,
                                 String charset) {
        try {
            readLines(new InputStreamReader(inputStream, charset), function);
        } catch (Exception e) {
            throw ExceptionHandler.throwException(e);
        }
    }

    /**
     * 读取InputStream对象内容的每行内容
     *
     * @param inputStream
     */
    public static List<String> readLines(InputStream inputStream) {
        return readLines(new InputStreamReader(inputStream));
    }

    /**
     * 读取InputStream对象内容的每行内容
     *
     * @param inputStream
     * @param function
     */
    public static void readLines(InputStream inputStream, Function<String, String> function) {
        readLines(new InputStreamReader(inputStream), function);
    }

    /**
     * 读取Reader对象内容的每行内容, 然关闭reader对象
     *
     * @param reader
     */
    public static List<String> readLines(Reader reader) {
        final List<String> lines = new ArrayList<String>();
        readLines(reader, new Function<String, String>() {
            @Override
            public String apply(String s) {
                lines.add(s);
                return null;
            }
        });
        return lines;
    }

    /**
     * 读取Reader对象内容的每行内容, 然关闭reader对象
     *
     * @param reader
     * @param function
     */
    public static void readLines(Reader reader, Function<String, String> function) {
        try {
            BufferedReader buffer = new BufferedReader(reader);
            String line;
            while ((line = buffer.readLine()) != null) {
                function.apply(line);
            }
        } catch (Exception e) {
            ExceptionHandler.throwException(e);
        } finally {
            Closer.close(reader);
        }
    }


    /**
     * 向文件写入内容
     *
     * @param file
     * @param content
     * @param isAppend
     */
    public static void write(File file, String content, boolean isAppend) {
        createParent(file);
        try {
            write(new FileWriter(file, isAppend), content);
        } catch (Exception e) {
            ExceptionHandler.throwException(e);
        }
    }

    /**
     * 以指定编码格式向文件写入内容
     *
     * @param file
     * @param content
     * @param charset
     * @param isAppend
     */
    public static void write(File file, String content, String charset,
                             boolean isAppend) {
        createParent(file);
        try {
            OutputStream outputStream = new FileOutputStream(file, isAppend);
            Writer writer = new OutputStreamWriter(outputStream, charset);
            write(writer, content);
        } catch (Exception e) {
            ExceptionHandler.throwException(e);
        }
    }

    /**
     * 向OutputStream对象写入从InputStream对象读取的内容, 之后关闭OutputStream和InputStream对象
     *
     * @param outputStream
     * @param inputStream
     */
    public static void write(OutputStream outputStream, InputStream inputStream) {
        try {
            int read = 0;
            byte[] buffer = new byte[bufferSize];
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
                outputStream.flush();
            }
        } catch (Exception e) {
            ExceptionHandler.throwException(e);
        } finally {
            Closer.close(outputStream);
            Closer.close(inputStream);
        }
    }

    /**
     * 向OutputStream对象写入内容后关闭OutputStream对象
     *
     * @param outputStream
     * @param content
     */
    public static void write(OutputStream outputStream, String content) {
        try {
            write(new OutputStreamWriter(outputStream), content);
        } catch (Exception e) {
            ExceptionHandler.throwException(e);
        }
    }

    /**
     * 以指定编码格式向OutputStream对象写入内容后关闭OutputStream对象
     *
     * @param outputStream
     * @param content
     * @param charset
     */
    public static void write(OutputStream outputStream, String content,
                             String charset) {
        try {
            write(new OutputStreamWriter(outputStream, charset), content);
        } catch (Exception e) {
            ExceptionHandler.throwException(e);
        }
    }

    /**
     * 向writer对象写入从reader对象读取的内容, 之后关闭Writer和Reader对象
     *
     * @param writer
     * @param reader
     */
    public static void write(Writer writer, Reader reader) {
        try {
            char[] buffer = new char[bufferSize];
            int read;
            while ((read = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, read);
                writer.flush();
            }
        } catch (Exception e) {
            ExceptionHandler.throwException(e);
        }
    }

    /**
     * 向Writer对象写入内容后关闭Writer对象
     *
     * @param writer
     * @param content
     */
    public static void write(Writer writer, String content) {
        writer = new BufferedWriter(writer);
        try {
            writer.write(content);
            writer.flush();
        } catch (Exception e) {
            ExceptionHandler.throwException(e);
        } finally {
            Closer.close(writer);
        }
    }


    /**
     * 获取URL对象对应的系统路径,如果是压缩包,则获取压缩包所在路径
     *
     * @param url
     * @return URL对象对应的系统路径
     */
    public static String urlToPath(URL url) {
        String path = url.getPath();
        int location = path.indexOf("!/");
        if (location != -1) {
            path = path.substring(0, location);
        }
        return path;
    }

    /**
     * 创建指定路径所表示的目录<br/>
     * 如果路径指向已存在的非目录文件,则删除该文件并创建目录
     *
     * @param path
     */
    public static void createDirectory(File path) {
        // 如果存在且不是目录,则删除后在创建
        if (!path.isDirectory()) {
            if (path.exists()) {
                path.delete();
            }
            path.mkdirs();
        }
    }

    /**
     * 创建并返回指定文件所在的目录文件<br/>
     *
     * @param file
     * @return 文件对象
     */
    public static File createParent(File file) {
        File parent = file.getParentFile();
        createDirectory(parent);
        return parent;
    }


    /**
     * 获取指定的绝对路径
     *
     * @param path
     * @return 返回标准路径, 不含"."或".."
     */
    public static String getAbsolutePath(String path) {
        File file = new File(path);
        try {
            return file.getCanonicalPath();
        } catch (Exception e) {
            return file.getAbsolutePath();
        }
    }

    /**
     * 获取指定路径的上级路径
     *
     * @param path
     * @return 上一级路径
     */
    public static String getParentPath(String path) {
        return new File(path).getParent();
    }

    /**
     * 获取当前路径
     *
     * @return 当前路径
     */
    public static String getCurrentPath() {
        String current = System.getProperty("user.dir");
        if (StringUtils.isEmpty(current))
            current = getAbsolutePath(".");
        return current;
    }


    /**
     * 获取路径所在的目录<br/>
     * 如果路径本身代表目录,则返回路径本身,否则路径所在目录
     *
     * @param path
     * @return 指定路径所在的目录
     */
    public static String getDirectory(String path) {
        File file = new File(path);
        if (file.isDirectory())
            return getAbsolutePath(path);
        //路径本身表示路径
        if (path.endsWith("/") || path.endsWith("\\"))
            return file.getAbsolutePath();
        return file.getParent();
    }

    /**
     * 多个路径组合构建文件对象
     *
     * @param paths
     * @return 拼装后的路径指向的文件对象
     */
    public static File getFile(String... paths) {
        File file = null;
        for (String path : paths) {
            if (file == null) {
                file = new File(path);
            } else {
                file = new File(file, path);
            }
        }
        return file;
    }


    /**
     * 获取指定路径的文件名
     *
     * @param path
     * @return 文件名称
     */
    public static String getFileName(String path) {
        return new File(path).getName();
    }

    /**
     * 获取不含后缀的文件名
     *
     * @param path
     * @return 不含后缀的文件名
     */
    public static String getFileNameWithoutExt(String path) {
        String fileName = getFileName(path);
        int index = fileName.lastIndexOf('.');
        return index == -1 ? fileName : fileName.substring(0, index);
    }

    /**
     * 获取文件名后缀
     *
     * @param path
     * @return 文件名后缀, 含"."
     */
    public static String getFileExt(String path) {
        String fileName = getFileName(path);
        int index = fileName.lastIndexOf('.');
        return index == -1 ? "" : fileName.substring(index);
    }


    /**
     * 判断指定路径是否为绝对路径
     *
     * @param path
     * @return 如果是返回true, 否则返回false
     */
    public static boolean isAbsolutePath(String path) {
        return new File(path).isAbsolute();
    }

}
