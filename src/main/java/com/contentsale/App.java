package com.contentsale;

import com.contentsale.dao.UserDOMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.contentsale.dataobject.UserDO;

/**
 * Created by wss on 2019/1/9.
 */
@SpringBootApplication(scanBasePackages = {"com.contentsale"})
@RestController
@MapperScan("com.contentsale.dao")
public class App 
{
    @Autowired
    private UserDOMapper userDOMapper;

    @RequestMapping("/")
    public String home(){
        UserDO userDo = userDOMapper.selectByPrimaryKey(1);
        if(userDo == null){
            return "用户不存在";
        }else{
            return userDo.getName();
        }

    }

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        SpringApplication.run(App.class, args);


    }
}
