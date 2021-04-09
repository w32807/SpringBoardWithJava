package com.zerock.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {
	// Criteria는 검색의 기준이라는 뜻
    private int pageNum; // 페이지 번호
    private int amount;// 1페이지에 몇개의 데이터를 보여줄 건지
    
    public Criteria() {
    	this(1, 10);
	}
    
    public Criteria(int pageNum, int amount) {
    	this.pageNum = pageNum;
    	this.amount = amount;
	}
}
