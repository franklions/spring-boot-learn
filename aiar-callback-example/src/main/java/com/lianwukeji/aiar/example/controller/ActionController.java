package com.lianwukeji.aiar.example.controller;

import com.lianwukeji.aiar.example.repository.ActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-05-23
 * @since Jdk 1.8
 */
@Controller
public class ActionController {
    @Autowired
    ActionRepository repo;

    @RequestMapping("/")
    public String index(Model model){
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(0, 20, sort);
        model.addAttribute("actions", repo.findAll(pageable));
        return "action";
    }
}
