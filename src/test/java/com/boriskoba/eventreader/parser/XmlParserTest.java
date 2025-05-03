package com.boriskoba.eventreader.parser;

import com.boriskoba.eventreader.dto.RootDto;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class XmlParserTest {
	private XmlParser xmlParser;

	@BeforeEach
	void setUp() {
		xmlParser = new XmlParser();
	}

	@Test
	void testParse_ValidXmlFile_ReturnsRootDto() throws Exception {
		Path xmlFilePath = Paths.get(getClass().getClassLoader().getResource("test-xml-content.xml").toURI());
		RootDto rootDto = xmlParser.parse(xmlFilePath);
		assertNotNull(rootDto);
		assertEquals("123", rootDto.getRequestDetails().getId());
	}
}
