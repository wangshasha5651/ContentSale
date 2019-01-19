package com.contentsale.controller;

import com.contentsale.common.error.BusinessException;
import com.contentsale.common.error.EmBusinessError;
import com.contentsale.common.responese.CommonReturnType;
import com.contentsale.controller.viewobject.ItemVO;
import com.contentsale.service.ItemService;
import com.contentsale.service.impl.CloudServiceImpl;
import com.contentsale.service.model.ItemModel;
import com.contentsale.utils.ItemUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by wss on 2019/1/17.
 */

@Controller("/item")
@RequestMapping("/")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "true")
public class ItemController extends BaseController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private CloudServiceImpl cloudService;

    // 创建商品的controller
    @RequestMapping(value="/item/create",method = {RequestMethod.POST})
    public String createItem(@RequestParam("title") String title,
                                       @RequestParam("summary") String summary,
                                       @RequestParam("imgUrl") String imgUrl,
                                       @RequestParam("description") String description,
                                       @RequestParam("price") BigDecimal price,
                                       @RequestParam("singleRadio") Integer singleRadio,
                                       RedirectAttributesModelMap modelMap) throws BusinessException {

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

        ItemModel itemModelForReturn = itemService.createItem(itemModel);
        ItemVO itemVO = ItemUtils.convertVOFromModel(itemModelForReturn);

        modelMap.addFlashAttribute("viewInfo", CommonReturnType.create(itemVO));

        return "redirect:/publishSubmit";
//        return CommonReturnType.create(itemVO);
    }


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
            return "";
        }
    }

    @RequestMapping(value="/showDetail", method = {RequestMethod.GET})
    public ModelAndView uploadImage(@RequestParam("id") Integer id, ModelAndView modelAndView){

        ItemModel itemModel = itemService.getItemById(id);

        ItemVO itemVO = ItemUtils.convertVOFromModel(itemModel);

        modelAndView.addObject("item", itemVO);

        modelAndView.setViewName("showDetail");

        return modelAndView;
    }

    // 商品列表浏览
    @RequestMapping(value="/", method = {RequestMethod.GET})
    @ResponseBody
    public ModelAndView listItem(ModelAndView modelAndView){

        List<ItemModel> itemModelList = itemService.listItem();

        // 使用stream api将list内的itemModel转化为itemVO
        List<ItemVO> itemVOList = itemModelList.stream().map(itemModel -> {
            ItemVO itemVO = ItemUtils.convertVOFromModel(itemModel);
            return itemVO;
        }).collect(Collectors.toList());

        modelAndView.addObject("itemList", itemVOList);
        modelAndView.setViewName("index");

        return modelAndView;
    }



}
