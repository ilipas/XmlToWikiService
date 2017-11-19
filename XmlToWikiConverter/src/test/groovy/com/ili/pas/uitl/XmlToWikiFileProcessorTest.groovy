package com.ili.pas.uitl

import static org.junit.Assert.*

import java.util.ArrayList
import java.util.List

import org.junit.Before
import org.junit.Test

import com.ili.pas.jaxb.Bold
import com.ili.pas.jaxb.Italic
import com.ili.pas.jaxb.Section
import com.ili.pas.util.XmlToWikiFileProcessor

class XmlToWikiFileProcessorTest extends spock.lang.Specification {
	
	private static final String SECTION_HEADING = "Section Heading";
	private static final String ITALIC_TEXT = "Italic text";
	private static final String BOLD_TEXT = "Bold text";
	private static final String TEXT = " Ordinary text ";
	private static final String NEW_LINE = "\n";

	@Test
	public void testIsEmptyLine() {

		given:"xml to wiki file processor"
		XmlToWikiFileProcessor xmlToWikiFileProcessor = new XmlToWikiFileProcessor();

		when: "a line is checked if empty line or not"
		def isEmptyLine = xmlToWikiFileProcessor.isEmptyLine(text)

		then: "result is a boolean value"
		isEmptyLine == expectedResult

		where:"lines to check and expected boolean results are"
		text              || expectedResult
		"NotEmptyLine\t"  ||  false
		"Not\n"           ||  false
		"not"             ||  false
		"\t"              ||  true
		"\n"              ||  true
		"\t\t"            ||  true
		"\n\n"            ||  true
		"\t\n\n\t"        ||  true
	}

	@Test
	public void testHandleNewLineCharacters() {
		
		given:"xml to wiki file processor"
		XmlToWikiFileProcessor xmlToWikiFileProcessor = new XmlToWikiFileProcessor();

		when: "a line is passed in to be checked"
		def result = xmlToWikiFileProcessor.handleNewLineCharacters(text)

		then: "a text is returned without new line character at the beginning and no more than one line character at the end"
		result == expectedResult

		where:"lines to check and expected boolean results are"
		text              || expectedResult
		"\nBeginning"     ||  "Beginning"
		"Beginning"       ||  "Beginning"
		"\n\nBeginning"   ||  "Beginning"
		"Beginning"       ||  "Beginning"
		"End\n"           ||  "End\n"
		"End\n\n\n"       ||  "End\n"
		"End"             ||  "End"
	}

	@Test
	public void testProcessContent() {
		
		given:"xml to wiki file processor"
		XmlToWikiFileProcessor xmlToWikiFileProcessor = new XmlToWikiFileProcessor();

		when: "a content is passed in to be processed"
		def result = xmlToWikiFileProcessor.processContent(content, new StringBuilder())

		then: "the correct wiki markup is applied"
		result.toString() == expectedResult

		where:"a contents to process and expected results are"
		content               || expectedResult
		createContent_1()     ||  " Ordinary text \n=Section Heading=\n=Section Heading=\n''Italic text'' Ordinary text '''Bold text'''"
		createContent_2()     ||  "=Section Heading=\n Ordinary text \n=Section Heading=\n'''Bold text''Italic text''''' Ordinary text \n Ordinary text ''Italic text'''Bold text'''''"
	}
	
	/**
	 * Generates the following content: <br>
	 * Text <br>
	 * Section <br>
	 * Section <br>
	 * Italic <br>
	 * Text <br>
	 * Bold
	 */
	private static Object createContent_1() {

		List<Object> content = new ArrayList<Object>();

		content.add(new String(TEXT));

		Section section1 = new Section();
		section1.setHeading(SECTION_HEADING);

		Section section2 = new Section();
		section2.setHeading(SECTION_HEADING);

		section2.getContent().add(getItalic(ITALIC_TEXT, null));
		section2.getContent().add(new String(TEXT));
		section2.getContent().add(getBold(BOLD_TEXT, null));

		content.add(section1);
		content.add(section2);

		return content;
	}

	/**
	 * Generates the following content: <br>
	 * Section <br>
	 * Text <br>
	 * Section <br>
	 * Bold <Italic> Text <br>
	 * Text Italic <Bold> <br>
	 */
	private static Object createContent_2() {

		List<Object> content = new ArrayList<Object>();
		Section section1 = new Section();
		section1.setHeading(SECTION_HEADING);

		section1.getContent().add(new String(TEXT));

		Section section2 = new Section();
		section2.setHeading(SECTION_HEADING);

		section2.getContent().add(getBold(BOLD_TEXT, getItalic(ITALIC_TEXT, null)));
		section2.getContent().add(new String(TEXT + NEW_LINE));

		section2.getContent().add(new String(NEW_LINE + TEXT));
		section2.getContent().add(getItalic(ITALIC_TEXT, getBold(BOLD_TEXT, null)));

		content.add(section1);
		content.add(section2);

		return content;
	}

	private static Object getBold(String boldText, Object object) {
		Bold bold = new Bold();
		bold.getContent().add(boldText);
		if (object != null) {
			bold.getContent().add(object);
		}
		return bold;
	}

	private static Object getItalic(String italicText, Object object) {
		Italic italic = new Italic();
		italic.getContent().add(italicText);
		if (object != null) {
			italic.getContent().add(object);
		}
		return italic;
	}
}
