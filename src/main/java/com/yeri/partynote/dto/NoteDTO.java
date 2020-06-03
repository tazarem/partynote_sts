package com.yeri.partynote.dto;

import lombok.Data;

@Data
public class NoteDTO {

	private String noteCode,noteTitle,noteDes,noteColor,priv;//아 얘는 날짜 가져와야하는데!
	/*0: 공개
	 *1: 친구공개
	 *2: 비공개 */
	
}
