package com.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.pojos.Food;
import com.app.pojos.User;
import com.app.service.IFoodService;
import com.app.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IFoodService foodService;

	public UserController() {
		System.out.println("in ctor of " + getClass());
	}
	
	@PostMapping("/register")
	public String registerUser(User u) {
		userService.addNewUser(u);
		return "/index";
	}
	@PostMapping("/addFood")
	public String addFood(Food f) {
		foodService.addNewFood(f);
		return "/index";
	}
	
	@GetMapping("/welcome")
	public String showAllParticipant(HttpSession session,Model map,HttpServletResponse resp,HttpServletRequest request)
	{
		System.out.println("in participant");
		return "/user/welcome";
	}
	
	@GetMapping("/food")
	public String foodList(Model map) {
		map.addAttribute("foods",foodService.allFoods());
		return "/user/food";
	}
	@GetMapping("/Vegetables")
	public String VegetablesList(Model map) {
		map.addAttribute("foods",foodService.allFoods());
		return "/user/Vegetables";
	}
	@GetMapping("/Grocery")
	public String GroceryList(Model map) {
		map.addAttribute("foods",foodService.allFoods());
		return "/user/Grocery";
	}
	
	@GetMapping("/cart")
	public String showCart( @RequestParam("foods") int[] id,Model map ) {
		double total=0;
		List<Food> foods = new ArrayList<Food>();
		for (int i : id) {
			foods.add(foodService.food(i));
		}
		for (Food food : foods) {
			total = total+food.getPrize();
		}
		
		 map.addAttribute("order",foods); 
		 map.addAttribute("total",total);
		return "/user/cart";
	}
	
	@GetMapping("/signout")
	public String userLogout(HttpSession session,Model map,HttpServletResponse resp,HttpServletRequest request)
	{
		System.out.println("in log out");
		map.addAttribute("user_dtls", session.getAttribute("user_dtls"));
		session.invalidate();
		System.out.println("ctx path "+request.getContextPath());
		resp.setHeader("refresh", "5;url="+request.getContextPath());
		return "/user/logout";
	}

}
