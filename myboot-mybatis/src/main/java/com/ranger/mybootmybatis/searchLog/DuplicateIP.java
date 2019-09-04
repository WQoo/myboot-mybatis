package com.ranger.mybootmybatis.searchLog;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author qiang.wang01@hand-china.com
 * @version 1.0
 * @name DuplicateIP
 * @description
 * @date 2019-08-26
 */

public class DuplicateIP {
    private String delimiter = "\"\"";
    private String FILE_PRE = "ip_";

    private int MAGIC = 10,BATCH_MAGIC = 500;
    private String root = "/Users/ranger/Desktop/ip_collector/";

    private String filename = "";

    public DuplicateIP(final String filename) {
        this.filename = filename;
    }

    /**
     * 将大文件拆分成较小的文件，进行预处理
     * @throws IOException
     */
    private void preProcess() throws IOException {
        //Path newfile = FileSystems.getDefault().getPath(filename);
        BufferedInputStream fis = new BufferedInputStream(new FileInputStream(new File(filename)));
        // 用5M的缓冲读取文本文件
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis,"utf-8"),5*1024*1024);

        //假设文件是10G，那么先根据hashcode拆成小文件，再进行读写判断
        //如果不拆分文件，将ip地址当做key，访问时间当做value存到hashmap时，
        //当来访的ip地址足够多的情况下，内存开销吃不消
//		List<Entity> entities = new ArrayList<Entity>();

        //存放ip的hashcode->accessTimes集合
        Map<String, List<String>> hashcodeMap = new HashMap<String,List<String>>();
        String line = "";
        int count = 0;
        while((line = reader.readLine()) != null){
            line=line.replaceAll("\t","");
            String split[] = line.split(delimiter);
            String optId = null;
            String username = null;
            if(split != null && split.length >= 2){
                //根据ip的hashcode这样拆分文件，拆分后的文件大小在1G上下波动
                //ip+操作内容 取哈希
                optId = split[0].replaceAll("\"","");
                username = split[1].replaceAll("\"","");
                int serial = (optId+username).hashCode() % MAGIC;

                String splitFilename = FILE_PRE + serial;
                List<String> lines = hashcodeMap.get(splitFilename);
                if(lines == null){
                    lines = new ArrayList<String>();

                    hashcodeMap.put(splitFilename, lines);
                }
                lines.add(line);
            }

            count ++;
            if(count > 0 && count % BATCH_MAGIC == 0){
                for(Map.Entry<String, List<String>> entry : hashcodeMap.entrySet()){
                    //System.out.println(entry.getKey()+"--->"+entry.getValue());
                    DuplicateUtils.appendFile(root + entry.getKey(), entry.getValue(), Charset.forName("UTF-8"));
                }
                //一次操作500之后清空，重新执行
                hashcodeMap.clear();
            }
        }
        System.out.println(count);
        reader.close();
        fis.close();
    }

    private boolean process() throws IOException{
        Path target = Paths.get(root);

        //ip -> List<Date>
        Map<String,List<Date>> resMap = new HashMap<String,List<Date>>();
        this.recurseFile(target,resMap);

        for(Map.Entry<String, List<Date>> entry : resMap.entrySet()){
            System.out.println(entry.getKey());
            System.out.println("访问次数："+ entry.getValue().size());
            System.out.println("访问时间段：");
            for(Date date : entry.getValue()){
              System.out.println(date);
            }
        }

        return true;
    }

    /**
     * 递归执行，将5分钟内访问超过阈值的ip找出来
     *
     * @param parent
     * @return
     * @throws IOException
     */
    private void recurseFile(Path parent, Map<String,List<Date>> resMap) throws IOException{
        //Path target = Paths.get(dir);
        if(!Files.exists(parent) || !Files.isDirectory(parent)){
            return;
        }
        List<File> fileList= Arrays.asList(new File(root).listFiles());
        for(File file:fileList){
            if(file.getName().startsWith(FILE_PRE)){
                List<String> lines = Files.readAllLines(file.toPath(), Charset.forName("UTF-8"));
                judgeAndcollection(lines,resMap);
            }
        }
    }

    /**
     * 根据从较小文件读上来的每行ip accessTimes进行判断符合条件的ip
     * 并放入resMap
     *
     * @param lines
     * @param resMap
     */
    private void judgeAndcollection(List<String> lines,Map<String,List<Date>> resMap) {
        if(lines != null){
            //ip->List<String>accessTimes
            Map<String,List<String>> judgeMap = new HashMap<String,List<String>>();
            for(String line : lines){
                line = line.trim();
                line=line.replaceAll("\t","");
                String split[] = line.split(delimiter);

                String userName =split[1];
                String opt =split[0].replaceAll("\"","");
                List<String> accessTimes = judgeMap.get(userName+"_"+opt);
                if(accessTimes == null){
                    accessTimes = new ArrayList<String>();
                }
                accessTimes.add(split[5]);
                judgeMap.put(userName+"_"+opt, accessTimes);
            }

            if(judgeMap.size() == 0){
                return;
            }

            for(Map.Entry<String, List<String>> entry : judgeMap.entrySet()){
                List<String> acessTimes = entry.getValue();
                //相同ip，先判断整体大于10个
                if(acessTimes != null && acessTimes.size() >= MAGIC){
                    //开始判断在List集合中，120分钟内访问超过MAGIC=10
                    List<Date> attackTimes = DuplicateUtils.attackList(acessTimes, 120 * 60 * 1000, MAGIC);
                    if(attackTimes != null){
                        resMap.put(entry.getKey(), attackTimes);
                    }
                }
            }
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        String filename = "/Users/ranger/Desktop/无标题.txt";
        DuplicateIP dip = new DuplicateIP(filename);
        try {
            dip.preProcess();
            dip.process();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
