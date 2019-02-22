package com.contentsale.utils;


import com.contentsale.common.Const;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.*;
import java.util.Map;

/**
 * Created by wss on 2019/2/22.
 */
@Component
public class CreateHtmlUtils {
    public static String createHtml(String templateName, Map<String, Object> map) throws Exception{

        //创建fm的配置
        Configuration config = new Configuration();

        //指定默认编码格式
        config.setDefaultEncoding("UTF-8");

        //设置模版文件的路径
        config.setDirectoryForTemplateLoading(new File(Const.TEMPLATE_PATH));

        //获得模版包
        Template template = config.getTemplate(templateName);

        String htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);

        return htmlText;
    }
}
