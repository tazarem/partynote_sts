package com.yeri.partynote.controller;



import com.yeri.partynote.dto.FriendDTO;
import com.yeri.partynote.dto.MemberDTO;
import com.yeri.partynote.service.MainService;

import java.util.List;

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
		System.out.println("중복확인 : "+userId);
		boolean result = ms.existedId(userId.substring(0,userId.length()-1));//true면 사용할 수 있음
	return result;}
	
	@RequestMapping(value = "/emailCheck", method = RequestMethod.POST)
	public boolean emailCheck(@RequestBody String userEmail) { //가입하고 나면 작성한 이메일로 이메일 송부. 인증 과정을 거쳐야 완벽하게 가입됨.
		boolean result = false;
	return result;}
	
	@RequestMapping(value = "/bringProfile", method = RequestMethod.POST)
	public MemberDTO bringProfile(@RequestBody String userId) {
		MemberDTO member = ms.bringProfile(userId.substring(0,userId.length()-1));
	return member;}
	
	@RequestMapping(value = "/checkPw", method = RequestMethod.POST)
	public int checkPw(@RequestBody MemberDTO idAndPw) {
		int answer = ms.checkPw(idAndPw);
	return answer;}
	
	@RequestMapping(value = "/editProfile", method = RequestMethod.POST)
	public int editProfile(@RequestBody List<MemberDTO> editedDatas) {
			int answer = ms.editProfile(editedDatas.get(0),editedDatas.get(1));
	return answer;}
	
	@RequestMapping(value = "/makeFriends", method = RequestMethod.POST)
	public int makeFriends(@RequestBody FriendDTO fr) {
		System.out.println("친구요청 : "+fr);
		int answer = ms.makeFriends(fr);
	return answer;}
	
	@RequestMapping(value = "/isYourFriends", method = RequestMethod.POST)
	public int isYourFriends(@RequestBody FriendDTO fr) {
		System.out.println("친구인지 확인 : "+fr);
		int answer = ms.isYourFriends(fr);
	return answer;}
	
	@RequestMapping(value = "/bringNewFriendsReq", method = RequestMethod.POST)
	public int bringNewFriendsReq(@RequestBody String userId) {
		System.out.println("새로운 친구요청 확인 : "+userId);
		int result = ms.bringNewFriendsReq(userId.substring(0,userId.length()-1));
//		System.out.println(result);
	return result;}
	
	@RequestMapping(value = "/bringNewFriendsDet", method = RequestMethod.POST)
	public List<FriendDTO> bringNewFriendsDet(@RequestBody String userId) {
		System.out.println("친구요청 조회(읽은 것 포함) : "+userId);
		List<FriendDTO> result = ms.bringNewFriendsDet(userId.substring(0,userId.length()-1));
//		System.out.println(result);
	return result;}
}
