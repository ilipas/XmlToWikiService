package com.ili.pas.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ili.pas.jaxb.Bold;
import com.ili.pas.jaxb.Italic;
import com.ili.pas.jaxb.Section;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class XmlToWikiFileProcessorTest {

	private static final String SECTION_HEADING = "Section Heading";
	private static final String ITALIC_TEXT = "Italic text";
	private static final String BOLD_TEXT = "Bold text";
	private static final String TEXT = " Ordinary text ";
	private XmlToWikiFileProcessor xmlToWikiFileProcessor;

	@Before
	public void setup() {
		xmlToWikiFileProcessor = new XmlToWikiFileProcessor();
	}

	@Test
	@Parameters({ "NotEmptyLine\t", "Not\n", "not" })
	public void isEmptyLineTestFalse(final String line) {
		assertFalse(xmlToWikiFileProcessor.isEmptyLine(line));
	}

	@Test
	@Parameters({ "\t", "\n", "\t\t", "\n\n", "\t\n" })
	public void isEmptyLineTestTrue(final String line) {
		assertTrue(xmlToWikiFileProcessor.isEmptyLine(line));
	}

	@Test
	@Parameters(method = "removeNewLineCharactersTestParameters")
	public void removeNewLineCharactersTest(final String line, final String expected) {
		assertEquals(expected, xmlToWikiFileProcessor.removeNewLineCharacters(line));
	}

	@SuppressWarnings("unused")
	private Object[] removeNewLineCharactersTestParameters() {
		return new Object[] { new Object[] { "\nBeginning", "Beginning" },
				new Object[] { "\n\nBeginning", "Beginning" }, new Object[] { "Beginning", "Beginning" },
				new Object[] { "End\n", "End\n" }, new Object[] { "End\n\n", "End\n" }, new Object[] { "End", "End" } };
	}

	@Test
	@Parameters(source = processContentTestParameters.class)
	public void processContentTest(final List<Object> list, final StringBuilder expectedResult) {
		StringBuilder result = xmlToWikiFileProcessor.processContent(list, new StringBuilder());
		assertEquals(expectedResult.toString(), result.toString());
	}

	public static class processContentTestParameters {
		// method returning parameters has to be prefixed with 'provide'
		public static Object[] provideContainsTrueParameters() {
			return new Object[] { new Object[] { createContent_1(), new StringBuilder(
					"=Section Heading=\n''Italic text'' Ordinary text '''Bold text\n'''''Italic text'''''Bold text''' Ordinary text ") },
					new Object[] { createContent_2(), new StringBuilder(
							"=Section Heading=\n''Italic text'' Ordinary text \n=Section Heading=\n Ordinary text '''Bold text'''") },
					new Object[] { createContent_3(), new StringBuilder(
							"=Section Heading=\n Ordinary text \n''Italic text''\n'''Bold text'''") } };
		}

		private static Object createContent_1() {

			List<Object> content = new ArrayList<Object>();
			Section section = new Section();
			section.setHeading(SECTION_HEADING);

			section.getContent().add(getItalic(ITALIC_TEXT));
			section.getContent().add(new String(TEXT));
			section.getContent().add(getBold(BOLD_TEXT + "\n"));

			section.getContent().add(getItalic(ITALIC_TEXT));
			section.getContent().add(getBold(BOLD_TEXT));
			section.getContent().add(new String(TEXT));

			content.add(section);

			return content;
		}

		private static Object createContent_2() {

			List<Object> content = new ArrayList<Object>();
			Section section = new Section();
			section.setHeading(SECTION_HEADING);

			section.getContent().add(getItalic(ITALIC_TEXT));
			section.getContent().add(new String(TEXT));

			Section section2 = new Section();
			section2.setHeading(SECTION_HEADING);

			section2.getContent().add(new String(TEXT));
			section2.getContent().add(getBold(BOLD_TEXT));

			content.add(section);
			content.add(section2);

			return content;
		}

		private static Object createContent_3() {

			List<Object> content = new ArrayList<Object>();
			Section section = new Section();
			section.setHeading(SECTION_HEADING);

			section.getContent().add(new String(TEXT + "\n"));
			section.getContent().add(getItalic(ITALIC_TEXT + "\n"));
			section.getContent().add(getBold(BOLD_TEXT));

			content.add(section);

			return content;
		}

		private static Object getBold(String boldText) {
			Bold bold = new Bold();
			bold.getContent().add(boldText);
			return bold;
		}

		private static Object getItalic(String italicText) {
			Italic italic = new Italic();
			italic.getContent().add(italicText);
			return italic;
		}

	}

}
