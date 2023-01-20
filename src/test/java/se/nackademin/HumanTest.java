package se.nackademin;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

import org.junit.Test;

public class HumanTest {
	@Test
	public void selectPlacementShouldReturnTuple() {
		System.setIn(new ByteArrayInputStream("5:2".getBytes()));
		Human human = new Human();
		int[] got = human.selectPlacement(new Board(6));
		int[] expected = { 5, 2 };
		assertTrue(Arrays.equals(expected, got));
	}
}
