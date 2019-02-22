package com.contentsale.dao;

import com.contentsale.dataobject.CartDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cart
     *
     * @mbg.generated Fri Feb 22 11:05:08 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cart
     *
     * @mbg.generated Fri Feb 22 11:05:08 CST 2019
     */
    int insert(CartDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cart
     *
     * @mbg.generated Fri Feb 22 11:05:08 CST 2019
     */
    int insertSelective(CartDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cart
     *
     * @mbg.generated Fri Feb 22 11:05:08 CST 2019
     */
    CartDO selectByPrimaryKey(Integer id);

    CartDO selectByItemIdAndUserId(Integer itemId, Integer userId);

    List<CartDO> listItem(Integer userId);

    int deleteByUserAndItem(@Param("userId") Integer userId, @Param("itemId") Integer itemId);

    int replaceIntoPreventDup(CartDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cart
     *
     * @mbg.generated Fri Feb 22 11:05:08 CST 2019
     */
    int updateByPrimaryKeySelective(CartDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cart
     *
     * @mbg.generated Fri Feb 22 11:05:08 CST 2019
     */
    int updateByPrimaryKey(CartDO record);
}