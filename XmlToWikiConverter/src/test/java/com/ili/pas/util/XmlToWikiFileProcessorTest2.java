package com.ili.pas.util;

import java.io.File;
import org.junit.Before;
import org.junit.Test;

public class XmlToWikiFileProcessorTest2 {

	private XmlToWikiFileProcessor xmlToWikiFileProcessor;
	private File testFile;

	@Before
	public void setup() {
		xmlToWikiFileProcessor = new XmlToWikiFileProcessor();
		testFile = new File(this.getClass().getResource("/report.xml").getFile());
	}

	@Test
	public void testProcessFiles() {
		xmlToWikiFileProcessor.processFiles(new File[]{testFile});
	}

}
