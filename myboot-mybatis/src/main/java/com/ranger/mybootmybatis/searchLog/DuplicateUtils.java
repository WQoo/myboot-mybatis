package com.ranger.mybootmybatis.searchLog;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author qiang.wang01@hand-china.com
 * @version 1.0
 * @name DuplicateUtils
 * @description
 * @date 2019-08-26
 */
public class DuplicateUtils {

    /**
     * 根据给出的数据，往给定的文件形参中追加一行或者几行数据
     *
     * @param splitFilename
     * @throws IOException
     */
    public static Path appendFile(String splitFilename,
                                  Iterable<? extends CharSequence> accessTimes, Charset cs) throws IOException {
        if(accessTimes != null){
            Path target = Paths.get(splitFilename);
            File file = new File(splitFilename);
            if(!file.exists()){
                createFile(splitFilename);
            }
            return Files.write(target, accessTimes, cs, StandardOpenOption.APPEND);
        }

        return null;
    }

    /**
     * 创建文件
     * @throws IOException
     */
    public static void createFile(String splitFilename) throws IOException {
        Path target = Paths.get(splitFilename);
        Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rw-rw-rw-");
        FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(perms);
        Files.createFile(target, attr);
    }

    public static Date stringToDate(String dateStr, String dateStyle){
        if(dateStr == null || "".equals(dateStr))
            return null;

        DateFormat format = new SimpleDateFormat(dateStyle);//"yyyy-MM-dd hh:mm:ss");
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String dateToString(Date date,String dateStyle){
        if(date == null)
            return null;

        DateFormat format = new SimpleDateFormat(dateStyle);
        return format.format(date);
    }

    /**
     * 根据间隔时间，判断列表中的数据是否已经大于magic给出的魔法数
     * 返回true or false
     *
     * @param dateStrs
     * @param intervalDate
     * @param magic
     * @return
     * @throws ParseException
     */
    public static boolean attack(List<String> dateStrs, long intervalDate, int magic) {
        if(dateStrs == null || dateStrs.size() < magic){
            return false;
        }

        List<Date> dates = new ArrayList<Date>();
        for(String date : dateStrs){
            if(date != null && !"".equals(date))
                dates.add(stringToDate(date,"dd/MM/yyyy hh:mm:ss"));
        }

        Collections.sort(dates);
        return judgeAttack(dates,intervalDate,magic);
    }

    public static boolean judgeAttack(List<Date> sequenceDates,long intervalDate,int magic){
        if(sequenceDates == null || sequenceDates.size() < magic){
            return false;
        }

        for(int x = 0; x < sequenceDates.size() && x <= sequenceDates.size() - magic;x++){
            Date dateAfter5 = new Date(sequenceDates.get(x).getTime() + intervalDate);

            int count = 1;
            for(int i = x + 1;i< sequenceDates.size();i++){
                Date compareDate = sequenceDates.get(i);

                if(compareDate.before(dateAfter5))
                    count ++ ;
                else
                    break;
            }

            if(count >= magic)
                return true;
        }

        return false;
    }

    /**
     * 判断在间隔时间内，是否有大于magic的上限的数据集合，
     * 如果有，则返回满足条件的集合
     * 如果找不到满足条件的，就返回null
     *
     * @param sequenceDates 已经按照时间顺序排序了的数组
     * @param intervalDate
     * @param magic
     * @return
     */
    public static List<Date> attackTimes(List<Date> sequenceDates,long intervalDate,int magic){
        if(sequenceDates == null || sequenceDates.size() < magic){
            return null;
        }

        List<Date> res = new ArrayList<Date>();
        for(int x = 0; x < sequenceDates.size() && x <= sequenceDates.size() - magic;x++){
            Date souceDate = sequenceDates.get(x);
            Date dateAfter5 = new Date(souceDate.getTime() + intervalDate);

            res.add(souceDate);

            for(int i = x + 1;i< sequenceDates.size();i++){
                Date compareDate = sequenceDates.get(i);

                if(compareDate.before(dateAfter5)){
                    res.add(compareDate);
                }else
                    break;
            }

            if(res.size() >= magic)
                return res;
            else
                res.clear();
        }

        return null;
    }

    public static List<Date> attackList(List<String> dateStrs,long intervalDate,int magic){
        if(dateStrs == null || dateStrs.size() < magic){
            return null;
        }

        List<Date> dates = new ArrayList<Date>();
        for(String date : dateStrs){
            if(date != null && !"".equals(date))
                dates.add(stringToDate(date,"dd/MM/yyyy hh:mm:ss"));
        }

        Collections.sort(dates);
        return attackTimes(dates,intervalDate,magic);
    }
}
