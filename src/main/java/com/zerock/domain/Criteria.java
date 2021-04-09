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
	// Criteria�� �˻��� �����̶�� ��
    private int pageNum; // ������ ��ȣ
    private int amount;// 1�������� ��� �����͸� ������ ����
    
    public Criteria() {
    	this(1, 10);
	}
    
    public Criteria(int pageNum, int amount) {
    	this.pageNum = pageNum;
    	this.amount = amount;
	}
}
