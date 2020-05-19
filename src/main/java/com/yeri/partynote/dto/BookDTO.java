package com.yeri.partynote.dto;

import java.util.List;

import lombok.Data;

@Data
public class BookDTO {

	private String bookCode,bookTitle,bookPage;
	private List<BookPageDTO> posts; //	private PostDTO fp,sp;
}
