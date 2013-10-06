package de.seventy2eleven.katas.bowling.model.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.seventy2eleven.katas.bowling.model.Frame;
import de.seventy2eleven.katas.bowling.model.FrameType;

public class FrameTests {

	private Frame frame;

	@Before
	public void setUp() {
		frame = new Frame();
	}

	@Test
	public void newFramesHaveScoreOfZero() {
		assertEquals(0, frame.getScore());
	}

	@Test
	public void throwingASingleBallDoesNotFinishFrame() {
		frame.handleBall(3);
		assertFalse(frame.isFinished());
	}

	@Test
	public void throwingASingleBallDoesNotAllowCalculationOfScore() {
		frame.handleBall(3);
		assertEquals(0, frame.getScore());
	}

	@Test
	public void throwingTwoBallsWillFinishFrame() {
		frame.handleBall(3);
		frame.handleBall(3);
		assertTrue(frame.isFinished());
	}

	@Test
	public void throwingTwoBallsWillSumUpScore() {
		frame.handleBall(3);
		frame.handleBall(6);
		assertEquals(9, frame.getScore());
	}

	@Test(expected = IllegalArgumentException.class)
	public void throwingTwoBallsCannotKnockMoreThan10Pins() {
		frame.handleBall(9);
		frame.handleBall(2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void throwingNegativeNumberOfPinsIsNotAllowed() {
		frame.handleBall(-2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void throwingNegativeNumberOfPinsIsNeverEverNotAllowed() {
		frame.handleBall(9);
		frame.handleBall(-2);
	}

	@Test
	public void throwingAStrikeMarksTheFrameAsStrike() {
		throwStrike();
		assertTrue(frame.isStrike());
	}

	@Test
	public void throwingAStrikeMarksTheFrameAsStrikeResultType() {
		throwStrike();
		assertEquals(FrameType.Strike, frame.getType());
	}

	@Test
	public void throwingASpareMarksTheFrameAsSpareResultType() {
		throwSpare();
		assertEquals(FrameType.Spare, frame.getType());
	}

	@Test
	public void throwingNotAStrikeNorASpareMarksTheFrameAsNormalResultType() {
		frame.handleBall(1);
		frame.handleBall(2);
		assertEquals(FrameType.Normal, frame.getType());
	}

	@Test
	public void throwingTwoBallsAndKnockDownAllPinsIsASpare() {
		throwSpare();
		assertTrue(frame.isSpare());
	}

	@Test
	public void aSpareIsNotAStrike() {
		throwSpare();
		assertFalse(frame.isStrike());
		assertTrue(frame.isSpare());
	}

	@Test
	public void aStrikeIsNotASpare() {
		throwStrike();
		assertFalse(frame.isSpare());
		assertTrue(frame.isStrike());
	}

	@Test
	public void framesInheritTheirInitialScoreFromPreviousFrames() {
		Frame previous = new Frame();
		previous.handleBall(5);
		previous.handleBall(3);
		Frame current = new Frame(previous);
		current.handleBall(4);
		current.handleBall(2);
		assertEquals(14, current.getScore());
	}

	@Test
	public void bonusBallsNeededByStrikeDecreaseWhenAddingABonus() {
		throwStrike();
		assertEquals(2, frame.getNumberOfBonusBallsNeeded());
	}

	@Test
	public void throwingAStrikeWithoutFollowUpFrameDoesNotFinishFrame() {
		throwStrike();
		assertFalse(frame.isFinished());
	}

	@Test
	public void throwingAStrikeWithoutAFollowUpFrameDoesNotCalculateFramesScore() {
		throwStrike();
		assertEquals(0, frame.getScore());
	}

	@Test
	public void throwingAStrikeWithFollowUpFrameDoesFinishesUnfinishedFrame() {
		throwStrike();
		Frame followUpFrame = new Frame(frame);
		followUpFrame.handleBall(3);
		followUpFrame.handleBall(2);
		assertTrue(frame.isFinished());
	}

	@Test
	public void testToString() {
		frame.handleBall(3);
		frame.handleBall(7);
		assertEquals(
				"Frame [ballsThrown=2, bonusScore=0, bonusBallsReceived=0, pinsKnocked=[3, 7]]",
				frame.toString());
	}

	@Test
	public void throwingAStrikeWithFollowUpFrameDoesCalculateFramesScore() {
		throwStrike();
		Frame followUpFrame = new Frame(frame);
		followUpFrame.handleBall(3);
		followUpFrame.handleBall(2);
		assertEquals(15, frame.getScore());
	}

	@Test
	public void throwingASpareWithoutFollowUpFrameIsNotFinished() {
		throwSpare();
		assertFalse(frame.isFinished());
	}

	@Test
	public void throwingASpareNeedsOneFollowUpFrame() {
		throwSpare();
		assertEquals(1, frame.getNumberOfBonusBallsNeeded());
	}

	@Test
	public void throwingASpareWitFollowUpFrameFinishesFrame() {
		throwSpare();
		Frame followUpFrame = new Frame(frame);
		followUpFrame.handleBall(3);
		assertTrue(frame.isFinished());
	}

	@Test
	public void throwingASpareWithoutFollowUpFrameDoesNotCalculateFrame() {
		throwSpare();
		assertEquals(0, frame.getScore());
	}

	@Test
	public void adjustSpareScoreWithOneFollowUpThrow() {
		throwSpare();
		Frame followUpFrame = new Frame(frame);
		followUpFrame.handleBall(7);
		assertEquals(17, frame.getScore());
	}

	@Test
	public void differentiateBetweenNeededBallsAndIsFinished() {
		throwStrike();
		assertEquals(2, frame.getNumberOfBonusBallsNeeded());
		assertEquals(0, frame.getNumberOfBallsNeeded());
	}

	@Test
	public void throwingABallDecreasesNumberOfBallsNeeded() {
		frame.handleBall(0);
		assertEquals(1, frame.getNumberOfBallsNeeded());
	}

	@Test
	public void havingNotThrownABallInAFrameShouldItHaveTwoBallsNeeded() {
		assertEquals(2, frame.getNumberOfBallsNeeded());
	}

	@Test
	public void throwingTwoBallsDecreasesNumberOfBallsNeededToZero() {
		frame.handleBall(0);
		frame.handleBall(0);
		assertEquals(0, frame.getNumberOfBallsNeeded());
	}

	@Test
	public void throwingFirstBallSavesItAsFirst() {
		frame.handleBall(10);
		assertEquals(10, frame.getKnockedWithFirstBall());
	}

	@Test
	public void throwingFirstBallSavesItAtSecond() {
		frame.handleBall(3);
		frame.handleBall(7);
		assertEquals(7, frame.getKnockedWithSecondBall());
	}

	@Test
	public void throwingAStrikeSetsNeededBallsCorrectly() {
		frame.handleBall(10);
		assertFalse(frame.needMoreThrows());
		assertTrue(frame.needMoreBonusBalls());
	}

	@Test
	public void throwingASingleBallSetsNeededBallsCorrectly() {
		frame.handleBall(5);
		assertTrue(frame.needMoreThrows());
		assertFalse(frame.needMoreBonusBalls());
	}

	@Test
	public void throwingASpareSetsNeededBallsCorrectly() {
		frame.handleBall(5);
		frame.handleBall(5);
		assertFalse(frame.needMoreThrows());
		assertTrue(frame.needMoreBonusBalls());
	}

	@Test
	public void throwingNotASparkAndNotASpareSetsNeededBallsCorrectly() {
		frame.handleBall(5);
		frame.handleBall(1);
		assertFalse(frame.needMoreThrows());
		assertFalse(frame.needMoreBonusBalls());
	}

	private void throwStrike() {
		frame.handleBall(10);
	}

	private void throwSpare() {
		frame.handleBall(3);
		frame.handleBall(7);
	}
}
