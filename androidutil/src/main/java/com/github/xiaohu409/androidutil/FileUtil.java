package com.github.xiaohu409.androidutil;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileUtil {

    private Context context;
    private FileChannel fileChannel;
    private FileOutputStream fileOutputStream;
    private FileInputStream fileInputStream;

    public FileUtil(Context context) {
        this.context = context;
    }

    public boolean writeFile(String fileName, String value) {
        boolean result;
        try {
            //File file = new File(context.getFilesDir(), fileName);
            //openFileOutput方法操作的是filesDir目录 context.getFilesDir();
            fileOutputStream = context.openFileOutput(fileName, Context.MODE_APPEND);
            fileChannel = fileOutputStream.getChannel();
            fileChannel.write(ByteBuffer.wrap(value.getBytes()));
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        } finally {
            try {
                if (fileChannel != null) {
                    fileChannel.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public String readFile(String fileName) {
        String result = "";
        try {
//            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            //File file = new File(context.getFilesDir(), fileName);
            //openFileInput方法操作的是filesDir目录 context.getFilesDir();
            fileInputStream = context.openFileInput(fileName);
//            fileChannel = fileInputStream.getChannel();
//            fileChannel.read(byteBuffer);
//            result = byteBuffer.asCharBuffer().toString();
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
            result = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            result = "";
        } finally {
            try {
                if (fileChannel != null) {
                    fileChannel.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
