package de.seventy2eleven.katas.bowling.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import de.seventy2eleven.katas.bowling.model.Game;
import de.seventy2eleven.katas.bowling.view.ResultView;
import de.seventy2eleven.katas.bowling.view.ThrowingView;

public class GameController {

	private Game game = new Game();
	private ThrowingView throwingView;
	private ResultView resultView;

	public GameController() {
		throwingView = new ThrowingView();
		throwingView.setReader(new BufferedReader(new InputStreamReader(
				System.in)));
		throwingView.setWriter(System.out);
		resultView = new ResultView();
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public void run() {
		do {
			showCurrentState();
			handleUserInput();
		} while (game.isFinished() == false
				&& throwingView.isQuitGameRequested() == false);
		resultView.show(game.getFrames());
	}

	private void showCurrentState() {
		throwingView.setBallNumber(game.getCurrentBallNumber());
		throwingView.setFrameNumber(game.getFrames().length);
		throwingView.setPinsStanding(game.getPinsStanding());
		throwingView.show();
	}

	private void handleUserInput() {
		try {
			throwingView.readInput();
			if (throwingView.isQuitGameRequested() == false) {
				int pinsKicked = throwingView.getPinsKicked();
				int pinsStanding = game.getPinsStanding();
				if (pinsStanding < pinsKicked) {
					throwingView.showError("No cheating please!");
				} else {
					game.throwBall(pinsKicked);
				}

			}
		} catch (IOException e) {
			// TODO - set state to exit game...
		}
	}

	public void setThrowingView(ThrowingView throwingView) {
		this.throwingView = throwingView;
	}

	public void setResultView(ResultView resultView) {
		this.resultView = resultView;
	}

}
