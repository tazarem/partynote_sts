package com.yeri.partynote.dto;


import java.util.List;

import lombok.Data;

@Data
public class BookDTO {

	private String bookCode,bookTitle;
	int bookPage;
	private List<PostDTO> posts;
}
