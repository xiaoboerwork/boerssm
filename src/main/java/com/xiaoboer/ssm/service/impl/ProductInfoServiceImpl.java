package com.xiaoboer.ssm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoboer.ssm.mapper.ProductInfoMapper;
import com.xiaoboer.ssm.pojo.ProductInfo;
import com.xiaoboer.ssm.pojo.ProductInfoExample;
import com.xiaoboer.ssm.pojo.vo.ProductInfoVo;
import com.xiaoboer.ssm.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    ProductInfoMapper productInfoMapper;

    @Override
    public List<ProductInfo> getAll() {
        List<ProductInfo> productInfos = productInfoMapper.selectByExample(null);
        return productInfos;
    }

    @Override
    public PageInfo splitPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        ProductInfoExample productInfoExample = new ProductInfoExample();

        productInfoExample.setOrderByClause("p_id desc");

        List<ProductInfo> productInfos = productInfoMapper.selectByExample(productInfoExample);

        PageInfo<ProductInfo> pageInfo = new PageInfo<>(productInfos);

        return pageInfo;

    }

    @Override
    public int save(ProductInfo info) {

        int insert = productInfoMapper.insert(info);
        return insert;
    }

    @Override
    public ProductInfo getById(int pId) {
        ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(pId);
        return productInfo;
    }

    @Override
    public int update(ProductInfo info) {
        return productInfoMapper.updateByPrimaryKey(info);
    }

    @Override
    public int delete(int pid) {

        return productInfoMapper.deleteByPrimaryKey(pid);
    }

    @Override
    public int deleteBatch(String[] ids) {
        return productInfoMapper.deleteBatch(ids);

    }

    @Override
    public List<ProductInfo> selectCondition(ProductInfoVo vo) {
        return productInfoMapper.selectCondition(vo);
    }

    @Override
    public PageInfo splitPageVo(ProductInfoVo vo, int pageSize) {
        PageHelper.startPage(vo.getPage(), pageSize);

        List<ProductInfo> infoList = productInfoMapper.selectCondition(vo);

        PageInfo<ProductInfo> pageInfo = new PageInfo<>(infoList);
        return pageInfo;
    }
}
