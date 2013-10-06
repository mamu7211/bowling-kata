package de.seventy2eleven.katas.bowling.model;

import java.util.ArrayList;
import java.util.List;

public class Game {

	private static final int INITIAL_NUMBER_OF_PINS = 10;

	private Frame frame;
	private List<Frame> frames;
	private int pinsStanding;
	private int currentBallNumber = 1;

	public Game() {
		frame = new Frame();
		frames = new ArrayList<Frame>();
		frames.add(frame);
		pinsStanding = INITIAL_NUMBER_OF_PINS;
	}

	public Frame[] getFrames() {
		return frames.toArray(new Frame[] {});
	}

	public boolean isFinished() {
		return frames.size() == 10 && frame.isFinished();
	}

	public void throwBall(int pinsKnocked) {
		handlePins(pinsKnocked);
		handleThrowAtFrame(pinsKnocked);
		addNewFrameIfNeeded();
	}

	private void handleThrowAtFrame(int pinsKnocked) {
		currentBallNumber++;
		if (frame.needMoreThrows()) {
			frame.handleBall(pinsKnocked);
		} else if (frame.needMoreBonusBalls()) {
			frame.handleBonus(pinsKnocked);
		} else {
			throw new IllegalStateException("Game is already finished!");
		}
	}

	private void addNewFrameIfNeeded() {
		if (frame.needMoreThrows() == false && frames.size() < 10) {
			frame = new Frame(frame);
			frames.add(frame);
			pinsStanding = 10;
			currentBallNumber = 1;
		}
	}

	private void handlePins(int pinsKnocked) {
		if (pinsStanding < pinsKnocked) {
			throw new IllegalArgumentException(
					"Illegal number of pins knocked!");
		}
		pinsStanding -= pinsKnocked;
		if (pinsStanding == 0) {
			pinsStanding = 10;
		}
	}

	public int getPinsStanding() {
		return pinsStanding;
	}

	public int getCurrentBallNumber() {
		return this.currentBallNumber;
	}

	public int getScore() {
		return frames.get(frames.size() - 1).getScore();
	}

	public Frame getCurrentFrame() {
		return frame;
	}

	public void play(int[] throwsPlayed) {
		for (int i = 0; i < throwsPlayed.length; i++) {
			throwBall(throwsPlayed[i]);
		}
	}

}
