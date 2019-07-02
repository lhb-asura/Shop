package com.lhb.sh.controller.auth;

import com.lhb.sh.controller.BaseController;
import com.lhb.sh.exception.user.UserException;
import com.lhb.sh.model.User;
import com.lhb.sh.service.auth.DbUserDetailsService;
import com.lhb.sh.service.user.UserService;
import com.lhb.sh.util.ResultUtil;
import com.lhb.sh.util.enums.AccountStaEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class AuthController extends BaseController {
    @Autowired
    DbUserDetailsService dbUserDetailsService;

    @RequestMapping("/login")
    public String loginPage(Model model) {
        log.info("you can go the loginPage");
        model.addAttribute("username","test user");
        return "login";
    }

    @GetMapping("/register")
    public String regPage() {
        log.info("you can go the regPage");
        return "register";
    }

    @PostMapping("/register")
    @ResponseBody
    public String register(User user, @RequestParam("confirm") String confirm) {
        try {
            dbUserDetailsService.register(user);
            return ResultUtil.getJson(AccountStaEnum.success.getCode(), AccountStaEnum.success.getInfo());
        } catch (UserException e) {
            return ResultUtil.getJson(e.getCode(), e.getMessage());
        }

    }
}
