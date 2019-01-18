package com.contentsale.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.contentsale.utils.ItemUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

/**
 * Created by wss on 2019/1/18.
 */

@Service
public class CloudServiceImpl {

    //设置云账号的ACCESS_KEY和SECRET_KEY
    private static String ACCESS_KEY = "I-kDiP23nMXnb5lOS9mAoMW8pmSrWk0JHsqocOzR";
    private static String SECRET_KEY = "paCWAnMayEVC1dLe2eyWwlthjh3Wk6GJJLyX1wCQ";
    //要上传的空间
    private static String bucketname = "contentsale";

    //密钥配置
    private static Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    //创建上传对象
    private static UploadManager uploadManager = new UploadManager();

    private static String QINIU_IMAGE_DOMAIN = "http://plitybg9g.bkt.clouddn.com/";


    //简单上传，使用默认策略，需设置上传的空间名
    public static String getUpToken() {
        return auth.uploadToken(bucketname);
    }


    public String saveImage(MultipartFile file) throws IOException {
        try {
            int dotPos = file.getOriginalFilename().lastIndexOf(".");
            if (dotPos < 0) {
                return null;
            }
            String fileExt = file.getOriginalFilename().substring(dotPos + 1).toLowerCase();
            if (!ItemUtils.isFileAllowed(fileExt)) {
                return null;
            }

            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
            //调用put方法上传
            Response res = uploadManager.put(file.getBytes(), fileName, getUpToken());

            //返回云存储上的url
            String finalUrl = QINIU_IMAGE_DOMAIN + JSONObject.parseObject(res.bodyString()).get("key");
            return finalUrl;
        } catch (QiniuException e) {
            // 请求失败时打印的异常的信息
            return null;
        }
    }


    public String downloadImageByUrl(String urlString){

        try {


            int dotPos = urlString.lastIndexOf(".");
            if (dotPos < 0) {
                return null;
            }
            String fileExt = urlString.substring(dotPos + 1).toLowerCase();

            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;

            // 构造URL
            URL url = new URL(urlString);
            // 打开连接
            URLConnection con = url.openConnection();
            //设置请求超时为5s
            con.setConnectTimeout(5 * 1000);
            // 输入流
            InputStream is = con.getInputStream();

            byte[] getData = readInputStream(is);

            //调用put方法上传
            Response res = uploadManager.put(getData, fileName, getUpToken());

            //返回云存储上的url
            String finalUrl = QINIU_IMAGE_DOMAIN + JSONObject.parseObject(res.bodyString()).get("key");

            is.close();
            return finalUrl;
        }catch(Exception e){
            return null;
        }
    }


    private static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

}


