package de.seventy2eleven.katas.bowling.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.MessageFormat;

public class ThrowingView {

	private static final String REGEX_INTEGER_ONLY = "[0-9]+";
	private static final String MSG_INPUT_PROMPT = "Enter number of pins knocked down or type 'bye' to exit: ";
	private static final String MSG_GAME_STATUS = "Frame {0}, Ball {1}, {2} Pins standing. Enter Pins knocked down > ";
	private PrintStream writer;
	private int ballNumber;
	private int frameNumber;
	private int pinsStanding;
	private BufferedReader reader;
	private int pinsKicked;
	private boolean quitRequested;

	public int getPinsStanding() {
		return 0;
	}

	public void show() {
		// writer.println();
		writer.print(MessageFormat.format(MSG_GAME_STATUS, frameNumber,
				ballNumber, pinsStanding));
		// writer.print(MSG_INPUT_PROMPT);
	}

	public void setPinsStanding(int pingStanding) {
		this.pinsStanding = pingStanding;
	}

	public void setFrameNumber(int frameNumber) {
		this.frameNumber = frameNumber;
	}

	public void setBallNumber(int ballNumber) {
		this.ballNumber = ballNumber;
	}

	public void setWriter(PrintStream writer) {
		this.writer = writer;
	}

	public void setReader(BufferedReader reader) {
		this.reader = reader;
	}

	public int getPinsKicked() {
		return pinsKicked;
	}

	public void readInput() throws IOException {
		String line = reader.readLine();

		// TODO - state not handled, do something?
		if (line == null)
			return;

		line = line.trim();

		if (line.matches(REGEX_INTEGER_ONLY)) {
			handleNumericInput(line);
		} else if (line.toLowerCase().equals("bye")) {
			quitRequested = true;
		} else {
			displayInvalidInputErrorMessage();
		}
	}

	private void handleNumericInput(String line) {
		pinsKicked = Integer.parseInt(line.trim());
		if (pinsKicked > pinsStanding) {
			displayInvalidInputErrorMessage();
		}
	}

	private void displayInvalidInputErrorMessage() {
		writer.println("Your input is invalid. Please try again!");
		writer.print(MSG_INPUT_PROMPT);
	}

	public boolean isQuitGameRequested() {
		return quitRequested;
	}

	public void showError(String string) {
		writer.println(string);
	}
}
