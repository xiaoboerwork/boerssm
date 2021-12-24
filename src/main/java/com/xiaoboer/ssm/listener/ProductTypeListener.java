package com.xiaoboer.ssm.listener;

import com.xiaoboer.ssm.pojo.ProductType;
import com.xiaoboer.ssm.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

@WebListener
public class ProductTypeListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext_*.xml");
        ProductTypeService productTypeService = (ProductTypeService) context.getBean("ProductTypeServiceImpl");
        List<ProductType> list = productTypeService.getAll();
        servletContextEvent.getServletContext().setAttribute("typeList", list);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
