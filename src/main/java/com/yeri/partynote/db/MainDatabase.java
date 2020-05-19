package com.yeri.partynote.db;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yeri.partynote.dto.BookDTO;
import com.yeri.partynote.dto.BookPageDTO;
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
	
	public List<PostDTO> bringPost(String noteCode) {
		System.out.println("bringPost : "+noteCode);
		noteCode += '%';
		return sql.selectList("Note.bringPost", noteCode);
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
		return sql.delete("Note.deletePost",post);
	}

	public int editPost(PostDTO post) {

		return sql.update("Note.editPost",post);
	}
	public String selectUnusedBookCode(String noteCode) {
		noteCode += "%";
		return sql.selectOne("Note.selectUnusedBook",noteCode);
	}
	public int makeBook(BookDTO newBook) {

		return sql.insert("Note.makeBook", newBook);
	}

	public String selectLatestBookCode(String noteCode) {
		noteCode += "%";
		return sql.selectOne("Note.selectLatestBook",noteCode);
	}

	public int addBookPage(BookPageDTO addPage) {
		
	return sql.insert("Note.addBookPage", addPage);}

	public int updatePostBooked(String postCode) {
		
	return sql.update("Note.updatePostBooked", postCode);}

	public int addCountWholeBookPage(String bookCode) {
		
	return sql.update("Note.addCountWholeBookPage",bookCode);}




	
	
}
