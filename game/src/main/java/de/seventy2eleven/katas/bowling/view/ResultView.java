package de.seventy2eleven.katas.bowling.view;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.plexus.util.StringUtils;

import de.seventy2eleven.katas.bowling.model.Frame;
import de.seventy2eleven.katas.bowling.model.FrameType;

public class ResultView {

	private static final String FRAME_NUMBER = "FRAME_NUMBER";
	private static final String FRAME_SCORE = "FRAME_SCORE";
	private static final String FRAME_PINS = "FRAME_PINS";
	private static final String FRAME_TYPE = "FRAME_TYPE";
	private static final String FRAME_BONUS = "FRAME_BONUS";
	private static final String LINE_HEADING_FRAME = "|Frame:|";
	private static final String LINE_HEADING_TYPE = "| Type:|";
	private static final String LINE_HEADING_PINS = "| Pins:|";
	private static final String LINE_HEADING_BONUS = "|Bonus:|";
	private static final String LINE_HEADING_SCORE = "|Score:|";
	private static final String FORMAT_FRAME_NUMBER = "    %3d|";
	private static final String FORMAT_FRAME_PINS = " %2d| %2d|";
	private static final String TEXT_FRAME_STRIKE = " STRIKE|";
	private static final String TEXT_FRAME_SPARE = "  SPARE|";
	private static final String TEXT_FRAME_NORMAL = "       |";

	private Map<String, StringBuilder> lines = new HashMap<String, StringBuilder>();

	private PrintStream writer = System.out;
	private Frame[] frames;

	public void show(Frame[] frames) {
		if (frames == null)
			return;

		this.frames = frames;
		createLines();
		fillLines();
		outputLines();
	}

	private void outputLines() {
		int len = lines.get(FRAME_NUMBER).toString().length();
		printDelimiter(len);
		writer.println(lines.get(FRAME_NUMBER).toString());
		printDelimiter(len);
		writer.println(lines.get(FRAME_PINS).toString());
		printDelimiter(len);
		writer.println(lines.get(FRAME_BONUS).toString());
		printDelimiter(len);
		writer.println(lines.get(FRAME_TYPE).toString());
		printDelimiter(len);
		writer.println(lines.get(FRAME_SCORE).toString());
		printDelimiter(len);
	}

	private void createLines() {
		lines.put(FRAME_NUMBER, new StringBuilder(LINE_HEADING_FRAME));
		lines.put(FRAME_SCORE, new StringBuilder(LINE_HEADING_SCORE));
		lines.put(FRAME_PINS, new StringBuilder(LINE_HEADING_PINS));
		lines.put(FRAME_TYPE, new StringBuilder(LINE_HEADING_TYPE));
		lines.put(FRAME_BONUS, new StringBuilder(LINE_HEADING_BONUS));
	}

	private void fillLines() {
		for (int i = 0; i < frames.length; i++) {
			Frame frame = frames[i];
			lines.get(FRAME_NUMBER).append(getFrameNumberCell(i + 1));
			lines.get(FRAME_SCORE).append(getFrameScoreCell(frame));
			lines.get(FRAME_PINS).append(getFrameThrowsCell(frame));
			lines.get(FRAME_TYPE).append(getFrameTypeCell(frame));
			lines.get(FRAME_BONUS).append(getFrameBonusCell(frame));
		}
	}

	private void printDelimiter(int length) {
		writer.println(StringUtils.repeat("-", length));
	}

	public void setWriter(PrintStream writer) {
		this.writer = writer;
	}

	public String getFrameNumberCell(int frameNumber) {
		return String.format(FORMAT_FRAME_NUMBER, frameNumber);
	}

	public String getFrameScoreCell(Frame frame) {
		return String.format(FORMAT_FRAME_NUMBER, frame.getScore());
	}

	public String getFrameTypeCell(Frame frame) {
		switch (frame.getType()) {
		case Strike:
			return TEXT_FRAME_STRIKE;
		case Spare:
			return TEXT_FRAME_SPARE;
		default:
			return TEXT_FRAME_NORMAL;
		}
	}

	public Object getFrameThrowsCell(Frame frame) {
		if (frame.isStrike())
			return String.format(FORMAT_FRAME_NUMBER,
					frame.getKnockedWithFirstBall());
		return String.format(FORMAT_FRAME_PINS,
				frame.getKnockedWithFirstBall(),
				frame.getKnockedWithSecondBall());
	}

	public Object getFrameBonusCell(Frame frame) {
		if (frame.isStrike() || frame.isSpare())
			return String.format(FORMAT_FRAME_NUMBER, frame.getBonusScore());
		return TEXT_FRAME_NORMAL;
	}
}
