package de.seventy2eleven.katas.bowling.model;

import java.util.Arrays;

public class Frame {

	private static final int INITIAL_NUMBER_OF_PINS = 10;

	private Frame previousFrame;

	private int ballsThrown;
	private int bonusScore;
	private int bonusBallsReceived;

	private int[] pinsKnocked = new int[2];

	public Frame() {
	}

	public Frame(Frame previous) {
		this.previousFrame = previous;
	}

	public void handleBall(int pinsKnocked) {
		validatePins(pinsKnocked);
		handleBallForPreviousFrames(pinsKnocked);
		handleBallForThisFrame(pinsKnocked);
	}

	private void validatePins(int pinsKnocked) {
		if (pinsKnocked < 0 || pinsKnocked > INITIAL_NUMBER_OF_PINS)
			throw new IllegalArgumentException(
					"pinsKnocked must be within [0..10].");
	}

	public int getScore() {
		if (isFinished() == false)
			return 0;
		return getPreviousScore() + sumOfKnockedPins() + this.bonusScore;
	}

	private int getPreviousScore() {
		return previousFrame == null ? 0 : previousFrame.getScore();
	}

	public void handleBonus(int pinsKnocked) {
		handleOwnBonus(pinsKnocked);
		addBonusToPreviousFrame(pinsKnocked);
	}

	private void handleOwnBonus(int pinsKnocked) {
		if (bonusBallsReceived < getNumberOfBonusBallsNeeded()) {
			bonusBallsReceived++;
			this.bonusScore += pinsKnocked;
		}
	}

	public void addBonusToPreviousFrame(int pinsKnocked) {
		if (previousFrame != null)
			previousFrame.handleBonus(pinsKnocked);
	}

	public boolean isFinished() {
		return getNumberOfBallsNeeded() == 0
				&& bonusBallsReceived >= getNumberOfBonusBallsNeeded();
	}

	public boolean isStrike() {
		return getType() == FrameType.Strike;
	}

	public boolean isSpare() {
		return getType() == FrameType.Spare;
	}

	public int getNumberOfBallsNeeded() {
		return isStrike() ? 0 : 2 - ballsThrown;
	}

	public int getKnockedWithFirstBall() {
		return pinsKnocked[0];
	}

	public int getKnockedWithSecondBall() {
		return pinsKnocked[1];
	}

	public boolean hasUnfinishedPreviousFrame() {
		return this.previousFrame != null
				&& previousFrame.isFinished() == false;
	}

	public int getNumberOfBonusBallsNeeded() {
		switch (getType()) {
		case Strike:
			return 2;
		case Spare:
			return 1;
		default:
			return 0;
		}
	}

	private void handleBallForThisFrame(int pinsKnocked) {
		if (this.getNumberOfBallsNeeded() > 0) {
			validatePinsAgainstAlreadyKicked(pinsKnocked);
			this.pinsKnocked[ballsThrown] = pinsKnocked;
			ballsThrown++;
		}
	}

	private void validatePinsAgainstAlreadyKicked(int pinsKnocked) {
		if (pinsKnocked > INITIAL_NUMBER_OF_PINS - (getKnockedWithFirstBall())) {
			throw new IllegalArgumentException(
					"More pins knocked down than allowed.");
		}
	}

	private void handleBallForPreviousFrames(int pinsKnocked) {
		if (hasUnfinishedPreviousFrame()) {
			addBonusToPreviousFrame(pinsKnocked);
		}
	}

	public FrameType getType() {
		if (getKnockedWithFirstBall() == INITIAL_NUMBER_OF_PINS)
			return FrameType.Strike;
		if (sumOfKnockedPins() == INITIAL_NUMBER_OF_PINS)
			return FrameType.Spare;
		return FrameType.Normal;
	}

	private int sumOfKnockedPins() {
		return pinsKnocked[0] + pinsKnocked[1];
	}

	public int getBonusScore() {
		return bonusScore;
	}

	@Override
	public String toString() {
		return "Frame [ballsThrown=" + ballsThrown + ", bonusScore="
				+ bonusScore + ", bonusBallsReceived=" + bonusBallsReceived
				+ ", pinsKnocked=" + Arrays.toString(pinsKnocked) + "]";
	}

	public boolean needMoreThrows() {
		return getNumberOfBallsNeeded() > 0;
	}

	public boolean needMoreBonusBalls() {
		return getNumberOfBonusBallsNeeded() > bonusBallsReceived;
	}

}
