package com.neucloud.controller;

import com.neucloud.dao.UserRepository;
import com.neucloud.dao.WindDataRepository;
import com.neucloud.dao.WindResultRepository;
import com.neucloud.entity.User;
import com.neucloud.entity.WindData;
import com.neucloud.entity.WindResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by neucloud on 2017/6/26.
 */
@Controller
public class IndexController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private WindResultRepository resultRepository;

    @Autowired
    private WindDataRepository windDataRepository;

    @GetMapping("/users")
    public Object users(Model model) {
        //查询所有的用户
        List<User> users = repository.findAll();
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping("/users")
    public String add(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String age = request.getParameter("age");
        int iage = 0;
        if (null != age && !age.equals("")) {
            iage = Integer.parseInt(age);
        }
        //保存数据到mongodb中
        repository.save(new User(name, iage));
        return "redirect:/users";
    }

    @GetMapping("/")
    public String chart() {
        return "simpleChart";
    }

    @GetMapping("/windResultJson")
    @ResponseBody
    public List<WindResult> getWindResult() {
        Sort sort = new Sort(Sort.Direction.ASC, "createTime");
        return resultRepository.findAll(sort);
    }

    @GetMapping("/windDataJson")
    @ResponseBody
    public Map<String, Object> getWindDatat() {
        Sort sort = new Sort(Sort.Direction.ASC, "createTime");
        List<WindResult> windResults = resultRepository.findAll(sort);

        List<WindData> windData = windDataRepository.findAll();
        List<WindData> windDataNomal = new ArrayList<>();
        List<WindData> windDataException = new ArrayList<>();
        int size = windResults.size();
        for (WindData d : windData) {
            float speed = d.getWindSpeed();
            int i = size - 1;
            for (; i >= 0; i--) {
                if (speed >= windResults.get(i).getWindSpeed()) {
                    break;
                }
            }
            if (i == size - 1 || i < 0) {
                windDataException.add(d);
            } else {
                WindResult result1 = windResults.get(i);
                WindResult result2 = windResults.get(i + 1);
                float down = getComputeValue(result1.getWindSpeed(), result1.getLineDown(),
                        result2.getWindSpeed(), result2.getLineDown(), speed);
                if (d.getPowerValid() < down) windDataException.add(d);
                else {
                    float up = getComputeValue(result1.getWindSpeed(), result1.getLineUp(),
                            result2.getWindSpeed(), result2.getLineUp(), speed);
                    if (d.getPowerValid() > up) windDataException.add(d);
                    else windDataNomal.add(d);
                }
            }
        }
        Map<String, Object> retData = new HashMap<>();
        retData.put("resultData", windResults);
        retData.put("nomalData", windDataNomal);
        retData.put("exceptionData", windDataException);
        return retData;
    }

    private float getComputeValue(float x1, float y1, float x2, float y2, float x3) {
        return (y2 - y1) / (x2 - x1) * (x3-x1) + y1;
    }
}
