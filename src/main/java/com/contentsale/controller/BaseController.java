package com.contentsale.controller;

import com.contentsale.common.error.BusinessException;
import com.contentsale.common.error.EmBusinessError;
import com.contentsale.common.error.GeneralException;
import com.contentsale.common.responese.CommonReturnType;
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

    //定义ExceptionHandler解决未被controller层吸收的exception
    @ExceptionHandler(Exception.class)
    public String handlerException(RedirectAttributesModelMap modelMap, Exception ex){

        if(ex instanceof BusinessException){
            BusinessException businessException = (BusinessException)ex;
            modelMap.addFlashAttribute("viewInfo", CommonReturnType.create(businessException,"fail"));

            // 若错误代码是以2开头，则为登录异常
            if(String.valueOf(businessException.getErrCode()).startsWith("2")){
                return "redirect:/login";
            }

            // 若错误代码是以3开头，则为创建item异常
            if(String.valueOf(businessException.getErrCode()).startsWith("3")){
                return "redirect:/publish";
            }

        }else{ // 如果是其他异常

            GeneralException generalException = new GeneralException(EmBusinessError.UNKNOWN_ERROR.getErrCode(), EmBusinessError.UNKNOWN_ERROR.getErrMsg());
            modelMap.addFlashAttribute("viewInfo", CommonReturnType.create(generalException,"fail"));
            return "redirect:/";
        }
        return "redirect:/";
    }



}
