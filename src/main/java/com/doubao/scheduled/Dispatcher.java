package com.doubao.scheduled;

import com.doubao.service.UploadService;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class Dispatcher {

    private Logger log = LoggerFactory.getLogger(Dispatcher.class);
    @Autowired
    private UploadService uploadService;


    /**
     * 定时上传
     */
    @Scheduled(cron = "0 0 7,17,23 * * ?")
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
    public void upload() throws Exception {
        log.info("run");

        String date = DateFormatUtils.format(System.currentTimeMillis(),DateFormatUtils.ISO_DATE_FORMAT.getPattern());
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
                                 if(f.getName().equals(date)){
                                     uploadService.upload(f);
                                 }else {
                                     log.info(f.getName()+"!="+date);
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
