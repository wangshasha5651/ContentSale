package com.contentsale.controller;

import com.contentsale.controller.viewobject.UserVO;
import com.contentsale.common.error.BusinessException;
import com.contentsale.common.error.EmBusinessError;
import com.contentsale.common.responese.CommonReturnType;
import com.contentsale.dao.LoginTicketDOMapper;
import com.contentsale.dao.UserDOMapper;
import com.contentsale.dao.UserPasswordDOMapper;
import com.contentsale.dataobject.LoginTicketDO;
import com.contentsale.dataobject.UserDO;
import com.contentsale.dataobject.UserPasswordDO;
import com.contentsale.service.UserService;
import com.contentsale.service.model.UserModel;
import com.contentsale.utils.UserUtils;
import com.contentsale.validator.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * Created by wss on 2019/1/9.
 */

@Controller("user")
@RequestMapping("/")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "true") //Access-Control-Allow-Origin
public class UserController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private UserService userService;

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;

    @Autowired
    private LoginTicketDOMapper loginTicketDOMapper;


    @RequestMapping(value="/user/login",method = {RequestMethod.POST})
//    @ResponseBody
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpServletResponse response,
                        RedirectAttributesModelMap modelMap) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {

        try {
            //入参校验
            if (StringUtils.isEmpty(username)) {
                throw new BusinessException(EmBusinessError.LOGIN_USERNAME_EMPTY);
            }
            if (StringUtils.isEmpty(password)) {
                throw new BusinessException(EmBusinessError.LOGIN_PASSWORD_EMPTY);
            }

            //用户登录服务，验证登录是否成功
            UserModel userModel = userService.validateLogin(username, password);

            if (!userModel.getTicket().equals("") && userModel.getTicket() != null) {
                // 下发ticket
                Cookie cookie = new Cookie("ticket", userModel.getTicket());
                cookie.setPath("/");
                response.addCookie(cookie);
            }

            UserVO userVO = UserUtils.convertVOFromModel(userModel);
            modelMap.addFlashAttribute("viewInfo", CommonReturnType.create(userVO));
        }catch (Exception e){
            logger.error("登录异常：" + e.getMessage());
        }

        return "redirect:/";
    }

    @RequestMapping(value="/user/logout",method = {RequestMethod.GET})
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        return "redirect:/login";
    }
}





