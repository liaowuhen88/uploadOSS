package com.doubao;

import com.doubao.utils.SpringUtilsSpider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by liaowuhen on 2017/5/18.
 */

public class Main {
    private final static Logger log= LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) throws Exception {
        SpringUtilsSpider.init();
        log.info("started eb_spider success,waiting ...");
    }
}
