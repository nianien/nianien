package com.nianien.core.io;

import com.nianien.core.exception.ExceptionHandler;
import com.nianien.core.function.Function;
import com.nianien.core.util.StringUtils;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 处理文件操作的工具类
 *
 * @author skyfalling
 */
public class Files {
    public static class LinesStringHandler implements Function<String, String> {

        private List<String> lines = new ArrayList<String>();

        @Override
        public String apply(String str) {
            lines.add(str);
            return str;
        }

        public List<String> getLines() {
            return lines;
        }
    }

    public static class ContactStringHandler implements Function<String, String> {
        private StringBuilder sb = new StringBuilder();

        @Override
        public String apply(String str) {
            sb.append(str).append(newLine);
            return null;
        }

        public String getContactString() {
            return sb.toString();
        }
    }

    /**
     * 换行符, windows:"\r\n", linux: "\n"
     */
    public final static String newLine = System.getProperty("line.separator");
    /**
     * 缓冲区大小
     */
    private final static int bufferSize = 1024 * 8;

    /**
     * 关闭Closeable对象
     *
     * @param closeable
     */
    public static void close(Closeable closeable) {
        Closer.close(closeable);
    }

    /**
     * 将文件from复制到to<br>
     * 如果to表示目录,则复制成to目录下的同名文件,如果to存在,则覆盖
     *
     * @param from 原文件
     * @param to   目标文件
     * @throws Exception
     */
    public static void copy(File from, File to) {
        if (to.isDirectory())
            to = new File(to, from.getName());
        OutputStream out = null;
        InputStream in = null;
        try {
            out = new FileOutputStream(to);
            in = new FileInputStream(to);
            // 8M缓冲区
            byte[] buffer = new byte[bufferSize];
            // 读取字节数
            int length = -1;
            while ((length = in.read(buffer)) != -1) {
                out.write(buffer, 0, length);
            }
        } catch (Exception e) {
            ExceptionHandler.throwException(e);
        } finally {
            Closer.close(out, in);
        }
    }

    /**
     * 创建指定路径所对应的目录<br>
     * 如果路径指向已存在的文件,则删除该文件并创建目录
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
     * 创建指定路径的上一级目录<br>
     *
     * @param file
     * @return 文件对象
     */
    public static void createParent(File file) {
        createDirectory(file.getParentFile());
    }

    /**
     * 删除指定文件或文件夹<br>
     * 如果指定路径为文件夹,则递归删除子文件夹及其内容
     *
     * @param file
     */
    public static void delete(File file) {
        if (file.isDirectory()) {
            for (File f : file.listFiles())
                delete(f);
        }
        file.delete();
    }

    /**
     * 获取指定路径的绝对路径
     *
     * @param path
     * @return 返回绝对路径
     */
    public static String getAbsolutePath(String path) {
        return new File(path).getAbsolutePath();
    }

    /**
     * 获取文件的字节内容
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
            int read = -1;
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
     * 获取系统标准规范路径
     *
     * @param path
     * @return 返回标准路径, 不含"."或".."
     */
    public static String getCanonicalPath(String path) {
        File file = new File(path);
        try {
            return file.getCanonicalPath();
        } catch (Exception e) {
            return file.getAbsolutePath();
        }
    }

    /**
     * 获取当前路径
     *
     * @return 当前路径
     */
    public static String getCurrentPath() {
        String current = System.getProperty("user.dir");
        if (StringUtils.isEmpty(current))
            current = new File("").getAbsolutePath();
        return current;
    }

    /**
     * 获取路径所在的目录<br>
     * 如果路径本身代表目录,则返回路径本身,否则返回路径的父路径
     *
     * @param path
     * @return 指定路径所在的目录
     */
    public static String getDirectory(String path) {
        File file = new File(path);
        if (file.isDirectory())
            return file.getAbsolutePath();
        String filename = file.getName();
        if (filename.endsWith("/") || filename.endsWith("\\"))
            return file.getName();
        return file.getParent();
    }

    /**
     * 根据指定多个路径进行组合拼装获取文件对象
     *
     * @param paths
     * @return 拼装后的路径指向的文件对象
     */
    public static File getFile(String... paths) {
        File f = null;
        for (String str : paths) {
            if (f == null) {
                f = new File(str);
            } else {
                f = new File(f, str);
            }
        }
        return f;
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
     * 获取指定路径的文件名
     *
     * @param path
     * @return 文件名称
     */
    public static String getFileName(String path) {
        return new File(path).getName();
    }

    /**
     * 获取不含后缀名的文件名称
     *
     * @param path
     * @return 不含后缀的文件名称
     */
    public static String getFileNameWithoutExt(String path) {
        String fileName = getFileName(path);
        int index = fileName.lastIndexOf('.');
        return index == -1 ? fileName : fileName.substring(0, index);
    }

    /**
     * 获取指定路径的父路径
     *
     * @param path
     * @return 上一级路径
     */
    public static String getParent(String path) {
        return new File(path).getParent();
    }

    /**
     * 判断指定的路径是否为绝对路径
     *
     * @param path
     * @return 如果是返回true, 否则返回false
     */
    public static boolean isAbsolutePath(String path) {
        return new File(path).isAbsolute();
    }

    /**
     * 将文件from移动到to<br>
     * 如果to表示目录,则移动为to目录下的同名文件,如果to存在,则覆盖
     *
     * @param from 原文件
     * @param to   目标文件
     */
    public static boolean move(File from, File to) {
        if (to.isDirectory())
            to = new File(to, from.getName());
        if (to.exists()) {
            to.delete();
        }
        return from.renameTo(to);
    }

    /**
     * 读取文件内容
     *
     * @param
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
     * 读取InputStream对象内容后关闭InputStream对象
     *
     * @param
     * @return
     */
    public static String read(InputStream inputStream) {
        return read(new InputStreamReader(inputStream));
    }

    /**
     * 以指定编码格式读取InputStream对象内容后关闭InputStream对象
     *
     * @param
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
     * 读取Reader对象内容后关闭Reader对象
     *
     * @param reader
     * @return
     */
    public static String read(Reader reader) {
        ContactStringHandler handler = new ContactStringHandler();
        readLines(reader, handler);
        return handler.getContactString();
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
     * 以指定编码格式读取InputStream对象的每行内容, 然后关闭InputStream对象
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
     * 读取文件对象每行内容
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
     * 读取Reader对象内容的每行内容
     *
     * @param reader
     */
    public static List<String> readLines(Reader reader) {
        LinesStringHandler handler = new LinesStringHandler();
        readLines(reader, handler);
        return handler.getLines();
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
     * 重命名文件,如果目标名称已存在,则进行覆盖
     *
     * @param file
     * @param newName
     * @return 更名后的文件对象
     */
    public static File rename(File file, String newName) {
        File newFile = new File(newName);
        if (newFile.exists()) {
            ExceptionHandler.throwIf(newFile.delete(), "File already exists but cannot be deleted: "
                    + newFile.getAbsolutePath());
        }
        ExceptionHandler.throwIf(
                file.renameTo(newFile),
                "File: " + file.getAbsolutePath() + " cannot be renamed to: "
                        + newFile.getAbsolutePath());
        return newFile;
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
            int read = -1;
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
}
