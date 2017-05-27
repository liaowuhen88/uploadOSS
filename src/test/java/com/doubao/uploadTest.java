package com.doubao;


import com.doubao.service.UploadService;
import com.oss.service.OssClientService;
import org.apache.commons.lang.time.DateFormatUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by liaowuhen on 2017/5/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:spring-*.xml"})
public class uploadTest {

    @Autowired
    private OssClientService ossClientService;

    @Autowired
    private UploadService uploadService;


    @Test
    public void upload() throws IOException {
         File file = new File("C:\\Users\\liaowuhen\\Desktop\\2017-05-12.zip");

        FileInputStream fileInputStream = new FileInputStream(file);
        String key ="upload/spider/"+ DateFormatUtils.format(System.currentTimeMillis(),DateFormatUtils.ISO_DATE_FORMAT.getPattern())+"/"+file.getName();

        ossClientService.uploadPrivateFile("dev",key,fileInputStream,"application/json", false);

    }


    @Test
    public void uploadServiceUpload() throws IOException {
        File file = new File("C:\\Users\\liaowuhen\\Downloads\\meinian\\20290358");

        uploadService.upload(file);

    }

    @Test
    public void time() throws ParseException {
        Calendar calendar =  Calendar.getInstance();
        /*calendar.add(Calendar.DATE,1);
        String date = DateFormatUtils.format(calendar.getTimeInMillis(),DateFormatUtils.ISO_DATE_FORMAT.getPattern());
*/
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
        Date da = formatter.parse("2017-05-27");

        calendar.setTime(da);


        boolean flag = Calendar.getInstance().after(calendar);

        System.out.print("date"+flag);

        da = formatter.parse("2017-05-26");

        calendar.setTime(da);


        flag = Calendar.getInstance().after(calendar);


        System.out.print("date"+flag);

    }




}
