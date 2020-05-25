package com.yeri.partynote.dto;

import java.util.List;

import lombok.Data;

@Data
public class SearchDTO {

	private List<MemberDTO> members;
	private List<BookDTO> books;
	private List<PostDTO> posts;
	private List<NoteDTO> notes;
	
}
