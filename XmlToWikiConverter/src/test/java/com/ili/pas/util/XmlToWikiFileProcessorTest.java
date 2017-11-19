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
	private static final String NEW_LINE = "\n";
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
	@Parameters({ "\t", "\n", "\t\t", "\n\n", "\t\n\n\t", "  ", "" })
	public void isEmptyLineTestTrue(final String line) {
		assertTrue(xmlToWikiFileProcessor.isEmptyLine(line));
	}

	@Test
	@Parameters(method = "removeNewLineCharactersTestParameters")
	public void removeNewLineCharactersTest(final String line, final String expected) {
		assertEquals(expected, xmlToWikiFileProcessor.handleNewLineCharacters(line));
	}

	@SuppressWarnings("unused")
	private Object[] removeNewLineCharactersTestParameters() {
		return new Object[] { new Object[] { "\nBeginning", "Beginning" },
				new Object[] { "\n\nBeginning", "Beginning" }, new Object[] { "Beginning", "Beginning" },
				new Object[] { "End\n", "End\n" }, new Object[] { "End\n\n", "End\n" }, new Object[] { "End", "End" } };
	}

	@Test
	@Parameters(source = processContentTestParameters.class)
	public void processContentTest(final List<Object> list, final String expectedResult) {
		StringBuilder result = xmlToWikiFileProcessor.processContent(list, new StringBuilder());
		assertEquals(expectedResult, result.toString());
	}

	public static class processContentTestParameters {
		// method returning parameters has to be prefixed with 'provide'
		public static Object[] provideParameters() {
			return new Object[] { new Object[] { createContent_1(),
					" Ordinary text \n=Section Heading=\n=Section Heading=\n''Italic text'' Ordinary text '''Bold text'''" },
					new Object[] { createContent_2(),
							"=Section Heading=\n Ordinary text \n=Section Heading=\n'''Bold text''Italic text''''' Ordinary text \n Ordinary text ''Italic text'''Bold text'''''" } };
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

}
