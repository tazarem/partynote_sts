package com.yeri.partynote.db;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yeri.partynote.dto.BookDTO;
import com.yeri.partynote.dto.MemberDTO;
import com.yeri.partynote.dto.NoteDTO;
import com.yeri.partynote.dto.PostDTO;

@Repository
public class MainDatabase {

	@Autowired
	SqlSessionTemplate sql;

	public int join(MemberDTO newMember) {
		
		return sql.insert("Member.joinMember",newMember);
	}

	public MemberDTO login(MemberDTO member) {
		// TODO Auto-generated method stub
		System.out.println(member);
		return sql.selectOne("Member.loginMember", member);
	}

	public String existedId(String userId) {
		// TODO Auto-generated method stub
		return sql.selectOne("Member.existedId",userId);
	}
	
	public MemberDTO bringProfile(String userId) {
		// TODO Auto-generated method stub
		return sql.selectOne("Member.bringProfile",userId);
	}

	public List<NoteDTO> bringNote(String userId) {

		userId+="_%";
		
		return sql.selectList("Note.bringNote",userId);
	}

	public List<NoteDTO> countNote(String userId) {
		userId+="_%";
		return sql.selectList("Note.countNote", userId);
	}
	
	public NoteDTO bringDisbaledNote(String userId) {
		
		userId+='%';
		
		return sql.selectOne("Note.bringDisabledNote",userId);
	}

	public int makeNote(NoteDTO newNote) {

		return sql.insert("Note.makeNote", newNote);
	}

	public int replaceNewNote(NoteDTO newNote) {

		return sql.update("Note.replaceNote",newNote);
	}
	
	public int deleteNote(NoteDTO note) {

		return sql.update("Note.deleteNote",note);
	}
	public int allDeletePost(NoteDTO note) {
		note.setNoteCode((note.getNoteCode()+'%'));

		return sql.update("Note.allDeletePost",note);
	}
	
	public List<PostDTO> bringPost(String noteCode) {//프론트엔드 출력용 bringPost
		System.out.println("bringPost : "+noteCode);
		noteCode += '%';
		return sql.selectList("Note.bringPost", noteCode);
	}
	public List<PostDTO> bringPostToMakeNewPost(String noteCode) {//포스트 코드생성기로 코드 만들때 쓰는 bringPost
		System.out.println("bringPost : "+noteCode);
		noteCode += '%';
		return sql.selectList("Note.bringPostToMakeNewPost", noteCode);
	}
	public PostDTO bringDisabledPost(String appendCode) {
		appendCode += '%'; //노트 코드가 들어갑니다.
		
		return sql.selectOne("Note.bringDisabledPost",appendCode);
	}

	public int makePost(PostDTO newPost) {

		return sql.insert("Note.makePost", newPost);
	}

	public int replaceNewPost(PostDTO newPost) {

		return sql.update("Note.replaceNewPost",newPost);
	}

	public int deletePost(PostDTO post) {
		// sql.update("Note.disablePost",post)
		return sql.update("Note.disablePost",post);
	}

	public int editPost(PostDTO post) {

		return sql.update("Note.editPost",post);
	}
	
	public int updatePostIndex(List<PostDTO> posts) {
		return sql.update("Note.updatePostIndex", posts);
	}
	
	public String selectUnusedBookCode(String noteCode) {
		noteCode += "%";
		return sql.selectOne("Note.selectUnusedBook",noteCode);
	}
	public int makeBook(BookDTO newBook) {

		return sql.insert("Note.makeBook", newBook);
	}
	public int remakeBook(BookDTO newBook) {

		return sql.update("Note.remakeBook",newBook);
	}
	public String selectLatestBookCode(String noteCode) {
		noteCode += "%";
		return sql.selectOne("Note.selectLatestBook",noteCode);
	}

	public int updatePostBooked(PostDTO post) {//페이지 여부 및 페이지 상태를 업데이트
		
	return sql.update("Note.updatePostBooked", post);}

	public int addCountWholeBookPage(BookDTO book) {
		
	return sql.update("Note.addCountWholeBookPage",book);}

	public List<BookDTO> bringBooks(String noteCode) {

		noteCode +="%";
		
		return sql.selectList("Note.bringBooks", noteCode);
	}

	public List<PostDTO> bringBookPages(String bookCode) {

		return sql.selectList("Note.bringBookPages", bookCode);
	}

	public int bringBookIndex(String bookCode) {

		return sql.selectOne("Note.bringLatestBookIndex", bookCode);
	}

	public int disableBookedPost(String bookCode) {
		return sql.update("Note.disableBookedPost", bookCode);
	}

	public int disableBook(String bookCode) {
		return sql.update("Note.disableBook", bookCode);
	}

	public int releaseBookedPost(String bookCode) {

		return sql.update("Note.releaseBookedPost",bookCode);
	}

	public List<MemberDTO> searchUsers(String searchData) {

		return sql.selectList("Member.searchUsers",searchData);
	}

	public List<BookDTO> searchBooks(String searchData) {
		
		return sql.selectList("Note.searchBooks",searchData);
	}

	public List<NoteDTO> searchNotes(String searchData) {

		return sql.selectList("Note.searchNotes",searchData);
	}

	public List<PostDTO> searchPosts(String searchData) {

		return sql.selectList("Note.searchPosts",searchData);
	}














	
	
}
