package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import fousfous.Cell;
import fousfous.PlateauFousFous;

public class TestsFousFous {
	
	PlateauFousFous p = new PlateauFousFous();


	@Test
	public void testParseCell() {
		String cell = "B6";
		Cell c = p.parseCell(cell);
		assertEquals(5, c.getHeight());
		assertEquals(1, c.getWidth());
		
		String cell2 = "H8";
		Cell c2 = p.parseCell(cell2);
		assertEquals(7, c2.getHeight());
		assertEquals(7, c2.getWidth());
	}
	
	
}
