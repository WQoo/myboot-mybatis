package com.ranger.mybootmybatis.checkFile;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author qiang.wang01@hand-china.com
 * @version 1.0
 * @name CheckFile
 * @description
 * @date 2019-07-01
 */
public class CheckFile {
    private static String PATH = "/Volumes/work/usr/local/svn/cmcc/04系统开发/04.代码管理/20报表开发";
    private static String KEY_WORDS = "and gcc.segment1 like '${公司段}%'";
    private static String FILE_SUFFIX = ".cpt";
    private static String FAR_AWATY_PATH = "/Volumes/work/usr/local/fileZ/reportlets";

    private static String pattern = "[\\d\\D]and.*gcc.*segment1[\\d\\D]";

    private static List<String> KEY_WORD_LIST = Arrays.asList("RA_CUSTOMER_TRX_ALL.ATTRIBUTE8",
            "apps.RA_INTERFACE_LINES_ALL.INTERFACE_LINE_ATTRIBUTE2",
            "apps.AR_CASH_RECEIPTS_ALL.ATTRIBUTE15");

    /*NotSave*/
    private static Set<String> fileNameSet = new LinkedHashSet<>();


    public static void main(String[] args) {
        File  file =new File(FAR_AWATY_PATH);
        if(file.isDirectory()){
            loopFile(file);
        }
    }

/*
    public static void main(String args[]) {
        String str = "ewqe and XXX gcc xx segment1 xx like xx '${公司段}%'";
        String pattern = ".*and.*gcc.*segment1.*like.*\\'\\$\\{公司段\\}\\%\\'";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        System.out.println(m.matches());
    }
*/

    public static void loopFile(File file){
        if(file.isDirectory()){
            List<File> fileList = Arrays.asList(file.listFiles());
            fileList.forEach(childFile -> {
                if(childFile.isDirectory()){
                    loopFile(childFile);
                }else{
                    if(childFile.getName().endsWith(FILE_SUFFIX)){
                        isContainWords(childFile,KEY_WORDS);
                    }
                }
            });
            fileNameSet.forEach(set->{
                System.out.println(set.toString());
            });

        }
    }

    public static void isContainWords(File file,String words){
        InputStream inputStream = null;
        BufferedReader reader = null;
        StringBuffer stringBuffer;
        List<String> KEY_WORD_LIST = Arrays.asList("DW.DIM_CUX_COA_AC1_V",
                "DW.DIM_CUX_COA_AC2_V",
                "DW.DIM_CUX_COA_AC3_V",
                "DW.DIM_CUX_COA_SEG1_V",
                "DW.DIM_CUX_COA_SEG2_V",
                "DW.DIM_CUX_COA_SEG3_V",
                "DW.DIM_CUX_COA_SEG4_V",
                "DW.DIM_CUX_COA_SEG5_V",
                "DW.DIM_CUX_COA_SEG6_V"
        );
        try {
            inputStream = new FileInputStream(file);
            String line; // 用来保存每行读取的内容
            reader = new BufferedReader(new InputStreamReader(inputStream));
            stringBuffer = new StringBuffer();
            line = reader.readLine(); // 读取第一行
            while (line != null) { // 如果 line 为空说明读完了
                stringBuffer.append(line); // 将读到的内容添加到 buffer 中
                stringBuffer.append("\n"); // 添加换行符
                line = reader.readLine(); // 读取下一行
            }
            String str = stringBuffer.toString();
/*          Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(str);
            if(m.find()){
                System.out.println(file.getName());
                System.out.println("匹配子句：----------》" + m.group());
            }*/
            KEY_WORD_LIST.forEach(key->{
                if(stringBuffer.toString().contains(key)){
                    fileNameSet.add(file.getName());
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(reader);
            IOUtils.closeQuietly(inputStream);
        }

    }
}
