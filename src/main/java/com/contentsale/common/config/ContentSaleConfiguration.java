package com.contentsale.common.config;

/**
 * Created by wss on 2019/1/14.
 */
import com.contentsale.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class ContentSaleConfiguration extends WebMvcConfigurationSupport {
    @Autowired
    PassportInterceptor passportInterceptor;

    //添加地址映射
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/publish").setViewName("publish");
        registry.addViewController("/publishSubmit").setViewName("publishSubmit");
        registry.addViewController("/showDetail").setViewName("showDetail");
        registry.addViewController("/showDetail").setViewName("showDetail");
        registry.addViewController("/cart/show").setViewName("settleAccount");


        registry.addViewController("/t").setViewName("test");
    }

    // 注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor).addPathPatterns("/**").
                excludePathPatterns("/user/login", "/login");
        super.addInterceptors(registry);
    }

    // 加载静态资源
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
        super.addResourceHandlers(registry);
    }

}
