package com.nianien.test;

import java.io.File;
import java.util.List;

import com.nianien.core.io.FileSearcher;

public class TestFiles {
	public static void main(String[] args) {
		File dir=new File("F:\\影视\\犯罪现场调查S13");
		List<File> files = FileSearcher.listAllFiles(dir);
		for (File file : files) {
			String filename=file.getName();
			filename=filename.replaceAll("\\.CSI", "");
			System.out.println(filename);
			file.renameTo(new File(dir,filename));
		}
	}
}
