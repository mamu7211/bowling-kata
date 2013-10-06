package de.seventy2eleven.katas.bowling.model.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.seventy2eleven.katas.bowling.model.Game;

public class GameTests {

	private Game game;

	@Before
	public void setUp() {
		game = new Game();
	}

	@Test
	public void havingNotPlayedASingleFrameTheGameCannotBeFinished() {
		assertFalse(game.isFinished());
	}

	@Test
	public void havingPlayedTenFramesTheGameShouldBeFinished() {
		throwSeveralBalls(20, 1);
		assertTrue(game.isFinished());
	}

	@Test
	public void havingPlayedTenBallsLeavesGameNotFinished() {
		throwSeveralBalls(10, 1);
		assertFalse(game.isFinished());
	}

	@Test
	public void aNewGameWillAlreadyHaveOneFrame() {
		assertEquals(1, game.getFrames().length);
	}

	@Test
	public void throwingThreeBallsWillAddNewFrame() {
		throwSeveralBalls(3, 1);
		assertEquals(2, game.getFrames().length);
	}

	@Test
	public void throwingTwentyBallsWillLeadToTenFrames() {
		throwSeveralBalls(20, 1);
		assertEquals(10, game.getFrames().length);
	}

	@Test
	public void throwingTenStrikesBallsWillLeadToTenFrames() {
		throwTenStrikes();
		assertEquals(10, game.getFrames().length);
	}

	@Test
	public void throwingThreeBallsWillHaveCorrectFrameStatesInFrames() {
		throwSeveralBalls(3, 1);
		assertTrue(game.getFrames()[0].isFinished());
		assertFalse(game.getFrames()[1].isFinished());
	}

	@Test
	public void throwingTwentyGutterBallsResultsInTenFrames() {
		throwSeveralBalls(20, 0);
		assertEquals(10, game.getFrames().length);
	}

	@Test
	public void havingPlayedTenStrikesAndTwoBallsTheGameShouldBeFinished() {
		throwTenStrikes();
		throwSeveralBalls(2, 1);
		assertTrue(game.isFinished());
	}

	@Test
	public void havingPlayedTenStrikesTenthFrameShouldStillBeCurrentFrame() {
		throwTenStrikes();
		assertSame(game.getCurrentFrame(), game.getFrames()[9]);
	}

	@Test
	public void tenStrikesAndTwoStrikeBonusesTheBonusScoreOfLastFrameIsCalculatedCorrectly() {
		throwTenStrikes();
		throwSeveralBalls(2, 3);
		assertEquals(6, game.getFrames()[9].getBonusScore());
	}

	@Test
	public void havingPlayedTenStrikesAndTwoBallsTheGameShouldRecordThoseTwoBonusBalls() {
		throwTenStrikes();
		throwSeveralBalls(2, 3);
		assertTrue(game.isFinished());
	}

	@Test(expected = IllegalArgumentException.class)
	public void throwingBallKnockingInvalidNumberOfPinsDoesNotRecordThrow() {
		game.throwBall(3);
		game.throwBall(10);
		assertEquals(7, game.getPinsStanding());
	}

	@Test(expected = IllegalArgumentException.class)
	public void throwingBallKnockingInvalidNumberOfPinsDoesNotRecordThrowAsBonusForParentFrame() {
		game.throwBall(10);
		game.throwBall(3);
		game.throwBall(10);
		assertEquals(0, game.getFrames()[0].getBonusScore());
	}

	@Test
	public void currentBallNumberIsSetToOneIfNoBallsAreThrown() {
		assertEquals(1, game.getCurrentBallNumber());
	}

	@Test
	public void currentBallNumberIsIncrementedWhenBallIsThrown() {
		game.throwBall(0);
		assertEquals(2, game.getCurrentBallNumber());
	}

	@Test
	public void throwingABallsDecreasesNumberOfStandingPins() {
		game.throwBall(6);
		assertEquals(4, game.getPinsStanding());
	}

	@Test
	public void throwingTwoBallsAndKnockDownAllPinsResetsPins() {
		throwSpare();
		assertEquals(10, game.getPinsStanding());
	}

	@Test
	public void throwingTwoBallsAndKnockDownNotAllPinsResetsPins() {
		game.throwBall(3);
		game.throwBall(0);
		assertEquals(10, game.getPinsStanding());
	}

	@Test
	public void havingPlayedTenStrikesTenPinsShouldBeStandingForBonusThrows() {
		throwTenStrikes();
		assertEquals(10, game.getPinsStanding());
	}

	@Test
	public void finishingAFrameResetsPinsStanding() {
		throwSpare();
		assertEquals(10, game.getPinsStanding());
	}

	@Test
	public void playGameViaEmptyArray() {
		game.play(new int[] {});
		assertEquals(0, game.getScore());
	}

	@Test
	public void playTwoThrowsUsingArray() {
		game.play(new int[] { 3, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0 });
		assertEquals(7, game.getScore());
	}

	@Test
	public void playThreeThrowsUsingArray() {
		game.play(new int[] { 3, 7, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0 });
		assertEquals(18, game.getScore());
	}

	@Test
	public void playStrikeAndTwoThrowsUsingArray() {
		game.play(new int[] { 10, 5, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0 });
		assertEquals(28, game.getScore());
	}

	@Test(expected = IllegalStateException.class)
	public void throwingABallAfterGameIsFinishedRaisesAnException() {
		throwSeveralBalls(20, 0);
		assertTrue(game.isFinished());
		game.throwBall(5);
	}

	private void throwSeveralBalls(int numberOfBalls, int pinsKicked) {
		for (int i = 0; i < numberOfBalls; i++)
			game.throwBall(pinsKicked);
	}

	private void throwTenStrikes() {
		for (int i = 0; i < 10; i++) {
			game.throwBall(10);
		}
	}

	private void throwSpare() {
		throwSeveralBalls(2, 5);
	}
}
