package com.yeri.partynote.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.yeri.partynote.db.MainDatabase;
import com.yeri.partynote.dto.EditIdsDTO;
import com.yeri.partynote.dto.FriendDTO;
import com.yeri.partynote.dto.MemberDTO;
import com.yeri.partynote.utils.EmailServiceImpl;

@Service
public class MainService {

	@Autowired
	MainDatabase db;
	
	@Autowired
	EmailServiceImpl email; //이메일 인증할때 쓰기
	
	@Autowired
	BCryptPasswordEncoder bcrypt;
	

	public MemberDTO login(MemberDTO member) { //로그인
//		.matches(rawPassword,
		String rawPw=member.getUserPw();
		MemberDTO loginUser = db.login(member);
		String encodedPw = loginUser.getUserPw();
		MemberDTO logined=null;
		if(bcrypt.matches(rawPw,encodedPw)) {
			System.out.println("비밀번호 확인.");
			logined=loginUser;
		}

	return logined;}
	
	public String join(MemberDTO newMember) { //회원가입
		String result = "회원가입 시도";
		
		//비밀번호 암호화하기
		String encodedPw=bcrypt.encode(newMember.getUserPw());
		
		newMember.setUserPw(encodedPw);
		
		int answer = db.join(newMember);
		if(answer==1) {
			result = "회원가입 되었습니다.";
			System.out.println(result);
		}
		return result;
	}

	public boolean existedId(String userId) { //계정 중복확인
//		selectOne 이니까 있는지 없는지 판별
		boolean result=false;
		String isExisted = db.existedId(userId);
		if(isExisted==null) {
//			써도 되는 아이디
			System.out.println("사용가능한 유저 아이디 :"+userId);
			result= true;
		}
		return result;
	}

	public MemberDTO bringProfile(String userId) {
		MemberDTO member = db.bringProfile(userId);
		return member;
	}
	
	public int checkPw(MemberDTO idAndPw) {
		String rawPassword = idAndPw.getUserPw();
		String encodedPassword=db.login(idAndPw).getUserPw();
		if(bcrypt.matches(rawPassword, encodedPassword)) {
			return 1;
		}else {
			return 0;
		}
	}
	public int editProfile(MemberDTO editedData, MemberDTO protoData) {
		int result=0;
			if(editedData.getUserId()!=protoData.getUserId()) { //아이디가 변경됨
				EditIdsDTO ids = new EditIdsDTO();
				ids.setNewId(editedData.getUserId());
				ids.setOldId(protoData.getUserId());
			result += db.editUserId(ids);
			}
			//기본 편집
			result += db.editProfileDefault(editedData);
			if(editedData.getUserPw().length()>0) { //pw가 변경됨
				String encodedPw = bcrypt.encode(editedData.getUserPw());
				editedData.setUserPw(encodedPw);
				//암호화해서 editedData에 pw 세팅
				result += db.editUserPw(editedData);
			}

			
		return result;
	}

	public int makeFriends(FriendDTO fr) {
		int i = db.makeFriends(fr);
		return i;
	}

	public int isYourFriends(FriendDTO fr) {
			FriendDTO result = db.isYourFriends(fr);
			
			int answer=3; //친구요청이 불가능한 상태
			
			if(result!=null) {
				int j = result.getFriendEach();
				switch(j) {
				case 1:{ //서로 친구인 상태
					answer=1;
				break;}
				case 0:{ //요청중인 상태
					answer=0;
				break;}
				
				}
			}else { //insert 열이 없음.
				answer=2;//친구요청이 가능한 상태
			}
		
		return answer;
	}

	public int bringNewFriendsReq(String userId) {
		int result = db.bringNewFriendsReq(userId);
		return result;
	}

	public List<FriendDTO> bringNewFriendsDet(String userId) {
		List<FriendDTO> result = db.bringNewFriendsDet(userId);
		return result;
	}
	
	
	
	












}
