package com.example.user.myapplication_sharedprefrences;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileManager {
    private Context m_context;
    public FileManager(Context context){
        m_context = context;
    }

    public void writeInternalFile(String fileName,String content,boolean append) throws IOException{
        writeInternalFile(fileName,content.getBytes(),append);
    }

    public  void  writeInternalFile(String fileName,byte[]content,boolean append) throws  IOException{
        FileOutputStream outputStream = m_context.openFileOutput(fileName,append?  Context.MODE_APPEND : Context.MODE_PRIVATE);
        outputStream.write(content);
        outputStream.close();
    }

    public String readInternalFile(String fileName) throws IOException{
        String content="";
        FileInputStream inputStream=m_context.openFileInput(fileName);
        if(inputStream!=null){
            InputStreamReader streamReader=new InputStreamReader(inputStream);
            BufferedReader bufferedReader=new BufferedReader(streamReader);
            StringBuilder stringBuilder=new StringBuilder();
            while ((content = bufferedReader.readLine())!=null){
                stringBuilder.append(content);
            }
            bufferedReader.close();
            streamReader.close();
            inputStream.close();
            content=stringBuilder.toString();
        }
        return content;

    }

    public boolean deleteInternalFile(String fileName){
        return m_context.deleteFile(fileName);
    }
    String[] getInternalFileList(){
        return m_context.fileList();
    }

    public static boolean isExternalStorageReadable(){
        String state= Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)){
            return true;
        }
        return false;
    }

    public static File getPublicPicturesDirectory(String pictureFolder){
        File file=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),pictureFolder);
        if (!file.mkdir()){
            Log.e("SNAPGuidesError","Directory not created");
        }
        return  file;
    }

}
