package com.contentsale.controller;

import com.contentsale.common.Const;
import com.contentsale.common.error.BusinessException;
import com.contentsale.common.error.EmBusinessError;
import com.contentsale.common.responese.CommonReturnType;
import com.contentsale.controller.viewobject.ItemVO;
import com.contentsale.controller.viewobject.UserVO;
import com.contentsale.dataobject.FinanceDO;
import com.contentsale.dataobject.ItemDO;
import com.contentsale.interceptor.model.HostHolder;
import com.contentsale.service.FinanceService;
import com.contentsale.service.ItemService;
import com.contentsale.service.impl.CloudServiceImpl;
import com.contentsale.service.model.ItemModel;
import com.contentsale.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by wss on 2019/1/17.
 */

@Controller("/item")
@RequestMapping("/")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "true")
public class ItemController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;

    @Autowired
    private CloudServiceImpl cloudService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private JedisAdapter jedisAdapter;

    @Autowired
    private FinanceService financeService;


    // 创建商品
    @RequestMapping(value="/item/create",method = {RequestMethod.POST})
    @ResponseBody
    public ModelAndView createItem(@RequestParam("title") String title,
                             @RequestParam("summary") String summary,
                             @RequestParam("imgUrl") String imgUrl,
                             @RequestParam("description") String description,
                             @RequestParam("price") BigDecimal price,
                             @RequestParam("singleRadio") Integer singleRadio,
                             RedirectAttributesModelMap modelMap,
                             ModelAndView modelAndView) throws BusinessException {

        try{
            //入参校验
            if(StringUtils.isEmpty(title)){
                throw new BusinessException(EmBusinessError.ITEM_TITLE_EMPTY);
            }
            if(StringUtils.isEmpty(summary)){
                throw new BusinessException(EmBusinessError.ITEM_SUMMARY_EMPTY);
            }
            if(StringUtils.isEmpty(imgUrl)){
                throw new BusinessException(EmBusinessError.ITEM_IMG_EMPTY);
            }
            if(StringUtils.isEmpty(description)){
                throw new BusinessException(EmBusinessError.ITEM_DESCRIPTION_EMPTY);
            }
            if(price == null){
                throw new BusinessException(EmBusinessError.ITEM_PRICE_EMPTY);
            }

            if(singleRadio == 1){
                //当客户端传来的是图片的URL时，下载图片
                String cloudUrl = cloudService.downloadImageByUrl(imgUrl);
                if(cloudUrl != null){
                    imgUrl = cloudUrl;
                }
            }

            // 封装service请求用来创建商品
            ItemModel itemModel = new ItemModel();
            itemModel.setTitle(title);
            itemModel.setSummary(summary);
            itemModel.setImgUrl(imgUrl);
            itemModel.setDescription(description);
            itemModel.setPrice(price);
            itemModel.setSellerId(hostHolder.getUser().getId());

            ItemModel itemModelForReturn = itemService.createItem(itemModel);
            ItemVO itemVO = ItemUtils.convertVOFromModel(itemModelForReturn);

            modelMap.addFlashAttribute("viewInfo", CommonReturnType.create(itemVO));
            modelAndView.addObject("viewInfo", CommonReturnType.create(itemVO));
            modelAndView.setViewName("publishSubmit");
        }catch(Exception e){
            logger.error("发布商品异常：" + e.getMessage());
        }

        return modelAndView;
    }


    // 商品列表浏览(主页浏览)
    @RequestMapping(value="/", method = {RequestMethod.GET}, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String listItem(Integer type, ModelAndView modelAndView) throws Exception {

        UserVO user = hostHolder.getUser();

        // 浏览所有商品
        if(type == null){

            String htmltext = null;
            if(user  == null){
                //清除之前残余
                jedisAdapter.del(RedisKeyUtil.getHomeKey());

                //当缓存中没有主页静态页面时，重新访问数据库，并加载到缓存
                if(!jedisAdapter.exists(RedisKeyUtil.getHomeKey())) {

                    htmltext = itemService.getIndexHtmlTextNotLogin();

                    jedisAdapter.setex(RedisKeyUtil.getHomeKey(), Const.INDEX_CACHE_EXPIRE_TIME,htmltext);

                    return htmltext;

                }else{
                    return jedisAdapter.get(RedisKeyUtil.getHomeKey());
                }
            }else{ //用户已登录

                //若刚登陆，需刷新该用户的对应商品到缓存
                if(Const.afterLoginCacheFlag.equals(Boolean.FALSE)){
                    Const.afterLoginCacheFlag = true;
                    return createIndexCache();
                }else{
                    //已登录，但缓存过期
                    if(!jedisAdapter.exists(RedisKeyUtil.getHomeKey())) {
                        return createIndexCache();
                    }
                    return jedisAdapter.get(RedisKeyUtil.getHomeKey());
                }
            }

        }

        // 浏览未购买的商品
        if(type == 1){
            List<ItemModel> itemNotBoughtModelList = itemService.listNotBoughtItem(financeService.getBoughtIdList(user.getId()));
            List<ItemVO> itemNotBoughtVOList = ItemUtils.convertVOListFromModelList(itemNotBoughtModelList);

            Map<String, Object> map = new HashMap<>();
            map.put("user", user);
            map.put("itemList", itemNotBoughtVOList);

            String htmltext = CreateHtmlUtils.createHtml(Const.NOTBOUGHT_FILE_NAME, map);

            return htmltext;
        }

        return "";
    }

    //单独上传图片
    @RequestMapping(value="/item/uploadImage", method = {RequestMethod.POST})
    @ResponseBody
    public String uploadImage(@RequestParam("image") MultipartFile file){
        try{
            String fileUrl = cloudService.saveImage(file);
            if(fileUrl == null){
                return "";
            }
            return fileUrl;
        }catch(Exception e){
            logger.error("发布商品时，上传图片异常：" + e.getMessage());
            return "";
        }
    }

    // 显示商品详情
    @RequestMapping(value="/showDetail", method = {RequestMethod.GET})
    public ModelAndView show(@RequestParam("id") Integer id, ModelAndView modelAndView) throws BusinessException {

        try{
            ItemModel itemModel = itemService.getItemById(id);

            ItemVO itemVO = ItemUtils.convertVOFromModel(itemModel);

            UserVO user = hostHolder.getUser();
            if(user != null){
                List<Integer> boughtList = financeService.getBoughtIdList(user.getId());
                Map<String, BigDecimal> priceMap = financeService.getBoughtPriceMap(user.getId());

                modelAndView.addObject("boughtList", boughtList);
                modelAndView.addObject("priceMap", priceMap);
            }


            modelAndView.addObject("item", itemVO);
            modelAndView.setViewName("showDetail");
        }catch (Exception e){
            throw new BusinessException(EmBusinessError.SHOW_DETAIL_ERROR);
        }

        return modelAndView;
    }



    // 获取商品信息发至编辑页
    @RequestMapping(value="/edit", method = {RequestMethod.GET})
    @ResponseBody
    public ModelAndView getItem(Integer id,ModelAndView modelAndView){
        try{
            ItemModel itemModel = itemService.getItemById(id);

            ItemVO itemVO = ItemUtils.convertVOFromModel(itemModel);

            modelAndView.addObject("item", itemVO);
            modelAndView.setViewName("edit");
        }catch (Exception e){
            logger.error("编辑商品时，获取商品信息异常：" + e.getMessage());
        }

        return modelAndView;
    }

    // 修改商品信息
    @RequestMapping(value="/item/edit",method = {RequestMethod.POST})
    @ResponseBody
    public ModelAndView editItem(@RequestParam("id") String id,
                           @RequestParam("title") String titleToEdit,
                           @RequestParam("summary") String summaryToEdit,
                           @RequestParam("imgUrl") String imgUrlToEdit,
                           @RequestParam("description") String descriptionToEdit,
                           @RequestParam("price") BigDecimal priceToEdit,
                           @RequestParam("singleRadio") Integer singleRadio,
                           ModelAndView modelAndView) throws BusinessException {
        try{
            //入参校验
            if(StringUtils.isEmpty(titleToEdit)){
                throw new BusinessException(EmBusinessError.ITEM_TITLE_EMPTY);
            }
            if(StringUtils.isEmpty(summaryToEdit)){
                throw new BusinessException(EmBusinessError.ITEM_SUMMARY_EMPTY);
            }
            if(StringUtils.isEmpty(imgUrlToEdit)){
                throw new BusinessException(EmBusinessError.ITEM_IMG_EMPTY);
            }
            if(StringUtils.isEmpty(descriptionToEdit)){
                throw new BusinessException(EmBusinessError.ITEM_DESCRIPTION_EMPTY);
            }
            if(priceToEdit == null){
                throw new BusinessException(EmBusinessError.ITEM_PRICE_EMPTY);
            }

            if(singleRadio == 1){
                //当客户端传来的是图片的URL时，下载图片
                String cloudUrl = cloudService.downloadImageByUrl(imgUrlToEdit);
                if(cloudUrl != null){
                    imgUrlToEdit = cloudUrl;
                }
            }

            // 封装service请求用来编辑商品
            ItemModel itemModel = new ItemModel();
            itemModel.setId(Integer.valueOf(id));
            itemModel.setTitle(titleToEdit);
            itemModel.setSummary(summaryToEdit);
            itemModel.setImgUrl(imgUrlToEdit);
            itemModel.setDescription(descriptionToEdit);
            itemModel.setPrice(priceToEdit);
            itemModel.setSellerId(hostHolder.getUser().getId());

            itemService.editItem(itemModel);
            ItemVO itemVO = ItemUtils.convertVOFromModel(itemModel);

            modelAndView.addObject("viewInfo", CommonReturnType.create(itemVO));
            modelAndView.setViewName("editSubmit");
        }catch (Exception e){
            logger.error("编辑商品异常：" + e.getMessage());
        }

        return modelAndView;
    }

    // 删除商品
    @RequestMapping(value="/item/delete", method = {RequestMethod.GET})
    @ResponseBody
    public String delete(@RequestParam("id") Integer id) throws BusinessException {
        try{
            Boolean deleteResult = itemService.deleteItem(id);
            if(!deleteResult.equals(Boolean.TRUE)){
                return "fail";
            }
        }catch (Exception e){
            logger.error("删除商品异常：" + e.getMessage());
        }

        return "success";
    }


    //获取登录用户的主页缓存
    private String createIndexCache() throws Exception {
        UserVO user = hostHolder.getUser();

        Map<String, Object> map = new HashMap<>();
        map.put("user", user);


        Integer userId = user.getId();
        // 每个买家已买的商品集合
        if(user.getType() == 1){
            List<Integer> boughtIdList = financeService.getBoughtIdList(userId);
            map.put("boughtList", boughtIdList);

            Map<String, BigDecimal> priceMap = financeService.getBoughtPriceMap(userId);
            map.put("priceMap", boughtIdList);

        }else{
            // 每个卖家已卖的商品集合
            List<Integer> soldList = financeService.getSoldIdList(userId);
            map.put("soldList", soldList);
        }

        String htmltext = itemService.getIndexHtmlTextIfLogined(map);

        jedisAdapter.setex(RedisKeyUtil.getHomeKey(), Const.INDEX_CACHE_EXPIRE_TIME,htmltext);

        return htmltext;
    }

}
