package es.udc.ws.isd060.runfic.service.restservice.servlets.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//**************************************************************************************************
//**************************************** NO SE USA ***********************************************
//**************************************************************************************************
public class Debug {
    public static final String DEFAULT_DEBUG_FILE = "C:\\software\\ws-javaexamples-3.4.0\\debug\\debug.txt";
    public static final String DEFAULT_INSCRIPCION_DEBUG_FILE = "C:\\software\\ws-javaexamples-3.4.0\\debug\\debugSales.txt";
    public static final String TAB = "  ";

    public static FileWriter debugCreateFile ( String pathname ){
        FileWriter fileWriter = null;
        try {
            File file  = new File(pathname);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
                fileWriter = new FileWriter(pathname);
            } else {
                System.out.println("File already exists.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
            throw new RuntimeException(e);
            //e.printStackTrace();
        }
        return fileWriter;
    }

    public static FileWriter debugCreateFile (){
       return Debug.debugCreateFile(DEFAULT_DEBUG_FILE);
    }

    public static void debugWriteString( FileWriter fileWriter , boolean lineJumping , String string ){

        try {
            fileWriter.write(string);
            if (lineJumping){
                fileWriter.write("\n");
            }
            fileWriter.close();
            System.out.println(string);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void debugWriteString( FileWriter fileWriter , String string   ){
        Debug.debugWriteString(fileWriter,true,string);
    }

    public static String TAB( int times ){
        String returnString="";
        if (times>0){
            for(int i=0;i<times;i++) {
                returnString += Debug.TAB;
            }
        }
        return returnString;
    }

    public static  String TAB(){
        return Debug.TAB;
    }

}
