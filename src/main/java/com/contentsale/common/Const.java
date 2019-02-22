package com.contentsale.common;

/**
 * Created by wss on 2019/2/20.
 */
public class Const {

    public static final String CURRENT_USER = "currentUser";

    //token过期时间设置
    public static final int LOGIN_EXPIRE_TIME = 15*60; //15分钟

    //主页缓存过期时间设置
    public static final int INDEX_CACHE_EXPIRE_TIME = 5*60; //5分钟

    //项目根目录（包含项目文件夹名）
    public static final String PROJECT_PATH = "E:\\GitRepo\\ContentSale-master\\";

    public static final String TEMPLATE_SUB_PATH = "\\src\\main\\resources\\templates";

    // 页面缓存模板文件路径
    public static final String TEMPLATE_PATH = PROJECT_PATH + TEMPLATE_SUB_PATH;

    public static final String INDEX_FILE_NAME = "index.ftl";

    public static final String NOTBOUGHT_FILE_NAME = "haveNotBought.ftl";

    public static Boolean afterLoginCacheFlag = false;




}
