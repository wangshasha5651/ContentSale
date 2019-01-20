package com.contentsale.utils;

import com.contentsale.controller.viewobject.ItemVO;
import com.contentsale.dataobject.ItemDO;
import com.contentsale.service.model.ItemModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by wss on 2019/1/17.
 */

@Component
public class ItemUtils {

    public static String[] IMAGE_FILE_EXT = new String[]{"png", "bmp", "jpg", "jpeg"};
    public static boolean isFileAllowed(String fileExt){
        for(String ext : IMAGE_FILE_EXT){
            if(ext.equals(fileExt)){
                return true;
            }
        }
        return false;
    }



    public static ItemDO convertItemDOFromItemModel(ItemModel itemModel){
        if(itemModel == null){
            return null;
        }
        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemModel, itemDO);

        // 由于数据库中price是double类型，model中定义为bigdecimal，直接用BeanUtils的copyProperties方法的可能会有精度损失
        itemDO.setPrice(itemModel.getPrice().doubleValue());

        return itemDO;
    }

    public static ItemModel convertModelFromDataObject(ItemDO itemDO){
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemDO, itemModel);
        itemModel.setPrice(new BigDecimal(itemDO.getPrice()));

        return itemModel;
    }

    public static ItemVO convertVOFromModel(ItemModel itemModel){
        if(itemModel == null){
            return null;
        }

        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel, itemVO);

        return itemVO;
    }

    public static List<ItemVO> convertVOListFromModelList(List<ItemModel> itemModelList){

        List<ItemVO> itemVOList = itemModelList.stream().map(itemModel -> {
            ItemVO itemVO = ItemUtils.convertVOFromModel(itemModel);
            return itemVO;
        }).collect(Collectors.toList());

        return itemVOList;
    }

    public static List<ItemModel> convertModelListFromDOList(List<ItemDO> itemDOList){
        List<ItemModel> itemModelList = itemDOList.stream().map(itemDO -> {
            ItemModel itemModel = ItemUtils.convertModelFromDataObject(itemDO);
            return itemModel;
        }).collect(Collectors.toList());

        return itemModelList;
    }
}
