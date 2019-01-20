package com.contentsale.service.impl;

import com.contentsale.common.error.BusinessException;
import com.contentsale.common.error.EmBusinessError;
import com.contentsale.dao.ItemDOMapper;
import com.contentsale.dataobject.ItemDO;
import com.contentsale.interceptor.model.HostHolder;
import com.contentsale.service.ItemService;
import com.contentsale.service.model.ItemModel;
import com.contentsale.utils.ItemUtils;
import com.contentsale.validator.ValidationResult;
import com.contentsale.validator.ValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by wss on 2019/1/16.
 */

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ValidatorImpl validator;

    @Autowired
    private ItemDOMapper itemDOMapper;

    @Autowired
    private HostHolder hostHolder;


    @Override
    @Transactional // 在事务中进行
    public ItemModel createItem(ItemModel itemModel) throws BusinessException {

        // 入参校验
        ValidationResult result = validator.validate(itemModel);
        if(result.isHasError()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }

        // 转化itemmodel->dataobject
        ItemDO itemDO = ItemUtils.convertItemDOFromItemModel(itemModel);

        // 写入数据库
        itemDOMapper.insertSelective(itemDO);
        itemModel.setId(itemDO.getId());

        // 返回创建完成的对象
        return this.getItemById(itemModel.getId());
    }


    @Override
    public List<ItemModel> listItem() {
        List<ItemDO> itemDOList = itemDOMapper.listItem();

        List<ItemModel> itemModelList = ItemUtils.convertModelListFromDOList(itemDOList);

        return itemModelList;
    }

    @Override
    public List<ItemModel> listNotBoughtItem() {

        List<ItemDO> itemDOAllList = itemDOMapper.listItem();

        List<ItemModel> itemModelNotBoughtList = new ArrayList<>();
        for(ItemDO itemDO: itemDOAllList){
            List<Integer> itemBoughtIdList = hostHolder.getItemBoughtList();
            // 如果已购买的商品id集合中不包含
            if(!itemBoughtIdList.contains(itemDO.getId())){
                ItemModel itemModel = ItemUtils.convertModelFromDataObject(itemDO);
                itemModelNotBoughtList.add(itemModel);
            }
        }

        return itemModelNotBoughtList;
    }

    @Override
    public ItemModel getItemById(Integer id) {
        ItemDO itemDO = itemDOMapper.selectByPrimaryKey(id);
        if(itemDO == null){
            return null;
        }

        ItemModel itemModel = ItemUtils.convertModelFromDataObject(itemDO);

        return itemModel;
    }

    @Override
    public Boolean editItem(ItemModel itemModel) throws BusinessException {
        // 入参校验
        ValidationResult result = validator.validate(itemModel);
        if(result.isHasError()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }

        // 转化itemmodel->dataobject
        ItemDO itemDO = ItemUtils.convertItemDOFromItemModel(itemModel);

        try{
            // update数据库
            itemDOMapper.updateByPrimaryKeySelective(itemDO);
            itemModel.setId(itemDO.getId());
        }catch (Exception e){
            throw new BusinessException(EmBusinessError.SQL_ERROR);
        }

        // 返回结果标识
        return Boolean.TRUE;
    }
}
