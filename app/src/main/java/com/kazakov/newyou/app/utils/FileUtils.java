package com.kazakov.newyou.app.utils;

import android.content.Context;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public final class FileUtils {

    private FileUtils(){}

    public static String readFile(Context context, int resourceId) throws IOException {
        InputStream is =
                context.getResources()
                        .openRawResource(resourceId);
        String result = IOUtils.toString(is);
        IOUtils.closeQuietly(is);
        return result;
    }

    public static void copy(File fileToCopy, String copyName) throws IOException {
        String copyPath = fileToCopy.getAbsoluteFile() + copyName;
        File copy = new File(copyPath);
        copy.createNewFile();
        Files.copy(fileToCopy.toPath(), copy.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
}
