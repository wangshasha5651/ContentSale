package com.contentsale.service;

import com.contentsale.common.error.BusinessException;
import com.contentsale.service.model.ItemModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by wss on 2019/1/16.
 */

public interface ItemService {

    // 创建商品
    ItemModel createItem(ItemModel itemModel) throws BusinessException;

    // 商品列表浏览
    List<ItemModel> listItem();

    // 商品详情浏览
    ItemModel getItemById(Integer id);

    // 保存客户端上传的商品图片
    public String saveImage(MultipartFile file) throws IOException;
}
