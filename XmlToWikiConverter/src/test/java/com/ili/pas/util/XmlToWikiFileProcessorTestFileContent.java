package com.ili.pas.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.Before;
import org.junit.Test;

import com.ili.pas.jaxb.ObjectFactory;
import com.ili.pas.jaxb.Report;

public class XmlToWikiFileProcessorTestFileContent {

	private static final String TEST_PRODUCED_FILE_NAME = "testProducedFile.wiki";
	private static final String REPORT_FILE_NAME = "/report.xml";
	private static final String TEST_AGAINST_FILE_NAME = "/testAgainstFile.wiki";
	private XmlToWikiFileProcessor xmlToWikiFileProcessor;
	private File reportFile;
	private File testAgainstFile;
	private String baseUrl;
	private Unmarshaller unmarshaller;
	private JAXBContext jaxbContext;
	

	@Before
	public void setup() throws JAXBException {
		xmlToWikiFileProcessor = new XmlToWikiFileProcessor();
		jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
		unmarshaller = jaxbContext.createUnmarshaller();
		baseUrl = FilenameUtils.getPath(this.getClass().getResource(REPORT_FILE_NAME).getFile());
		reportFile = new File(this.getClass().getResource(REPORT_FILE_NAME).getFile());
		testAgainstFile = new File(this.getClass().getResource(TEST_AGAINST_FILE_NAME).getFile());
	}
	
	@Test
	public void testCompareFiles() throws JAXBException, IOException {
		
		Report report = (Report) unmarshaller.unmarshal(reportFile);
		List<Object> reportContent = report.getContent();
		StringBuilder stringBuilder = xmlToWikiFileProcessor.processContent(reportContent, new StringBuilder());
		File file = new File(baseUrl + TEST_PRODUCED_FILE_NAME);
		FileUtils.writeStringToFile(file, stringBuilder.toString(), Charset.defaultCharset());
		
		assertTrue(FileUtils.contentEquals(testAgainstFile, file));
	}

}
