package com.yeri.partynote.dto;

import lombok.Data;

@Data
public class PostDTO {
	
	private String postCode,
	postTitle,
	postSubtitle,
	postContents,
	postColor;
	private int postIndex;
	
}
