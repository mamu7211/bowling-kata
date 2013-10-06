package de.seventy2eleven.katas.bowling.model.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import de.seventy2eleven.katas.bowling.model.FrameType;

public class FrameTypeTests {
	@Test
	public void testOfEnumGetValues() {
		assertEquals(FrameType.Normal, FrameType.valueOf("Normal"));
		assertEquals(FrameType.Spare, FrameType.valueOf("Spare"));
		assertEquals(FrameType.Strike, FrameType.valueOf("Strike"));
	}

	@Test
	public void testValues() {
		FrameType[] expected = new FrameType[] { FrameType.Normal,
				FrameType.Spare, FrameType.Strike };
		FrameType[] actual = FrameType.values();
		assertTrue(Arrays.equals(expected, actual));
	}
}
