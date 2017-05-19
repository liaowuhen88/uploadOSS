package com.doubao.service;

import java.io.File;
import java.io.IOException;

/**
 * Created by liaowuhen on 2017/5/18.
 */
public interface UploadService {
    void upload(String fileName) throws IOException;


    void upload(File file) throws IOException;

}
