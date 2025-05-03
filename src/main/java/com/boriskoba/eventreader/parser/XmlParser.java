package com.boriskoba.eventreader.parser;

import com.boriskoba.eventreader.dto.RootDto;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class XmlParser {

    public RootDto parse(Path filePath) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(RootDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (RootDto) unmarshaller.unmarshal(filePath.toFile());
    }
}
