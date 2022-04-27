//package com.example.demo.Controllers;
//
//import com.example.demo.DTO.UserDTO;
//import com.example.demo.JWT.GetDataJWT;
//import com.example.demo.JWT.JWT;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.validation.Valid;
//
//@Controller
//public class MainController {
//    JWT jwt = new JWT();
//    GetDataJWT get_jwt = new GetDataJWT();
//    GetDataJWT getDataJWT = new GetDataJWT();
//    @Autowired
//    UserServiceImplement userServiceImplement;
//
//    @GetMapping("/")
//    public String home(Model model,HttpServletRequest request, HttpServletResponse response){
//
//
//        String email =getDataJWT.getEmail(request,response);
//
//
//
//        return (email.equals("None")) ? "redirect:/reg" : "main/home";
//    }
//    @GetMapping("/reg")
//    public String reg_get(Model model){
//        UserDTO userDTO = new UserDTO();
//        model.addAttribute("user_dto",userDTO);
//        return "main/reg";
//    }
//    @PostMapping("/reg")
//    public String reg_post(Model model,@ModelAttribute("user_dto") @Valid UserDTO userDTO,
//                           BindingResult bindingResult){
//
//        if (bindingResult.hasErrors()) {
//            return "main/reg";
//        }
//        String email = userDTO.getEmail();
//        String password = userDTO.getPassword();
//
//        try {
//            userServiceImplement.findByEmail(email);
//            model.addAttribute("user","Пользователь уже зарегестрирован!");
//            return "main/reg";
//        } catch (NullPointerException err) {
//
//            userServiceImplement.saveUser(userDTO);
//            return "redirect:/login";
//        }
//
//    }
//    @GetMapping("/login")
//    public String get_login(Model model,HttpServletRequest request,HttpServletResponse response){
//        String user_email = get_jwt.getEmail(request,response);
//        if (!user_email.equals("None")){
//            System.out.println(user_email);
//            return "redirect:/";
//        }
//        UserDTO user_dto = new UserDTO();
//        model.addAttribute("user_dto",user_dto);
//        return "main/login";
//    }
//    @PostMapping("/login")
//    public String post_login(HttpServletRequest request,HttpServletResponse response,Model model,@ModelAttribute("user_dto") @Valid UserDTO userDTO,
//                             BindingResult bindingResult){
//        if (bindingResult.hasErrors()) {
//            return "main/login";
//
//        }
//
//        String email = userDTO.getEmail();
//        String password = userDTO.getPassword();
//        try {
//            UserDTO user =userServiceImplement.findByEmail(email);
//            if (user.getPassword().equals(password)) {
//                String token = jwt.jwt_create(email);
//                Cookie cookie = new Cookie("JWT", token);
//                cookie.setMaxAge(3600);
//                response.addCookie(cookie);
//                return "redirect:/home";
//            }
//            else {
//                model.addAttribute("user","Неверный логин или пароль");
//            }
//
//        } catch (NullPointerException err) {
//
//            model.addAttribute("user","Не верный логин или пароль!");
//            return "main/login";
//        }
//
//        return "main/login";
//    }
//}