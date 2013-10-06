package de.seventy2eleven.katas.bowling.view.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.verification.VerificationMode;

import de.seventy2eleven.katas.bowling.view.ThrowingView;

public class ThrowingViewTests {

	private static final VerificationMode ONE_TIME = Mockito.times(1);
	ThrowingView throwingView;
	PrintStream printStream;
	BufferedReader inputStream;

	@Before
	public void setUp() {
		throwingView = new ThrowingView();
		printStream = Mockito.mock(PrintStream.class);
		inputStream = Mockito.mock(BufferedReader.class);
		throwingView.setWriter(printStream);
		throwingView.setReader(inputStream);
	}

	@Test
	public void showShowsMessageAndWaitsForInput() {
		assertEquals(0, throwingView.getPinsStanding());
	}

	@Test
	public void showWillDisplayMessageAndInformation() {
		throwingView.setPinsStanding(7);
		throwingView.setFrameNumber(3);
		throwingView.setBallNumber(2);
		throwingView.show();
		Mockito.verify(printStream).print(
				"Frame 3, Ball 2, 7 Pins standing. Enter Pins knocked down > ");
		// Mockito.verify(printStream).print("Enter number of pins knocked down or type 'bye' to exit: ");
	}

	@Test
	public void readingInputWillSetNumberOfPinsKickedIfNumeric()
			throws IOException {
		Mockito.stub(inputStream.readLine()).toReturn("5");
		throwingView.readInput();
		assertEquals(5, throwingView.getPinsKicked());
	}

	@Test
	public void readingInputWillTrimInputAndSetNumberPinsKickedIfNumeric()
			throws IOException {
		Mockito.stub(inputStream.readLine()).toReturn("   8  ");
		throwingView.readInput();
		assertEquals(8, throwingView.getPinsKicked());
	}

	@Test
	public void readingInputWillShowErrorMessageIfNotNumericOrNotACommand()
			throws IOException {
		Mockito.stub(inputStream.readLine()).toReturn("one");
		throwingView.readInput();
		verifyErrorMessageIsDisplayed();
	}

	@Test
	public void readingInputWithThrowingMorePinsThanStandingWillDisplayErrorMessage()
			throws IOException {
		throwingView.setPinsStanding(2);
		Mockito.stub(inputStream.readLine()).toReturn("9");
		throwingView.readInput();
		verifyErrorMessageIsDisplayed();
	}

	@Test
	public void readingInputWithNegativeNumberOfPinsWillDisplayErrorMessage()
			throws IOException {
		throwingView.setPinsStanding(10);
		Mockito.stub(inputStream.readLine()).toReturn("-1");
		throwingView.readInput();
		verifyErrorMessageIsDisplayed();
	}

	@Test
	public void readingInputWithCommandWillSetUserRequestedFinishFlag()
			throws IOException {
		Mockito.stub(inputStream.readLine()).toReturn("bye");
		throwingView.readInput();
		assertTrue(throwingView.isQuitGameRequested());
	}

	private void verifyErrorMessageIsDisplayed() {
		Mockito.verify(printStream, ONE_TIME).println(
				"Your input is invalid. Please try again!");
		Mockito.verify(printStream, ONE_TIME).print(
				"Enter number of pins knocked down or type 'bye' to exit: ");
	}

}
