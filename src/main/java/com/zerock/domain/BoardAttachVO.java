package com.zerock.domain;

import lombok.Data;

@Data
public class BoardAttachVO {
	private String uuid; // 파일 고유번호
	private String uploadPath;// 파일 경로
	private String fileName;// 파일명
	private boolean fileType;// 파일 타입 (Image = true)
	private Long bno;// 글번호
}
