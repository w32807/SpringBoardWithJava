package com.zerock.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import testConfig.ApplicationContextTest;

public class CommonUtilsTests extends ApplicationContextTest{

	@Test
	public void isListNullTest() {
		List<String> list = new ArrayList<String>();
		System.out.println(CommonUtils.isNull(list));
		assertTrue(CommonUtils.isNull(list));
		list.add("tests");
		assertFalse(CommonUtils.isNull(list));
	}
}
