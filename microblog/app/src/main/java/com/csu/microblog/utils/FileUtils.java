package com.csu.microblog.utils;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {


    public static File bitmapToFile(Context context, Bitmap bitmap, String fileName) throws IOException {
        File f = new File(context.getCacheDir(), fileName);
        f.createNewFile();

        FileOutputStream fos = new FileOutputStream(f);

        bitmap.compress(Bitmap.CompressFormat.PNG, 100 /*ignored for PNG*/, fos);
        fos.flush();
        fos.close();

        return f;
    }
}
