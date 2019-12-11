package com.baizhi.cmfz.action;

import com.baizhi.cmfz.entity.Admin;
import com.baizhi.cmfz.service.AdminService;
import com.baizhi.cmfz.util.ImageCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    //注册
    @RequestMapping("/add")
    public String add(Admin admin)throws  Exception{
        adminService.insert(admin);
        return "login/login";
    }

    //获得验证码并存储在作用域中
    @RequestMapping("/getImageCode")
    public void getImageCode(HttpSession session, HttpServletResponse response)throws Exception{
        //获取随机字符串
        String securityCode = ImageCodeUtil.getSecurityCode();
        //将获得的字符串存储到作用域里
        session.setAttribute("code",securityCode);
        //获取图片
        BufferedImage image = ImageCodeUtil.createImage(securityCode);
        //获得流
        ServletOutputStream outputStream = response.getOutputStream();
        //打印流
        ImageIO.write(image,"png",outputStream);
    }


    //登录
    @RequestMapping("/login")
    @ResponseBody
    public HashMap<String,Object> login(String name, String password, HttpSession session,String enCode)throws Exception{
        //获取一个对象来存储结果
        HashMap<String, Object> map = new HashMap<>();
        //获得登录页面存储的验证码
        String code = (String)session.getAttribute("code");
        //判断输入的验证码是否一致
        if(code.equalsIgnoreCase(enCode)){
            //验证码通过就去获得用户
            Admin login = adminService.login(name, password);
            //判断用户名是否存在
            if(login!=null){
                map.put("success","200");
                map.put("message","登陆成功");
                session.setAttribute("logo",login);
            }else {
                map.put("success","400");
                map.put("message","密码或用户名不正确，请核对后再输入!");
            }
        }else {
            map.put("success","400");
            map.put("message","验证码错误");
        }
        return map;
    }

    //安全退出
    @RequestMapping("/exit")
    public String exit(HttpSession session)throws Exception{
        session.invalidate();
        return "redirect:/login/login.jsp";
    }

}
