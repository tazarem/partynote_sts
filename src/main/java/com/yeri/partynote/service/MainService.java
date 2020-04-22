package com.yeri.partynote.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yeri.partynote.db.MainDatabase;
import com.yeri.partynote.dto.MemberDTO;
import com.yeri.partynote.dto.NoteDTO;
import com.yeri.partynote.dto.PostDTO;

@Service
public class MainService {

	@Autowired
	MainDatabase db;
	

	public MemberDTO  login(MemberDTO member) { //로그인
		MemberDTO loginUser = db.login(member);
	return loginUser;}
	
	public String join(MemberDTO newMember) { //회원가입
		String result = "회원가입 시도";
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
			result= true;
		}
		return result;
	}
	
	//노트 및 포스트
	public List<NoteDTO> bringNote(String userId) {// 노트 가져오기
		List<NoteDTO> notes =db.bringNote(userId);
		return notes;
	}
	
	//노트 생성하기 
	public boolean makeNote(NoteDTO newNote, String userId) {
		// 아이디로 검색해서 비활성화된 노트가 없다면 기존 노트 리스트를 불러온다.

		NoteDTO disabledNote = db.bringDisbaledNote(userId);
		String resultCode=null;
		boolean answer=false;
		if(disabledNote==null) {
			//없다면 새로 만드세요
			List<NoteDTO> noteList = db.countNote(userId);		
				if(noteList.size()==0) {//노트 첫 생성이라면
				System.out.println("노트 첫 생성");
				resultCode=userId+"_000";
				
				}else { //아니라면 최신 번호에 1을 더해서 번호 생성
				int key = (noteList.size()-1);
				String lastCode = (noteList.get(key)).getNoteCode();
				String codeNumb = lastCode.substring(userId.length()+1);
				System.out.println("마지막 노트 번호 : "+ codeNumb);
				resultCode = noteCodemaker(codeNumb, userId);//length만큼 제거를 해서 userId_까지 빠진 상태.
				}
			
			newNote.setNoteCode(resultCode);
			int result = db.makeNote(newNote);
			
			if(result==1) {
				System.out.println("노트 생성 성공");
				answer=true;
			}
			
		}else {//비활성화 된 노트가 있다면
			newNote.setNoteCode(disabledNote.getNoteCode());
			
			//데이터를 가져가고 활성화를 트루로 업데이트 
			int result =db.replaceNewNote(newNote);
			
			if(result==1) {
				answer=true;
			}
		}
				
	return answer;}


	//코드 메이커
	private String noteCodemaker(String codeNumb, String append) {
		int number = Integer.parseInt(codeNumb)+1;
		//append _
		String resultCode="_";
		if(number<10) {
			//한자리수일 때 앞에 00을 붙이고
			resultCode=append+"_00"+number;
		}
		else if(number<100) {
			//두자릿수일 때 0 을 붙이고
			resultCode=append+"_0"+number;
		}
		else if(number<1000) {
			//세자릿수일 때 
			resultCode=append+"_"+number;
		}
		return resultCode;
	}
	
	public boolean editNote(NoteDTO note) {
			boolean answer=false;
		int result = db.replaceNewNote(note);
		if(result==1){
			answer = true;
		}
		
		return answer;
	}
	public int deleteNote(NoteDTO note) {
		
		int result=0;
		result = db.deleteNote(note);
		result = db.allDeletePost(note);
		
	return result;}

	public List<PostDTO> bringPost(String noteCode) {
				
		List<PostDTO> posts = db.bringPost(noteCode);
			
		return posts;
	}

	public boolean makePost(PostDTO newPost, String appendCode) {
		boolean answer=false;
		String resultCode=null;
		//새 포스트를 만듭니다
		//기존에 안쓰던 포스트가 있다면 가져오기 
		PostDTO disabledPost = db.bringDisabledPost(appendCode);
		
		if(disabledPost==null) {//비활성화 된 게 없다
			//기존 것으로 새로 만들기.
			List<PostDTO> posts = db.bringPost(appendCode);
			if(posts.size()==0) {// 기존에 만들어둔것도 없다면
				resultCode=appendCode+"_0000";
				System.out.println(resultCode);
			}else {
				//코드메이커로 돌리기
				int key = (posts.size()-1);
				String lastCode = (posts.get(key)).getPostCode();
				String codeNumb = lastCode.substring(appendCode.length()+1); //수만 추출 완료
				resultCode = postCodeMaker(codeNumb,appendCode);
				}
				newPost.setPostCode(resultCode);
				int i =db.makePost(newPost);
				if(i==1) {
					System.out.println("포스트 생성 : "+resultCode);
					answer=true;
				}
			
		}else {
			//업데이트 치기
		 newPost.setPostCode(disabledPost.getPostCode());
			int i =db.replaceNewPost(newPost);
			if(i==1) {
				System.out.println("포스트 생성 : "+newPost.getPostCode());
				answer=true;
			}
		}
		//코드메이커 만들기 
		//북 코드메이커 만들기
//		boolean answer = db.makePost()
		
		return answer;
	}
	private String postCodeMaker(String codeNumb, String appendCode) {
		String resultCode="_"; //초기값 설정
		int number=Integer.parseInt(codeNumb)+1;
		
		if(number<10) {
			resultCode = appendCode+"_000"+number;
		}
		else if(number<100) {
			resultCode = appendCode+"_00"+number;
		}
		else if(number<1000) {
			resultCode = appendCode+"_0"+number;
		}
		else if(number<10000) {
			resultCode = appendCode+"_"+number;
		}
		
		
		return resultCode;
	}

	public int deletePost(PostDTO post) {

		int answer = db.deletePost(post);
		
		return answer;
	}

	public int editPost(PostDTO post) {
		
		int answer = db.editPost(post);
		
		return answer;
	}




}
