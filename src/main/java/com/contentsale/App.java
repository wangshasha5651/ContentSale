package com.contentsale;

import com.contentsale.controller.viewobject.UserVO;
import com.contentsale.utils.JedisAdapter;
import org.mybatis.spring.annotation.MapperScan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;



/**
 * Created by wss on 2019/1/9.
 */
@SpringBootApplication(scanBasePackages = {"com.contentsale"})
@RestController
@EnableWebMvc
@MapperScan("com.contentsale.dao")
public class App extends WebMvcConfigurationSupport
{
    @Autowired
    public static JedisAdapter jedisAdapter;

    public static void main( String[] args )
    {

        SpringApplication.run(App.class, args);
    }

//    @Override
//    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
//        super.addResourceHandlers(registry);
//    }

}
