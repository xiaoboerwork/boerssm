package com.xiaoboer.ssm.service.impl;

import com.xiaoboer.ssm.mapper.ProductTypeMapper;
import com.xiaoboer.ssm.pojo.ProductType;
import com.xiaoboer.ssm.pojo.ProductTypeExample;
import com.xiaoboer.ssm.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ProductTypeServiceImpl")
public class ProductTypeServiceImpl implements ProductTypeService {
    @Autowired
    ProductTypeMapper productTypeMapper;

    @Override
    public List<ProductType> getAll() {
        return productTypeMapper.selectByExample(new ProductTypeExample());
    }
}
