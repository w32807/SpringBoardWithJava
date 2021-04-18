package com.zerock.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class FuctionTests {

	@Test
	public void probeContentTypeTest() throws Exception{
		Path source = Paths.get("C:\\upload\\test.txt");
		Path source2 = Paths.get("C:\\upload\\attach.png");
		assertFalse(Files.probeContentType(source).startsWith("image"));
		assertTrue(Files.probeContentType(source2).startsWith("image"));
	}
}
