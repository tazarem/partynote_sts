package com.yeri.partynote.dto;

import lombok.Data;

@Data
public class FriendDTO {
	
	private String userId,friendId,recentDate;
	private int friendEach,read;

}
