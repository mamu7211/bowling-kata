package de.seventy2eleven.katas.bowling.model.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import de.seventy2eleven.katas.bowling.model.Frame;

// For Double's pinfall and Turkey's pinfall see http://en.wikipedia.org/wiki/Ten-pin_bowling

public class FrameUsageTestsByExample {

	@Test
	public void playingExampleOne() {
		Frame frame = createFrameAndThrowBalls(1, 4, null);
		frame = createFrameAndThrowBalls(4, 5, frame);
		frame = createFrameAndThrowBalls(6, 4, frame);
		frame = createFrameAndThrowBalls(5, 5, frame);
		frame = createFrameAndThrowBalls(10, -1, frame);
		frame = createFrameAndThrowBalls(0, 1, frame);
		frame = createFrameAndThrowBalls(7, 3, frame);
		frame = createFrameAndThrowBalls(6, 4, frame);
		frame = createFrameAndThrowBalls(10, -1, frame);
		frame = createFrameAndThrowBalls(2, 8, frame);

		// Tenth frame has one missing bonus ball because second throw
		// caused a Spare. Now let's finish this game...
		frame.handleBonus(6);

		assertTrue(frame.isFinished());
		assertEquals(133, frame.getScore());
	}

	@Test
	public void playingExampleTwo() {
		Frame frame = createTenThrows(10, -1);
		// Last frame is unfinished and need two balls
		frame.handleBonus(10);
		frame.handleBonus(10);

		assertTrue(frame.isFinished());
		assertEquals(300, frame.getScore());
	}

	@Test
	public void playingOnlySpares() {
		Frame frame = createTenThrows(4, 6);
		// Ten Spares, so one bonus is needed
		assertFalse(frame.isFinished());
		frame.handleBonus(1);
		assertTrue(frame.isFinished());
		assertEquals(137, frame.getScore());
	}

	@Test
	public void playingUnluckyGameGame() {
		Frame frame = createTenThrows(1, 1);
		assertTrue(frame.isFinished());
		assertEquals(20, frame.getScore());
	}

	@Test
	public void playingADoublesPinfall() {
		Frame frame = createFrameAndThrowStrike(null);
		frame = createFrameAndThrowStrike(frame);
		frame = createFrameAndThrowBalls(9, 0, frame);
		assertEquals(57, frame.getScore());
	}

	@Test
	public void playingATurkeysPinfall() {
		Frame frame = createFrameAndThrowStrike(null);
		frame = createFrameAndThrowStrike(frame);
		frame = createFrameAndThrowStrike(frame);
		frame = createFrameAndThrowBalls(0, 9, frame);
		assertEquals(78, frame.getScore());
	}

	@Test
	public void createTwoStrikesAndOneBallBonusWillBeAddedCorrectly() {
		Frame frame1 = createFrameAndThrowStrike(null);
		Frame frame2 = createFrameAndThrowStrike(frame1);
		Frame frame3 = new Frame(frame2);
		frame3.handleBall(5);
		assertEquals(15, frame1.getBonusScore());
		assertEquals(5, frame2.getBonusScore());
	}

	private Frame createTenThrows(int pinsKnockedFirst, int pinsKnockedSecond) {
		Frame frame = null;
		for (int i = 0; i < 10; i++) {
			frame = createFrameAndThrowBalls(pinsKnockedFirst,
					pinsKnockedSecond, frame);
		}
		return frame;
	}

	private Frame createFrameAndThrowStrike(Frame parentFrame) {
		Frame frame = parentFrame != null ? new Frame(parentFrame)
				: new Frame();
		frame.handleBall(10);
		return frame;
	}

	private Frame createFrameAndThrowBalls(int pinsKnockedFirst,
			int pinsKnockedSecond, Frame parentFrame) {
		Frame frame = parentFrame != null ? new Frame(parentFrame)
				: new Frame();
		frame.handleBall(pinsKnockedFirst);
		if (frame.isStrike() == false)
			frame.handleBall(pinsKnockedSecond);
		return frame;
	}
}
