package fr.triedge.sekai.sdk.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
/**
 * Utility class to provide some handy method about zip management
 * Credit goes to: https://www.baeldung.com/java-compress-and-uncompress
 * 
 * @author sbi
 *
 */
public class ZipHelper {
	
	/**
	 * Zips a folder. The folder will be contained inside the zip.
	 * 
	 * @param sourceFile - The path to the source folder to zip
	 * @param target - The name and path to the target zipped file. e.g: path_to_file/myZip.zip
	 * @throws IOException - If the zipping failed
	 */
	public static void zipFolder(String sourceFile, String target) throws IOException {
        FileOutputStream fos = new FileOutputStream(target);
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        File fileToZip = new File(sourceFile);
 
        zipFile(fileToZip, fileToZip.getName(), zipOut);
        zipOut.close();
        fos.close();
    }
 
	/**
	 * Internal zip method to zip a specific file into the folder
	 * 
	 * @param fileToZip - The file to zip
	 * @param fileName - The name of the file
	 * @param zipOut - The outputstream of the current zipping process
	 * @throws IOException - If the zipping failed
	 */
    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }
    
    /**
     * Unzips a zip file
     * 
     * @param fileZip - The zip file to unzip
     * @param destDir - The path to where the zip file will be unzipped
     * @throws IOException - If the zipping failed
     */
    public static void unzipFolder(String fileZip, String destDir) throws IOException {
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            File newFile = newFile(new File(destDir), zipEntry);
            FileOutputStream fos = new FileOutputStream(newFile);
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
    }
    
    /**
     * Internal unzip method used to unzip a specific file
     * 
     * @param destinationDir
     * @param zipEntry
     * @return
     * @throws IOException
     */
    private static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());
         
        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();
         
        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }
         
        return destFile;
    }
}
