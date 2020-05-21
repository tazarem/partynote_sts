package com.yeri.partynote.dto;

import lombok.Data;

@Data
public class PostDTO {
	
	private String postCode,
	postTitle,
	postSubtitle,
	postContents,
	postColor,
	bookCode;
	private boolean usable,booked;
	private int postIndex, pageIndex;
	
}
