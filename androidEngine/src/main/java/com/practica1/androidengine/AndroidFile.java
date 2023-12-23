package com.practica1.androidengine;

import android.content.Context;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class AndroidFile {
    public AndroidFile() {
    }

    public FileOutputStream openFileOutput(String file, Context context) throws FileNotFoundException {
        return context.openFileOutput(file, Context.MODE_PRIVATE);
    }

    public OutputStreamWriter createOutputStreamWriter(FileOutputStream fileOutputStream) {
        return new OutputStreamWriter(fileOutputStream);
    }

    public InputStreamReader createInputStreamReader(FileInputStream fileInputStream) {
        return new InputStreamReader(fileInputStream);
    }

    public InputStreamReader createInputStreamReader(InputStream inputStream) {
        return new InputStreamReader(inputStream);
    }

    public FileInputStream openFileInputStream(String file, Context context) throws FileNotFoundException {
        return context.openFileInput(file);
    }

    public InputStream createInputStream(Context context, String filePath) throws IOException {
        return context.getAssets().open(filePath);
    }

    public String[] listFiles(Context context, String file) throws IOException {
        String[] dir = context.getAssets().list(file);
        return dir;
    }
}
