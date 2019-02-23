package com.contentsale.service.impl;

import com.contentsale.common.Const;
import com.contentsale.common.error.BusinessException;
import com.contentsale.common.error.EmBusinessError;
import com.contentsale.controller.viewobject.ItemVO;
import com.contentsale.dao.ItemDOMapper;
import com.contentsale.dataobject.ItemDO;
import com.contentsale.interceptor.model.HostHolder;
import com.contentsale.service.ItemService;
import com.contentsale.service.model.ItemModel;
import com.contentsale.service.model.OrderItemModel;
import com.contentsale.utils.CreateHtmlUtils;
import com.contentsale.utils.ItemUtils;
import com.contentsale.utils.JedisAdapter;
import com.contentsale.utils.RedisKeyUtil;
import com.contentsale.validator.ValidationResult;
import com.contentsale.validator.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.stream.Collectors;

/**
 * Created by wss on 2019/1/16.
 */

@Service
public class ItemServiceImpl implements ItemService {

    private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

    @Autowired
    private ValidatorImpl validator;

    @Autowired
    private ItemDOMapper itemDOMapper;

    @Override
    @Transactional // 在事务中进行
    public ItemModel createItem(ItemModel itemModel){

        try{
            // 入参校验
            ValidationResult result = validator.validate(itemModel);
            if(result.isHasError()){
                return null;
            }

            // 转化itemmodel->dataobject
            ItemDO itemDO = ItemUtils.convertItemDOFromItemModel(itemModel);

            // 写入数据库
            itemDOMapper.insertSelective(itemDO);
            itemModel.setId(itemDO.getId());

            // 返回创建完成的对象
            return this.getItemById(itemModel.getId());
        }catch (Exception e){
            logger.error("创建商品服务层发生异常：" + e.getMessage());
            return null;
        }

    }


    @Override
    public List<ItemModel> listItem() {
        try{
            List<ItemDO> itemDOList = itemDOMapper.listItem();

            List<ItemModel> itemModelList = ItemUtils.convertModelListFromDOList(itemDOList);

            return itemModelList;
        }catch (Exception e){
            logger.error("查看所有商品服务层发生异常：" + e.getMessage());
            return null;
        }

    }

    @Override
    public List<ItemModel> listNotBoughtItem(List<Integer> itemBoughtIdList) {
        try{
            List<ItemDO> itemDOAllList = itemDOMapper.listItem();

            List<ItemModel> itemModelNotBoughtList = new ArrayList<>();
            for(ItemDO itemDO: itemDOAllList){
                // 如果已购买的商品id集合中不包含
                if(!itemBoughtIdList.contains(itemDO.getId())){
                    ItemModel itemModel = ItemUtils.convertModelFromDataObject(itemDO);
                    itemModelNotBoughtList.add(itemModel);
                }
            }

            return itemModelNotBoughtList;
        }catch (Exception e){
            logger.error("查看未购买商品服务层发生异常：" + e.getMessage());
            return null;
        }
    }

    @Override
    public ItemModel getItemById(Integer id) {
        try{
            ItemDO itemDO = itemDOMapper.selectByPrimaryKey(id);
            if(itemDO == null){
                return null;
            }

            ItemModel itemModel = ItemUtils.convertModelFromDataObject(itemDO);

            return itemModel;
        }catch (Exception e){
            logger.error("获得商品模型服务层发生异常：" + e.getMessage());
            return null;
        }
    }

    @Override
    public Boolean editItem(ItemModel itemModel) throws BusinessException {
        try{
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

            }

            // 返回结果标识
            return true;
        }catch (Exception e){
            logger.error("编辑商品服务层发生异常：" + e.getMessage());
            return false;
        }
    }

    @Override
    @Transactional(propagation = Propagation.NESTED)  //在SettleAccount的子事务中进行
    public Boolean changeSales(List<OrderItemModel> orderItemModelList) throws BusinessException {
        try{
            for(OrderItemModel orderItemModel : orderItemModelList){

                Integer itemId = orderItemModel.getItemId();
                Integer quantity = orderItemModel.getQuantity();

                try{
                    ItemDO itemDO = itemDOMapper.selectByPrimaryKey(itemId);
                    itemDO.setSales(itemDO.getSales() + quantity);

                    itemDOMapper.updateByPrimaryKeySelective(itemDO);
                }catch (Exception e){
                    throw new BusinessException(EmBusinessError.SQL_ERROR);
                }
            }

            return Boolean.TRUE;
        }catch (Exception e){
            logger.error("更改销量服务层发生异常：" + e.getMessage());
            return false;
        }

    }

    @Override
    @Transactional // 在事务中进行
    public Boolean deleteItem(Integer id) throws BusinessException {

        try{
            itemDOMapper.deleteByPrimaryKey(id);
            return Boolean.TRUE;
        }catch(Exception e){
            logger.error("删除商品服务层发生异常：" + e.getMessage());
            return false;
        }
    }

    @Override
    public String getIndexHtmlTextNotLogin() throws Exception {
        List<ItemModel> itemModelList = listItem();

        List<ItemVO> itemVOList = ItemUtils.convertVOListFromModelList(itemModelList);

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("itemList", itemVOList);

        String htmltext = CreateHtmlUtils.createHtml(Const.INDEX_FILE_NAME, map);

        return htmltext;
    }

    @Override
    public String getIndexHtmlTextIfLogined(Map<String, Object> map) throws Exception {
        List<ItemModel> itemModelList = listItem();

        List<ItemVO> itemVOList = ItemUtils.convertVOListFromModelList(itemModelList);

        map.put("itemList", itemVOList);

        String htmltext = CreateHtmlUtils.createHtml(Const.INDEX_FILE_NAME, map);

        return htmltext;
    }


}
