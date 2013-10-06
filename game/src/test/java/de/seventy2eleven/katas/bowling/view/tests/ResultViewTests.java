package de.seventy2eleven.katas.bowling.view.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import de.seventy2eleven.katas.bowling.model.Frame;
import de.seventy2eleven.katas.bowling.view.ResultView;

public class ResultViewTests {

	private static final String FRAME_NUMBER = "     10|";
	private ResultView resultView;
	private PrintStream writer;
	private Frame strikeFrame;
	private Frame spareFrame;
	private Frame normalFrame;

	@Before
	public void setUp() {
		resultView = new ResultView();
		writer = Mockito.mock(PrintStream.class);
		resultView.setWriter(writer);

		normalFrame = new Frame();
		normalFrame.handleBall(3);
		normalFrame.handleBall(5);

		spareFrame = new Frame();
		spareFrame.handleBall(4);
		spareFrame.handleBall(6);

		strikeFrame = new Frame();
		strikeFrame.handleBall(10);
		strikeFrame.handleBonus(8);
		strikeFrame.handleBonus(9);
	}

	@Test
	public void assertLayoutOfFrameNumber() {
		assertEquals(FRAME_NUMBER, resultView.getFrameNumberCell(10));
	}

	@Test
	public void assertLayoutOfFrameScore() {
		assertEquals("      8|", resultView.getFrameScoreCell(normalFrame));
	}

	@Test
	public void assertFrameTypeNormalTextValid() {
		assertEquals("       |", resultView.getFrameTypeCell(normalFrame));
	}

	@Test
	public void assertSpareTextValid() {
		assertEquals("  SPARE|", resultView.getFrameTypeCell(spareFrame));
	}

	@Test
	public void assertStrikeTextValid() {
		assertEquals(" STRIKE|", resultView.getFrameTypeCell(strikeFrame));
	}

	@Test
	public void assertFrameTextValid() {
		assertEquals("  3|  5|", resultView.getFrameThrowsCell(normalFrame));
	}

	@Test
	public void assertStrikeFrameTextValid() {
		assertEquals("     10|", resultView.getFrameThrowsCell(strikeFrame));
	}

	@Test
	public void assertStrikeBonusTextValid() {
		assertEquals("     17|", resultView.getFrameBonusCell(strikeFrame));
	}

	@Test
	public void assertSpareBonusTextValid() {
		assertEquals("      0|", resultView.getFrameBonusCell(spareFrame));
	}

	@Test
	public void assertNormalBonusTextValid() {
		assertEquals("       |", resultView.getFrameBonusCell(normalFrame));
	}

	// ... etc.
}
