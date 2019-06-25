package com.kazakov.newyou.app.utils;

import android.content.Context;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

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
}
