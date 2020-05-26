package com.yeri.partynote.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yeri.partynote.db.MainDatabase;
import com.yeri.partynote.dto.BookDTO;
import com.yeri.partynote.dto.MemberDTO;
import com.yeri.partynote.dto.NoteDTO;
import com.yeri.partynote.dto.PostDTO;
import com.yeri.partynote.dto.SearchDTO;

@Service
public class NoteService {
	@Autowired
	MainDatabase db;
//	[[Note]]
	
	
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
			List<PostDTO> posts = db.bringPostToMakeNewPost(appendCode);
			System.out.println("새로 만듭니다");
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
				System.out.println(resultCode);
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
	public int updatePostIndex(List<PostDTO> posts) {

		int answer = db.updatePostIndex(posts);
		
		return answer;
	}
	public int makeBook(BookDTO newBook) {
		
		/* 1. DB에서 이 계정의 이 노트에서 생성된  book이 있는지 셀렉트.
		 * 2. 비활성화된 book 이 있다면 재활용, 없다면 새로 생성
		 * 3. 부여된 번호로 makeBook
		 * note => userId_000
		 * bookCode => userId_000_b_00 ([노트코드]_b_00 꼴)
		 * */

		List<PostDTO> basePosts = newBook.getPosts(); //리팩토링하기
		
		System.out.println("초기 포스트 "+basePosts);
		
		int answer = 0;
		int answer2 = 0;

		String postCode = basePosts.get(1).getPostCode();
		
		String noteCode = postCode.substring(0, postCode.length()-5);
		System.out.println(noteCode);
		String usableBookCode = db.selectUnusedBookCode(noteCode); //searching usable bookcode
		
		if(usableBookCode==null) { // 비활성화 된 게 없다면 그 다음 번호로 새로 생성
		String latestBookCode = db.selectLatestBookCode(noteCode);
		String bookCode=null;
			if(latestBookCode==null) {
				//00 만들고
				bookCode = noteCode+"_b_00";
				System.out.println("새 책 생성");

			}else {
				int bookCount=Integer.parseInt(latestBookCode.substring(postCode.length()-2,postCode.length()));
				int bookNumb=bookCount+1;
				if(bookNumb<10) {
					bookCode = noteCode+"_b_0"+bookNumb;
				}else if(bookNumb<100){
					bookCode = noteCode+"_b_"+bookNumb;
				}else {
					System.out.println("더 이상 book을 생성할 수 없습니다.");
				}
			}
			
			if(bookCode!=null) {
			newBook.setBookCode(bookCode);
			answer = db.makeBook(newBook);//책 생성
			System.out.println("책 생성 완료 : "+answer);
			}
			
		}else { // 아니라면 재활용
			System.out.println("북코드 재활용");
			newBook.setBookCode(usableBookCode);
			answer = db.remakeBook(newBook);
		}
		if(answer!=0) { //겹친 두 개의 포스트로 책 기본 페이지 생성하기
			System.out.println("책 기본 페이지 생성..");
			int i=0;
			for(PostDTO item : basePosts) {
				System.out.println("item : "+item);
				item.setBookCode(newBook.getBookCode());
				item.setPageIndex(i);
				addBookPage(item);
				i++;
			}
			
			answer2= 1;
		}
		
	return answer2;}
	
	public int addBookPageAndIndex(PostDTO post) {
		int bIndex = db.bringBookIndex(post.getBookCode()); //0부터 시작하니까 마지막 index는 페이지 -1된 값임
		bIndex+=2;
		post.setPageIndex(bIndex);
		BookDTO book = new BookDTO();
		book.setBookCode(post.getBookCode());
		book.setBookPage(bIndex);
		int i = addCountWholeBookPage(book);
		System.out.println("updateBookPage : "+i);
		int answer = addBookPage(post);
		return answer;
	}

	public int addBookPage(PostDTO addPage){
		int answer=0;
		db.updatePostBooked(addPage);
	return answer;}
	
	public int addCountWholeBookPage(BookDTO book) {//페이지 총수 업데이트
		int answer=0;
		db.addCountWholeBookPage(book);
	return answer;}

	public List<BookDTO> bringBooks(String noteCode) {
		List<BookDTO> books = db.bringBooks(noteCode);
		for(BookDTO book : books) {
			String bookCode = book.getBookCode();
			book.setPosts(db.bringBookPages(bookCode));
			System.out.println("책 페이지 가져오기 : "+book.getPosts());
		}
		return books;
	}

	public int deleteBook(BookDTO book) {
			String bookCode = book.getBookCode();
			System.out.println("delete bookCode : "+bookCode);
			int result =db.disableBookedPost(bookCode);
			int result2 = db.disableBook(bookCode);
			
		return 0;
	}

	public int releaseBook(BookDTO book) {
				String bookCode = book.getBookCode();
				System.out.println("release this : " +bookCode);
				int result = db.releaseBookedPost(bookCode);
				int result2 = db.disableBook(bookCode);
		return 0;
	}

	public SearchDTO generalSearch(String searchData) {
		searchData ="%"+searchData+"%";
		SearchDTO result = new SearchDTO();
		//1. 유저 검색
		List<MemberDTO> members = db.searchUsers(searchData);
		result.setMembers(members);
		//2. book/note/post 검색
		List<BookDTO> books = db.searchBooks(searchData);
		result.setBooks(books);
		List<NoteDTO> notes = db.searchNotes(searchData);
		result.setNotes(notes);
		List<PostDTO> posts = db.searchPosts(searchData);
		result.setPosts(posts);
		
		return result;
	}


}
