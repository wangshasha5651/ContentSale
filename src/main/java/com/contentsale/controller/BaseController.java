package com.contentsale.controller;

import com.contentsale.common.error.BusinessException;
import com.contentsale.common.error.EmBusinessError;
import com.contentsale.common.error.GeneralException;
import com.contentsale.common.responese.CommonReturnType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wss on 2019/1/10.
 */
public class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    //定义ExceptionHandler解决未被controller层吸收的exception
    @ExceptionHandler(Exception.class)
    public String handlerException(RedirectAttributesModelMap modelMap, Exception ex){

        if(ex instanceof BusinessException){
            BusinessException businessException = (BusinessException)ex;
            modelMap.addFlashAttribute("viewInfo", CommonReturnType.create(businessException,"fail"));

            // 若错误代码是以2开头，则为登录异常
            if(String.valueOf(businessException.getErrCode()).startsWith("2")){
                logger.error("登录异常：" + businessException.getErrMsg());
                modelMap.addFlashAttribute("viewInfo", CommonReturnType.create(businessException, "fail"));
                return "redirect:/login";
            }

            // 若错误代码是以3开头，则为创建item异常
            if(String.valueOf(businessException.getErrCode()).startsWith("3")){
                logger.error("创建商品异常：" + businessException.getErrMsg());
                return "redirect:/publish";
            }

            // 若错误代码是以5开头，则为添加购物车异常
            if(String.valueOf(businessException.getErrCode()).startsWith("5")) {
                logger.error("购物车异常：" + businessException.getErrMsg());
                return "redirect:/";
            }

            // 若错误代码是以6开头，则为商品详情异常
            if(String.valueOf(businessException.getErrCode()).startsWith("6")) {
                logger.error("商品异常：" + businessException.getMessage());
                return "redirect:/";
            }

        }else{ // 如果是其他异常

            GeneralException generalException = new GeneralException(EmBusinessError.UNKNOWN_ERROR.getErrCode(), EmBusinessError.UNKNOWN_ERROR.getErrMsg());
            modelMap.addFlashAttribute("viewInfo", CommonReturnType.create(generalException,"fail"));
            return "redirect:/";
        }
        return "redirect:/";
    }



}
