package com.zerock.domain;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
// Mybatis의 List<BoardVO> 일 때 생성자를 기준으로 가져오므로, 아래 두 가지 어노테이션 선언 
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardVO {
    private Long bno;	// PK 글 번호
    private String title;// 글 제목
    private String content;// 글 내용
    private String writer;// 작성자
    private Date regDate;// 등록시간
    private Date updateDate;// 등록일자
    private int replyCnt;// 댓글 수
    
    private List<BoardAttachVO> attachList; // 첨부파일 리스트
}
