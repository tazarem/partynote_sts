package com.yeri.partynote.controller;



import com.yeri.partynote.dto.MemberDTO;
import com.yeri.partynote.dto.NoteDTO;
import com.yeri.partynote.dto.PostDTO;
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
public class NoteController {
	
	@Autowired 
	MainService ms;
	
	@RequestMapping(value = "/bringNoteIndex", method = RequestMethod.GET)
	public List<NoteDTO> bringNoteIndex(String userId) {
		//노트 목록을 불러옵니다.
		System.out.println(userId);
		List<NoteDTO> notes = ms.bringNote(userId);
		
	return notes;}
	
	@RequestMapping(value = "/makeNote", method = RequestMethod.POST)
	public boolean makeNote(@RequestBody NoteDTO newNote) {
//		새로운 노트를 생성합니다
	    System.out.println(newNote);
	    String userId = newNote.getNoteCode();//노트코드안에 임시로 저장된 아이디 가져옴
		boolean answer = ms.makeNote(newNote, userId);
		
	return answer;}
	
	@RequestMapping(value = "/editNote", method = RequestMethod.POST)
	public boolean editNote(@RequestBody NoteDTO note) {
//		노트의 내용을 편집합니다.
	    System.out.println(note);
		boolean answer = ms.editNote(note);
		
	return answer;}
	
	@RequestMapping(value = "/deleteNote", method = RequestMethod.POST)
	public int deleteNote(@RequestBody NoteDTO note) {
//		노트를 삭제합니다.
		int answer = ms.deleteNote(note);
		
	return answer;}
	
	@RequestMapping(value = "/bringPost", method = RequestMethod.POST)
	public List<PostDTO> bringPost(@RequestBody String noteCode) {
		
		noteCode = noteCode.substring(0,noteCode.length()-1);//=이 붙네 ㅡㅡ;;
		System.out.println("노트코드 : "+noteCode);
		List<PostDTO> posts = ms.bringPost(noteCode);
	return posts;}

	@RequestMapping(value = "/makePost", method = RequestMethod.POST)
	public boolean makePost(@RequestBody PostDTO newPost) {
		String appendCode = newPost.getPostCode();
		System.out.println(newPost);
		boolean answer = ms.makePost(newPost,appendCode);
	
	return answer;}
	
	@RequestMapping(value = "/deletePost", method = RequestMethod.POST)
	public int deletePost(@RequestBody PostDTO post) {
		System.out.println("포스트 삭제 : " + post.getPostCode());
		int answer = ms.deletePost(post);
	return answer;}
	
	@RequestMapping(value = "/editPost", method = RequestMethod.POST)
	public int editPost(@RequestBody PostDTO post) {
		System.out.println("포스트 수정 : " + post);
		int answer = ms.editPost(post);
	return answer;}
}
