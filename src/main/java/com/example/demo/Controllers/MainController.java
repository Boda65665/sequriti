package com.example.demo.Controllers;

import com.example.demo.DTO.UserDTO;
import com.example.demo.JWT.GetDataJWT;
import com.example.demo.JWT.JWT;
import com.example.demo.Services.UserServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class MainController {
    JWT jwt = new JWT();
    GetDataJWT get_jwt = new GetDataJWT();
    GetDataJWT getDataJWT = new GetDataJWT();
    @Autowired
    UserServiceImplement userServiceImplement;

    @GetMapping("/")
    public String home(Model model,HttpServletRequest request, HttpServletResponse response){


        String email =getDataJWT.getEmail(request,response);



        return (email.equals("None")) ? "redirect:/reg" : "main/home";
    }
    @GetMapping("/reg")
    public String reg_get(Model model){
        UserDTO userDTO = new UserDTO();
        model.addAttribute("user_dto",userDTO);
        return "main/reg";
    }
    @PostMapping("/reg")
    public String reg_post(Model model,@ModelAttribute("user_dto") @Valid UserDTO userDTO,
                           BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return "main/reg";
        }
        String email = userDTO.getEmail();
        String password = userDTO.getPassword();

        try {
            userServiceImplement.findByEmail(email);
            model.addAttribute("user","Пользователь уже зарегестрирован!");
            return "main/reg";
        } catch (NullPointerException err) {

            userServiceImplement.saveUser(userDTO);
            return "redirect:/login";
        }

    }


}