package com.xiaoboer.ssm.service;

import com.github.pagehelper.PageInfo;
import com.xiaoboer.ssm.pojo.ProductInfo;
import com.xiaoboer.ssm.pojo.vo.ProductInfoVo;

import java.util.List;

public interface ProductInfoService {

    public List<ProductInfo> getAll();

    public PageInfo splitPage(int pageNum, int pageSize);

    int save(ProductInfo info);

    ProductInfo getById(int pId);

    int update(ProductInfo info);

    int delete(int pid);

    int deleteBatch(String[] ids);

    List<ProductInfo> selectCondition(ProductInfoVo vo);

    PageInfo splitPageVo(ProductInfoVo vo, int pageSize);
}
