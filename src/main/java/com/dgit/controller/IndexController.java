package com.dgit.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dgit.domain.MemberVO;
import com.dgit.service.MemeberService;
import com.mysql.fabric.xmlrpc.base.Member;

@Controller
public class IndexController {
	
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	@Autowired
	MemeberService memSerivce;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model,HttpSession session) {
		logger.info("[index] 메인 접속 ----------------------");
		
		MemberVO loginVo = (MemberVO) session.getAttribute("login");
		if(loginVo!=null){
			logger.info("로그인 아이디 : " + loginVo.getUserId());
		}  

		return "index";
	}  
	

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginGet(MemberVO vo, Model model) { 
		logger.info("[login:GET] --------------------------");
		
		return "login";    
	}  
	
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String joinGet(MemberVO vo, Model model) { 
		logger.info("[join:GET] --------------------------");
		
		return "join";    
	}  
	
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String joinPost(MemberVO vo, Model model) throws Exception { 
		logger.info("[join:POST] --------------------------");
		
		memSerivce.insertMember(vo);
		
		return "login";    
	}  
	
	@RequestMapping(value = "/loginPost", method = RequestMethod.POST)
	public void loginPost(MemberVO vo, Model model) { 
		logger.info("[login:POST] --------------------------");
		try {
			MemberVO readVo = memSerivce.readWithPw(vo);
			model.addAttribute("loginVo",vo);
			logger.info("[login]" + readVo.toString());
		} catch (Exception e) {
			logger.info("[login] 실패");
			e.printStackTrace(); 
		}
	}  
	
	@RequestMapping(value = "/checkId", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> checkId(String userId){
		ResponseEntity<String> entity = null;
		try {
			MemberVO vo =  memSerivce.readMember(userId);
			if(vo!=null){
				entity = new ResponseEntity<String>("exist", HttpStatus.OK);
			}else{
				entity = new ResponseEntity<String>("notexist",HttpStatus.OK);	
			} 
		} catch (Exception e) {
			e.printStackTrace();	
			entity = new ResponseEntity<String>("fail",HttpStatus.BAD_REQUEST);	
		}

		return entity;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET) 
	public String logoutGet(String logout,HttpSession session) throws Exception {
		logger.info("[logoutGet] ---------------- ");		
		session.invalidate();
		
		return "redirect:/";
	}
}
