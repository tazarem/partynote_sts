package com.yeri.partynote.controller;



import com.yeri.partynote.dto.MemberDTO;
import com.yeri.partynote.dto.NoteDTO;
import com.yeri.partynote.service.MainService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins="http://localhost:4040") //vue 가 돌아가는 서버의 크로스 오리진 허용
@RestController
public class MainController {
	
	@Autowired 
	MainService ms;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		
		return "home";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public MemberDTO login(@RequestBody MemberDTO member) {
		System.out.println("로그인 시도 : "+member.getUserId()+member.getUserPw());
		//서비스로 갖고들어가서 아이디와 패스워드 분리 
		//세션설정 해주고
		MemberDTO loginUser = ms.login(member);

	return loginUser;}
	
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(@RequestBody MemberDTO newMember) {
		System.out.println("회원가입 시도 : "+newMember);
		String result = ms.join(newMember);

	return result;}
	
	@RequestMapping(value = "/idCheck", method = RequestMethod.POST)
	public boolean existedId(@RequestBody String userId) {
//		System.out.println("중복확인 : "+userId);
		boolean result = ms.existedId(userId);//true면 사용할 수 있음
	return result;}
	
	
}
