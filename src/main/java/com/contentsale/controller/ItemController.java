package com.contentsale.controller;

import com.contentsale.common.error.BusinessException;
import com.contentsale.common.error.EmBusinessError;
import com.contentsale.common.responese.CommonReturnType;
import com.contentsale.controller.viewobject.ItemVO;
import com.contentsale.service.ItemService;
import com.contentsale.service.model.ItemModel;
import com.contentsale.utils.ItemUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.math.BigDecimal;

/**
 * Created by wss on 2019/1/17.
 */

@Controller("/item")
@RequestMapping("/item")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "true")
public class ItemController extends BaseController {

    @Autowired
    private ItemService itemService;

    // 创建商品的controller
    @RequestMapping(value="/create",method = {RequestMethod.POST})
//    @ResponseBody
    public String createItem(@RequestParam("title") String title,
                                       @RequestParam("summary") String summary,
                                       @RequestParam("imgUrl") String imgUrl,
                                       @RequestParam("description") String description,
                                       @RequestParam("price") BigDecimal price,
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


    @RequestMapping(value="/uploadImage", method = {RequestMethod.POST})
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file){
        try{
            String fileUrl = itemService.saveImage(file);
            if(fileUrl == null){
                return "上传失败";
            }
            return "成功：" + fileUrl;
        }catch(Exception e){
            return "上传失败";
        }
    }
}
