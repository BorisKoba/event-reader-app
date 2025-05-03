package com.boriskoba.eventreader.parser;

import com.boriskoba.eventreader.dto.RootDto;
import org.junit.jupiter.api.Test;
import jakarta.xml.bind.JAXBException;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class XmlParserTest {

    private final XmlParser xmlParser = new XmlParser();

    @Test
    void testParse_ValidXmlFile_ReturnsRootDto() throws JAXBException, URISyntaxException {
        Path xmlPath = Paths.get(
                getClass().getClassLoader().getResource("test-event.xml").toURI()
        );

        RootDto rootDto = xmlParser.parse(xmlPath);

        assertNotNull(rootDto);
        assertNotNull(rootDto.getRequestDetails());
        assertEquals("Company", rootDto.getRequestDetails().getSourceCompany());
    }
}
