package com.xiaoboer.ssm.controller;

import com.github.pagehelper.PageInfo;
import com.xiaoboer.ssm.pojo.ProductInfo;
import com.xiaoboer.ssm.pojo.vo.ProductInfoVo;
import com.xiaoboer.ssm.service.ProductInfoService;
import com.xiaoboer.ssm.utils.FileNameUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/prod")
public class ProductInfoAction {

    private static final int PAGE_SIZE = 3;
    @Autowired
    ProductInfoService productInfoService;
    String saveFileName = "";

    @RequestMapping("/getAll")
    public String getAll(Model model){
        List<ProductInfo> productInfos = productInfoService.getAll();

        model.addAttribute("list", productInfos);
        return "product";
    }

    @RequestMapping("/split")
    public String split(Model model, HttpSession session){
        Object prodVo = session.getAttribute("prodVo");

        if(prodVo == null){
            prodVo = new ProductInfoVo();
        }

        PageInfo pageInfo = productInfoService.splitPageVo((ProductInfoVo) prodVo, PAGE_SIZE);

        session.removeAttribute("prodVo");

        model.addAttribute("info", pageInfo);
        return "product";
    }

    @ResponseBody
    @RequestMapping("/ajaxsplit")
    public void ajaxSplit(ProductInfoVo vo, HttpSession session){
        PageInfo pageInfo = productInfoService.splitPageVo(vo, PAGE_SIZE);
        session.setAttribute("info", pageInfo);
    }

    @ResponseBody
    @RequestMapping("/ajaxImg")
    public Object ajaxImg(MultipartFile pimage, HttpServletRequest request){
        saveFileName = FileNameUtil.getUUIDFileName() + FileNameUtil.getFileType(pimage.getOriginalFilename());

        String path = request.getServletContext().getRealPath("/image_big");

//        System.out.println(path);

        try {
            pimage.transferTo(new File(path + File.separator + saveFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject object = new JSONObject();
        object.put("imgurl", saveFileName);
        return object.toString();
    }

    @RequestMapping("/save")
    public String save(ProductInfo info, Model model){
        info.setpImage(saveFileName);
        info.setpDate(new Date());

        int save = productInfoService.save(info);

        if (save >0){
            model.addAttribute("msg", "添加成功");
        }else {
            model.addAttribute("msg", "添加失败");
        }
        saveFileName = "";
        return "forward:/prod/split.action";
    }

    @RequestMapping("/one")
    public String one(int pid, ProductInfoVo vo, Model model, HttpSession session){
        ProductInfo productInfo = productInfoService.getById(pid);
        model.addAttribute("prod", productInfo);

        session.setAttribute("prodVo", vo);
        return "update";
    }

    @RequestMapping("/update")
    public String update(ProductInfo info, Model model){

        if(!saveFileName.equals(""))
            info.setpImage(saveFileName);

        int num = -1;
        num = productInfoService.update(info);

        if (num > 0){
            model.addAttribute("msg", "更新成功");
        }else {
            model.addAttribute("msg", "更新失败");
        }

        saveFileName ="";
        return "forward:/prod/split.action";

    }

    @RequestMapping("/delete")
    public String delete(int pid){

        int num = productInfoService.delete(pid);

        return "forward:/prod/split.action";
    }

    @RequestMapping("/deleteBatch")
    public String deleteBatch(String pids){
        String[] ids = pids.split(",");
        int num = productInfoService.deleteBatch(ids);

        return "forward:/prod/split.action";

    }

    @ResponseBody
    @RequestMapping("/condition")
    public void condition(ProductInfoVo vo, HttpSession session){
        List<ProductInfo> productInfos = productInfoService.selectCondition(vo);

//        session.setAttribute("list", productInfos);

        PageInfo<ProductInfo> pageInfo = new PageInfo<>(productInfos);
        session.setAttribute("info", pageInfo);
    }

}
