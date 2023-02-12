package com.example.shop.admin.product.util;

import org.apache.commons.io.FilenameUtils;

import java.nio.file.Files;
import java.nio.file.Path;

public class ExistingFileRenameUtils {

    /**
     * test.png
     * test-1.png
     * test-2.png
     * test-3.png
     * test-4.png
     */
    public static String renameIfExists(Path uploadDir, String filename) {
        if (Files.exists(uploadDir.resolve(filename))) {
            return renameAndCheck(uploadDir, filename);
        }
        return filename;
    }

    private static String renameAndCheck(Path uploadDir, String filename) {
        String newName = renameFilename(filename);
        if (Files.exists(uploadDir.resolve(newName))) {
            newName = renameAndCheck(uploadDir, newName);
        }
        return newName;
    }

    private static String renameFilename(String filename) {
        String name = FilenameUtils.getBaseName(filename);
        String[] split = name.split("-(?=[0-9]+$)");
        int counter = split.length > 1 ? Integer.parseInt(split[1]) + 1 : 1;
        return split[0] + "-" + counter + "." + FilenameUtils.getExtension(filename);
    }
}
