package com.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zerock.domain.BoardAttachVO;
import com.zerock.domain.BoardVO;
import com.zerock.domain.Criteria;
import com.zerock.mapper.BoardAttachMapper;
import com.zerock.mapper.BoardMapper;
import com.zerock.utils.CommonUtils;

import lombok.extern.log4j.Log4j;

@Log4j
@Service
@Repository
public class BoardServiewImpl implements BoardService{
    
    @Autowired
    private BoardMapper mapper;

    @Autowired
    private BoardAttachMapper attachMapper;
    
    @Override
    @Transactional
    public void register(BoardVO board) {
        mapper.insertSelectKey(board);
        
        if(CommonUtils.isNull(board.getAttachList())) return;
        
        board.getAttachList().forEach(attach -> {
        	attach.setBno(board.getBno());
        	attachMapper.insert(attach);
        });
    }

    @Override
    public BoardVO get(Long bno) {
        return mapper.read(bno);
    }

    @Override
    @Transactional
    public boolean modify(BoardVO board) {
    	// delete 후 insert 처리
    	boolean modifyResult = mapper.update(board) == 1;
    	if(modifyResult && !CommonUtils.isNull(board.getAttachList())) {
    		attachMapper.deleteAll(board.getBno());
    		board.getAttachList().forEach(attach -> {
    			attach.setBno(board.getBno());
    			attachMapper.insert(attach);
    		});
    	}
        return modifyResult;
    }

    @Override
    @Transactional
    public boolean remove(Long bno) {
    	attachMapper.deleteAll(bno);
        return mapper.delete(bno) == 1;
    }

    @Override
    public List<BoardVO> getList(Criteria cri) {
        return mapper.getListWithPaging(cri);
    }

	@Override
	public int getTotal(Criteria cri) {
		return mapper.getTotalCount(cri);
	}
	
	@Override
	public List<BoardAttachVO> getAttachList(Long bno) {
		return attachMapper.findByBno(bno);
	}
}
