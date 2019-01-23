package org.web.autobuild.tool;

import java.util.zip.*;
import java.io.*;

public class ZipHelper {

	public static void zipCompress(String[] srcFiles, String desFile) throws IOException {

		String[] fileNames = new String[srcFiles.length];
		for (int i = 0; i < srcFiles.length; i++) {
			fileNames[i] = parse(srcFiles[i]);
		}

		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(desFile));
		ZipOutputStream zos = new ZipOutputStream(bos);

		for (int i = 0; i < fileNames.length; i++) {
			// 创建Zip条目
			ZipEntry entry = new ZipEntry(srcFiles[i].substring(srcFiles[i].lastIndexOf(File.separator) + 1));
			zos.putNextEntry(entry);

			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcFiles[i]));

			byte[] b = new byte[bis.available()];
			bis.read(b, 0, bis.available());
			zos.write(b);
			bis.close();
			zos.closeEntry();
		}

		zos.flush();
		zos.close();

	}

	// 解析文件名
	private static String parse(String srcFile) {
		int location = srcFile.lastIndexOf("/");
		String fileName = srcFile.substring(location + 1);
		return fileName;
	}

	public static void main(String[] args) throws IOException {
		String[] srcFiles = new String[1];
		srcFiles[0] = "D:/out.log";
		zipCompress(srcFiles, "D:/d.zip");
	}
}
