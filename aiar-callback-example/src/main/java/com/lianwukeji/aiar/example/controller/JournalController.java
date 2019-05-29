package com.lianwukeji.aiar.example.controller;

import com.lianwukeji.aiar.example.repository.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public class JournalController {
    @Autowired
    JournalRepository repo;

    @RequestMapping("/journal")
    public String index(Model model){
        model.addAttribute("journal", repo.findAll());
        return "index";
    }
}
