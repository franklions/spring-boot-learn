package com.franklions.gradle2maven;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-12-06
 * @since Jdk 1.8
 */
public class RunApplication {
    public static void main(String[] args) {
        String source = "/Users/andon/flsh/temp";
        String destRoot = "/Users/andon/flsh/java/apache-maven-3.6.0/repository";
        File rootFile = new File(source);
        for (File file : rootFile.listFiles()) {
            if (file.isDirectory() && file.getName().length()> 0) {
                //将包名转换成文件的层级结构
                String destDir = file.getName().replace(".", File.separator);
                traversalFile(file, source + File.separator + file.getName(), destRoot + File.separator + destDir);
            }
        }
    }

    /**
     *遍历文件
     */
    private static void traversalFile(File rootFile, String source, String destRoot) {
        for (File subFile : rootFile.listFiles()) {
            if (!subFile.getName().equals(".DS_Store")) {
                if (subFile.isDirectory()) {    //文件夹
                    traversalFile(subFile, source, destRoot);
                } else {    //文件
                    File md5Dir = new File(subFile.getParent());
                    String copyPath = new File(md5Dir.getParent()).getAbsolutePath().replace(source, destRoot);
                    File copyDir = new File(copyPath);
                    if (!copyDir.exists()) {
                        copyDir.mkdirs();
                    }
                    File destFile = new File(copyDir, subFile.getName());
                    //copy时发现同一个包同一个版本可能存在多个，在这里打印出来那个依赖包存在多个但不进行copy
                    if (destFile.exists()) {
                        System.out.println(subFile.getAbsolutePath());
                    } else {
                        try {
                            //引入的common.io的包
                            FileUtils.copyFile(subFile, destFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
