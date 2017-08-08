package com.neucloud.controller;
import com.neucloud.dao.UserRepository;
import com.neucloud.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by neucloud on 2017/6/26.
 */
@Controller
public class IndexController {
    @Autowired
    private UserRepository repository;
    @GetMapping("/")
    public Object users( Model model){
		//查询所有的用户
        List<User> users = repository.findAll();
        model.addAttribute("users",users);
        return "users";
    }

    @PostMapping("/")
    public String add(HttpServletRequest request, HttpServletResponse response){
        String name = request.getParameter("name");
        String age = request.getParameter("age");
        int iage = 0;
        if(null != age && !age.equals("")){
            iage = Integer.parseInt(age);
        }
        //保存数据到mongodb中
        repository.save(new User(name, iage));
        return "redirect:/";
}
}
