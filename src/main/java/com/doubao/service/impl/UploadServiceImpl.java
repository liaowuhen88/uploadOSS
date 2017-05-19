package com.doubao.service.impl;

import com.doubao.service.UploadService;
import com.oss.service.OssClientService;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


/**
 * Created by liaowuhen on 2017/5/18.
 */

@Service
public class UploadServiceImpl implements UploadService {

    protected static final Logger logger = LoggerFactory.getLogger(UploadServiceImpl.class);

    @Autowired
    private OssClientService ossClientService;

    @Value("${upload_prefix}")
    private String uploadPrefix;

    @Value("${nev}")
    private String nev;

    @Override
    public void upload(String fileName) throws IOException {
         File file = new File(fileName);
         upload(file);
    }

    @Override
    public void upload(File file) throws IOException {
        if (null != file) {
            if (file.exists()) {
                if (file.isFile()) {

                    logger.info("ready to upload[" + file.getAbsolutePath() + "]");
                    try{
                        if(!file.getName().contains("bat")){
                            // File file = new File("C:\\Users\\liaowuhen\\Desktop\\2017-05-12.zip");
                            FileInputStream fileInputStream = new FileInputStream(file);

                            String key = uploadPrefix + "/" + DateFormatUtils.format(System.currentTimeMillis(), DateFormatUtils.ISO_DATE_FORMAT.getPattern()) + "/" + file.getName();

                            String response = ossClientService.uploadPrivateFile(nev, key, fileInputStream, "application/json", false);

                            logger.info(response);

                            boolean flag = file.renameTo(new File(file.getAbsoluteFile()+"bat"));

                            logger.info("["+file.getName()+"]文件名称修改"+flag);

                        }else {
                            logger.info("["+file.getName()+"]已上传，忽略");
                        }

                    }catch (Exception e){
                        logger.error("error",e);
                    }


                } else if (file.isDirectory()) {
                    File[] files = file.listFiles();
                    if (null != files) {
                        for (int i = 0; i < files.length; i++) {
                            File cfile = files[i];
                            upload(cfile);
                        }
                    }
                }
            }else{
                logger.error("文件不存在");
            }


        } else {
            logger.error("["+file.getAbsolutePath()+"]文件为空");
        }


    }
}
