package de.seventy2eleven.katas.bowling.controller.tests;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import de.seventy2eleven.katas.bowling.app.GameController;
import de.seventy2eleven.katas.bowling.model.Frame;
import de.seventy2eleven.katas.bowling.model.Game;
import de.seventy2eleven.katas.bowling.view.ResultView;
import de.seventy2eleven.katas.bowling.view.ThrowingView;

public class GameControllerTests {

	private GameController gameController;
	private Game gameMock;
	private ThrowingView throwingViewMock;
	private ResultView resultViewMock;
	private Frame[] resultFrames = new Frame[0];

	@Before
	public void setUp() {
		gameController = new GameController();

		gameMock = Mockito.mock(Game.class);
		throwingViewMock = Mockito.mock(ThrowingView.class);
		resultViewMock = Mockito.mock(ResultView.class);

		Mockito.stub(gameMock.getFrames()).toReturn(resultFrames);

		gameController.setGame(gameMock);
		gameController.setThrowingView(throwingViewMock);
		gameController.setResultView(resultViewMock);
	}

	@Test
	public void ifGameStartedUserIsShownTheThrowingView() {
		Mockito.stub(gameMock.isFinished()).toReturn(true);
		gameController.run();
		Mockito.verify(throwingViewMock).show();
	}

	@Test
	public void ifGameEndedResultVieWShouldBeShown() {
		Mockito.stub(gameMock.isFinished()).toReturn(true);
		gameController.run();
		Mockito.verify(resultViewMock).show(resultFrames);
	}

	@Test
	public void ifGameStartedUserInputIsRequested() throws IOException {
		Mockito.stub(gameMock.isFinished()).toReturn(true);
		gameController.run();
		Mockito.verify(throwingViewMock).readInput();
	}

	@Test
	public void havingAGameWithTenPinsStandingAndEnteredThreeKnockedWillCallThrowBall()
			throws IOException {
		Mockito.stub(gameMock.getPinsStanding()).toReturn(10);
		Mockito.stub(throwingViewMock.getPinsKicked()).toReturn(3);
		Mockito.stub(gameMock.isFinished()).toReturn(true);
		gameController.run();
		Mockito.verify(gameMock).throwBall(3);
	}

	@Test
	public void foo() throws IOException {
		Mockito.stub(throwingViewMock.getPinsKicked()).toReturn(3);
		Mockito.stub(gameMock.getPinsStanding()).toReturn(1);
		Mockito.stub(gameMock.isFinished()).toReturn(true);
		gameController.run();
		Mockito.verify(throwingViewMock).showError("No cheating please!");
	}

	@Test
	public void simulateEndOfGameIfQuitIsRequestedByUser() throws IOException {
		Mockito.stub(throwingViewMock.isQuitGameRequested()).toReturn(true);
		gameController.run();
		Mockito.verify(resultViewMock).show(resultFrames);
	}
}
