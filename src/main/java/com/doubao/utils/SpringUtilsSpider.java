package com.doubao.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.beans.Introspector;

public class SpringUtilsSpider {
	
	private static Logger log = LoggerFactory
			.getLogger(SpringUtilsSpider.class);
	
	private static ApplicationContext ctx = new ClassPathXmlApplicationContext(
			"classpath*:spring-*.xml");// 读取bean.xml中的内容
 
	public static void init() {
		log.info("init Spring");
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBeanByClassName(String clazzName) {
		if (StringUtils.isEmpty(clazzName)) {
			return null;
		}

		String beanName = Introspector.decapitalize(clazzName);

		return (T) ctx.getBean(beanName);
	}

}
