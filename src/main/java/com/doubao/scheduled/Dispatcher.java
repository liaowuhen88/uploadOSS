package com.doubao.scheduled;

import com.doubao.service.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class Dispatcher {

    private Logger log = LoggerFactory.getLogger(Dispatcher.class);
    @Autowired
    private UploadService uploadService;


    /**
     * 定时上传
     */
    @Scheduled(cron = "0 0 7 17 * * ?")
    public void uploadA(){
        try {
            upload();
        } catch (Exception e) {
            log.error("上传失败",e);
        }
    }


    /**
     * 启动上传
     * @throws Exception
     */
    @Scheduled(fixedRate = 1000*60*60*24*365)
    public void uploadB(){
        try {
            upload();
        } catch (Exception e) {
            log.error("上传失败",e);
        }
    }


    // 5s之后执行
   // @Scheduled(fixedDelay = 100000)
    //@Scheduled(cron = "0 0/1 * * * ?")
    public void upload(){
        log.info("run");
        Calendar calendar =  Calendar.getInstance();
      /*
        calendar.add(Calendar.DATE,-1);
        String date = DateFormatUtils.format(calendar.getTimeInMillis(),DateFormatUtils.ISO_DATE_FORMAT.getPattern());
       */

        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");

        // 用户根目录
        String rootFile = System.getProperties().getProperty("user.home");
        String down= "Downloads";
        // 文件用途   medicaleport
        String type = "health_examination";

        String rootName = rootFile + File.separator+down+File.separator+type;

        File root = new File(rootName);

        if(null != root){
            log.info("root["+root.getAbsolutePath()+"]");

            File[] files = root.listFiles();

            if(null != files){
                 for(int i=0;i<files.length;i++){
                     File fi = files[i];
                     if(null != fi){
                         log.info("fi["+fi.getAbsolutePath()+"]");
                         File[] fis = fi.listFiles();

                         for(int j=0;j<fis.length;j++){
                             File f = fis[j];
                             if(null != f){
                                 log.info("f["+f.getAbsolutePath()+"]");
                                 Date da;
                                 try {
                                     da = formatter.parse(f.getName());
                                     calendar.setTime(da);

                                     if(Calendar.getInstance().after(calendar)){
                                         log.info(f.getName()+"上传");
                                         try {
                                             uploadService.upload(f);
                                         } catch (IOException e) {
                                            log.error("",e);
                                         }
                                     }else {
                                         log.info(f.getName()+"不上传");
                                     }

                                 } catch (ParseException e) {
                                     log.error("",e);
                                 }
                             }

                         }
                     }


                 }
            }else {
                log.info("root["+root.getAbsolutePath()+"] is null");
            }
        }else{
            log.info("root is null");
        }



    }


}
