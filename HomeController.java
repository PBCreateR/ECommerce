package com.app.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.pojos.Food;
import com.app.pojos.User;
import com.app.service.IUserService;

@Controller
public class HomeController {
	
	@Autowired
	private IUserService userService;
	 public HomeController() {
		System.out.println("in ctor of "+getClass());
	}
	 @GetMapping("/register")
	 public String showRegisterUser(User u)
	 {
		 return "/user/register";
	 }
	 
	 
	 @GetMapping("/")
	 public String showHomePage(Model map)
	 {
		 System.out.println("in home page");
		 map.addAttribute("ts", new Date());
		 return "/index";
	 }
	 @GetMapping("/login")
		public String showLoginForm() {
			System.out.println("in show login form");
			return "/user/login";
		}
	 
	 @PostMapping("/login")
		public String processLoginForm(@RequestParam String email, @RequestParam("pass") String pwd, 
				Model map,HttpSession session,RedirectAttributes flashMap)
		{
			System.out.println("in process login form " + email + " " + pwd + " " + map+" "+flashMap);
			try {
				User user = userService.authenticateUser(email, pwd);
				flashMap.addFlashAttribute("mesg",
						"Hello , " + user.getName());
				session.setAttribute("user_dtls", user);
				System.out.println(user);
				return "redirect:/user/welcome";
			}catch (Exception e) {
				map.addAttribute("mesg","invalid Credentials!!!!!");
				return "/user/login";
			}
		}
	 
	 @GetMapping("/addFood")
		public String showaddFoodForm(Food f) {
			
			return "/user/addFood";
		}
}
