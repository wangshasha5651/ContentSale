package com.contentsale.dao;

import com.contentsale.dataobject.OrderAllDO;

public interface OrderAllDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_all
     *
     * @mbg.generated Tue Jan 22 09:14:59 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_all
     *
     * @mbg.generated Tue Jan 22 09:14:59 CST 2019
     */
    int insert(OrderAllDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_all
     *
     * @mbg.generated Tue Jan 22 09:14:59 CST 2019
     */
    int insertSelective(OrderAllDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_all
     *
     * @mbg.generated Tue Jan 22 09:14:59 CST 2019
     */
    OrderAllDO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_all
     *
     * @mbg.generated Tue Jan 22 09:14:59 CST 2019
     */
    int updateByPrimaryKeySelective(OrderAllDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_all
     *
     * @mbg.generated Tue Jan 22 09:14:59 CST 2019
     */
    int updateByPrimaryKey(OrderAllDO record);
}