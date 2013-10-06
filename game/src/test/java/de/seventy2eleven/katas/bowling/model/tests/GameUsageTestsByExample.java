package de.seventy2eleven.katas.bowling.model.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.seventy2eleven.katas.bowling.model.Game;

public class GameUsageTestsByExample {

	private Game game;

	@Before
	public void setUp() {
		game = new Game();
	}

	@Test
	public void playExampleOneGameUsingThrowMethod() {
		game.throwBall(1);
		game.throwBall(4);
		game.throwBall(4);
		game.throwBall(5);
		game.throwBall(6);
		game.throwBall(4);
		game.throwBall(5);
		game.throwBall(5);
		game.throwBall(10);
		game.throwBall(0);
		game.throwBall(1);
		game.throwBall(7);
		game.throwBall(3);
		game.throwBall(6);
		game.throwBall(4);
		game.throwBall(10);
		game.throwBall(2);
		game.throwBall(8);
		game.throwBall(6);
		assertEquals(133, game.getScore());
	}

	@Test
	public void playExampleOneGameUsingAnArray() {
		game.play(new int[] { 1, 4, 4, 5, 6, 4, 5, 5, 10, 0, 1, 7, 3, 6, 4, 10,
				2, 8, 6 });
		assertEquals(133, game.getScore());
	}

	@Test
	public void playExampleTwoGameUsingAnArray() {
		game.play(new int[] { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 });
		assertEquals(300, game.getScore());
	}
}
