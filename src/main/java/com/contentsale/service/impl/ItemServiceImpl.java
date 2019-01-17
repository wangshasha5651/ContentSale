package com.contentsale.service.impl;

import com.contentsale.common.error.BusinessException;
import com.contentsale.common.error.EmBusinessError;
import com.contentsale.dao.ItemDOMapper;
import com.contentsale.dataobject.ItemDO;
import com.contentsale.service.ItemService;
import com.contentsale.service.model.ItemModel;
import com.contentsale.utils.ItemUtils;
import com.contentsale.validator.ValidationResult;
import com.contentsale.validator.ValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

/**
 * Created by wss on 2019/1/16.
 */

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ValidatorImpl validator;

    @Autowired
    private ItemDOMapper itemDOMapper;


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
    public String saveImage(MultipartFile file) throws IOException{
        // 判断得到的是不是图片
        int dotPos = file.getOriginalFilename().lastIndexOf(".");
        if(dotPos < 0){
            return null;
        }
        String fileExt = file.getOriginalFilename().substring(dotPos+1).toLowerCase();
        // 文件格式不符
        if(!ItemUtils.isFileAllowed(fileExt)){
            return null;
        }

        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + fileExt;
        Files.copy(file.getInputStream(), new File(ItemUtils.IMAGE_DIR + fileName).toPath(),
                StandardCopyOption.REPLACE_EXISTING);

        return ItemUtils.CONTENT_SALE_DOMAIN + "image?name=" + fileName;
    }

    @Override
    public List<ItemModel> listItem() {
        return null;
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
}
