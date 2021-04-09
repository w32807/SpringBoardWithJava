package com.zerock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zerock.domain.BoardVO;
import com.zerock.domain.Criteria;
import com.zerock.service.BoardService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/board/*")
@Log4j
public class BoardController {
    
    @Autowired
    private BoardService service;
    
    @GetMapping("/list")
    public void list(Criteria cri, Model model) {
        model.addAttribute("list", service.getList(cri));
    }
    
    @GetMapping("/register")
    public void register() {}
    
    @PostMapping("/register")
    public String register(BoardVO board, RedirectAttributes rttr) {
        
        log.info("register " + board);
        service.register(board);
        rttr.addFlashAttribute("result", board.getBno());
        return "redirect:/board/list";
    }
    
    @GetMapping({"/get", "modify"})
    public void get(@RequestParam("bno") Long bno, Model model) {
        log.info("get");
        model.addAttribute("board", service.get(bno));
    }
    
    @PostMapping("/modify")
    public String modify(BoardVO board, RedirectAttributes rttr) {
        log.info("modify " + board);
        if(service.modify(board)) {
            rttr.addFlashAttribute("result", "sucess");
        }
        return "redirect:/board/list";
    }
    
    @PostMapping("remove")
    public String remove(@RequestParam("bno") Long bno, RedirectAttributes rttr) {
        log.info("remove " + bno);
        if(service.remove(bno)) {
            rttr.addFlashAttribute("result", "sucess");
        }
        return "redirect:/board/list";
    }
}
