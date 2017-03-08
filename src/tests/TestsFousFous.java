package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import fousfous.Cell;
import fousfous.PlateauFousFous;

public class TestsFousFous {

	@Test
	public void testParseCell() {
		PlateauFousFous p = new PlateauFousFous();
		String cell = "B6";
		Cell c = p.parseCell(cell);
		assertEquals(5, c.getHeight());
		assertEquals(1, c.getWidth());

		String cell2 = "H8";
		Cell c2 = p.parseCell(cell2);
		assertEquals(7, c2.getHeight());
		assertEquals(7, c2.getWidth());
	}

	@Test
	public void testDoitPrendre() {
		// Joueur blanc prend le noir en diago
		PlateauFousFous p1 = new PlateauFousFous();
		p1.setFromFile("plateauTest1");
		String curr = "F3";
		Cell currCell = p1.parseCell(curr);
		String player1 = "Blanc";
		assertTrue(p1.doitPrendre(currCell, player1));

		// Joueur blanc ne peut pas prendre
		PlateauFousFous p2 = new PlateauFousFous();
		p2.setFromFile("plateauTest2");
		String curr2 = "F3";
		Cell currCell2 = p2.parseCell(curr2);
		String player2 = "Blanc";
		assertFalse(p2.doitPrendre(currCell2, player2));

		// Joueur noir prend le blanc en diago
		PlateauFousFous p3 = new PlateauFousFous();
		p3.setFromFile("plateauTest1");
		String curr3 = "D5";
		Cell currCell3 = p3.parseCell(curr3);
		String player3 = "Noir";
		assertTrue(p3.doitPrendre(currCell3, player3));
	}
	
	@Test
	public void testTrajectoireOK() {
		// Joueur blanc en F3 veut aller en D5, renvoie vrai
		PlateauFousFous p1 = new PlateauFousFous();
		p1.setFromFile("plateauTest1");
		String fst1 = "F3";
		String snd1 = "D5";
		Cell fstCell1 = p1.parseCell(fst1);
		Cell sndCell1 = p1.parseCell(snd1);
		assertTrue(p1.trajectoireOK(fstCell1, sndCell1));
		
		// Joueur blanc en F3 veut aller en E3, renvoie faux
		PlateauFousFous p2 = new PlateauFousFous();
		p2.setFromFile("plateauTest1");
		String fst2 = "F3";
		String snd2 = "E3";
		Cell fstCell2 = p1.parseCell(fst2);
		Cell sndCell2 = p1.parseCell(snd2);
		assertFalse(p2.trajectoireOK(fstCell2, sndCell2));
	}
	
	@Test
	public void testEstValide() {
		// Joueur blanc en F3 veut prendre pion noir en D5, renvoie vrai
		PlateauFousFous p1 = new PlateauFousFous();
		p1.setFromFile("plateauTest1");
		String move1 = "F3-D5";
		String player1 = "Blanc";
		assertTrue(p1.estValide(move1, player1));
		
		// Joueur blanc en F3 veut prendre pion noir en D3, renvoie faux
		PlateauFousFous p2 = new PlateauFousFous();
		p2.setFromFile("plateauTest2");
		String move2 = "F3-D3";
		assertFalse(p2.estValide(move2, player1));
		
		// Joueur noir en D3 veut prendre pion noir en B5, renvoie faux
		PlateauFousFous p3 = new PlateauFousFous();
		p3.setFromFile("plateauTest3");
		String move3 = "D3-B5";
		String player3 = "Noir";
		assertFalse(p3.estValide(move3, player3));
		
		// Joueur blanc en F3 veut se déplacer en H5 (case vide), renvoie faux
		String move4 = "F3-H5";
		assertFalse(p1.estValide(move4, player1));
		
		// Manque test menace
	}
	
	@Test
	public void testFinDePartie() {
		// Il ne reste qu'un pion blanc
		PlateauFousFous p1 = new PlateauFousFous();
		p1.setFromFile("plateauTest4");
		assertTrue(p1.finDePartie());
		
		// Il ne reste que des pions noirs
		PlateauFousFous p2 = new PlateauFousFous();
		p2.setFromFile("plateauTest5");
		assertTrue(p2.finDePartie());
		
		// Pas fin de partie, renvoie false
		PlateauFousFous p3 = new PlateauFousFous();
		p3.setFromFile("plateauTest1");
		assertFalse(p3.finDePartie());
	}

}
