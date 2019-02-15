package com.romaway.commons.db;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.romaway.commons.lang.StringUtils;

/**
 * 数据库工具类，有关数据库的常用操作
 * @author 祝丰华
 *
 */
public class DbUtils {

    /**
     * 
     * 检查数据库是否 存在
     * @param dbFile 数据库文件存放路径 ，如： "/data/data/cn.myapp.examples/databases/myDb.mdb";<br>
     * 其中"cn.myapp.examples" 指应用包名。"myDb.mdb"指数据库文件名
     * 
     * @return 已存在，则返回true,否则返回false
     */
    public static boolean checkDataBase(String dbFile){
        
        if (StringUtils.isEmpty(dbFile)){
            return false;
        }
        
       

        boolean result = false;
        SQLiteDatabase checkDB = null;    

        try{            

            checkDB = SQLiteDatabase.openDatabase(dbFile, null, SQLiteDatabase.OPEN_READONLY);

        }catch(Exception e){

            //database does't exist yet.

       }finally{

           if(checkDB != null){
               result = true;
               checkDB.close();
    
           }
       }

        return result;

   }
    
    /**
     * 复制assert内的数据库文件到系统中（假设手机系统中不存在此文件）
     * @param context 上下文
     * @param assets_name 存放于asserts目录下的数据库文件名字
     * @param targetFile 数据库文件存放路径 ，如： "/data/data/cn.myapp.examples/databases/myDb.mdb";<br>
     * 其中"cn.myapp.examples" 指应用包名。"myDb.mdb"指数据库文件名
     * @throws IOException 若发生异常，则复制失败。
     */
    public static void copyDataBase(Context context,String assets_name,String targetFile) throws IOException{

        //Open your local db as the input stream

        InputStream myInput = context.getAssets().open(assets_name);

        // Path to the just created empty db


        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(targetFile);


        byte[] buffer = new byte[1024];

       int length;

        while ((length = myInput.read(buffer))>0){

            myOutput.write(buffer, 0, length);

        }

        //Close the streams

        myOutput.flush();

        myOutput.close();

        myInput.close();

    }
    
}
