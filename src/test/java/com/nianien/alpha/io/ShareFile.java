package com.nianien.alpha.io;

import java.io.File;
import java.io.IOException;

import com.nianien.core.io.Files;

public class ShareFile {

    private File file;
    private File writeLock;
    private File readLock;

    public boolean getWriteLock() throws IOException {
        if (!readLock.exists() && writeLock.createNewFile()) {
            if (readLock.exists()) {
                writeLock.delete();
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean getReadLock() throws IOException {
        if (!writeLock.exists()) {
            readLock.createNewFile();
            if (writeLock.exists()) {
                readLock.delete();
                return false;
            }
            return true;
        }
        return false;
    }

    public String read(boolean clean) {
        String content = null;
        if (file.exists()) {
            content = Files.read(file);
            if (clean) {
                write("", false);
            }
        }
        return content;
    }

    public void write(String content, boolean isAppend) {
        if (content == null)
            content = "";
        Files.write(file, content, isAppend);
    }

    public ShareFile(String file) {
        this.file = new File(file);
        this.readLock = new File(file + ".readlock");
        this.writeLock = new File(file + ".writeLock");
        
    }
    
    
    

}
