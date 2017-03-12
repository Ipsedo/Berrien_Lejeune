package tests;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.Arrays;
import java.util.List;

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
		assertTrue(p1.doitPrendre(currCell, PlateauFousFous.JBLANC));

		// Joueur blanc ne peut pas prendre
		PlateauFousFous p2 = new PlateauFousFous();
		p2.setFromFile("plateauTest2");
		String curr2 = "F3";
		Cell currCell2 = p2.parseCell(curr2);
		assertFalse(p2.doitPrendre(currCell2, PlateauFousFous.JBLANC));

		// Joueur noir prend le blanc en diago
		PlateauFousFous p3 = new PlateauFousFous();
		p3.setFromFile("plateauTest1");
		String curr3 = "D5";
		Cell currCell3 = p3.parseCell(curr3);
		assertTrue(p3.doitPrendre(currCell3, PlateauFousFous.JNOIR));
		
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
		
		// Test trajectoire pas bonne
		PlateauFousFous p4 = new PlateauFousFous();
		p4.setFromFile("plateau.txt");
		String fst3 = "B7";
		String snd3 = "E4";
		assertFalse(p4.trajectoireOK(p4.parseCell(fst3), p4.parseCell(snd3)));
	}
	
	@Test
	public void testMenaceOk(){
		PlateauFousFous p1 = new PlateauFousFous();
		p1.setFromFile("plateauTest6");
		
		String c1Str = "E4";
		assertTrue(p1.menaceOk(p1.parseCell(c1Str), PlateauFousFous.JBLANC));
		assertFalse(p1.menaceOk(p1.parseCell(c1Str), PlateauFousFous.JNOIR));
		
		String c2Str = "H1";
		assertTrue(p1.menaceOk(p1.parseCell(c2Str), PlateauFousFous.JBLANC));
		assertFalse(p1.menaceOk(p1.parseCell(c2Str), PlateauFousFous.JNOIR));
		
		String c3Str = "D1";
		assertTrue(p1.menaceOk(p1.parseCell(c3Str), PlateauFousFous.JBLANC));
		assertFalse(p1.menaceOk(p1.parseCell(c3Str), PlateauFousFous.JNOIR));
		
		String c4Str = "H5";
		assertTrue(p1.menaceOk(p1.parseCell(c4Str), PlateauFousFous.JBLANC));
		assertFalse(p1.menaceOk(p1.parseCell(c4Str), PlateauFousFous.JNOIR));
		
		String c5Str = "F1";
		assertFalse(p1.menaceOk(p1.parseCell(c5Str), PlateauFousFous.JBLANC));
		assertFalse(p1.menaceOk(p1.parseCell(c5Str), PlateauFousFous.JNOIR));
	}
	
	@Test
	public void testEstValide() {
		// Joueur blanc en F3 veut prendre pion noir en D5, renvoie vrai
		PlateauFousFous p1 = new PlateauFousFous();
		p1.setFromFile("plateauTest1");
		String move1 = "F3-D5";
		assertTrue(p1.estValide(move1, PlateauFousFous.JBLANC));
		
		// Joueur blanc en F3 veut prendre pion noir en D3, renvoie faux
		PlateauFousFous p2 = new PlateauFousFous();
		p2.setFromFile("plateauTest2");
		String move2 = "F3-D3";
		assertFalse(p2.estValide(move2, PlateauFousFous.JBLANC));
		
		// Joueur noir en D3 veut prendre pion noir en B5, renvoie faux
		PlateauFousFous p3 = new PlateauFousFous();
		p3.setFromFile("plateauTest3");
		String move3 = "D3-B5";
		assertFalse(p3.estValide(move3, PlateauFousFous.JNOIR));
		
		// Joueur blanc en F3 veut se déplacer en H5 (case vide), renvoie faux
		String move4 = "F3-H5";
		assertFalse(p1.estValide(move4, PlateauFousFous.JBLANC));
		
		// Tests de menace
		PlateauFousFous p4 = new PlateauFousFous();
		p4.setFromFile("plateauTest7");
		String move5 = "C2-E4";
		assertTrue(p4.estValide(move5, PlateauFousFous.JBLANC));
		String move6 = "C2-D3";
		assertFalse(p4.estValide(move6, PlateauFousFous.JBLANC));
		String move7 = "F3-E4";
		assertTrue(p4.estValide(move7, PlateauFousFous.JNOIR));
		String move8 = "F3-D5";
		assertFalse(p4.estValide(move8, PlateauFousFous.JNOIR));
		
		//Test pion blanc obligé de prendre le pion en F3 et ne peut pas menacer celui en G6
		PlateauFousFous p5 = new PlateauFousFous();
		p5.setFromFile("plateauTest8");
		String move9 = "D5-F3";
		assertTrue(p5.estValide(move9, PlateauFousFous.JBLANC));
		String move10 = "D5-F7";
		assertFalse(p5.estValide(move10, PlateauFousFous.JBLANC));
		
	}
	
	@Test
	public void testMouvementsPossibles() {
		PlateauFousFous p1 = new PlateauFousFous();
		p1.setFromFile("plateauTest2");
		List<String> actual1 = Arrays.asList(p1.mouvementsPossibles(PlateauFousFous.JBLANC));
		List<String> expected1 = Arrays.asList("F3-E4","F3-E2");
		assertThat(actual1, is(expected1));
		
		PlateauFousFous p2 = new PlateauFousFous();
		p2.setFromFile("plateauTest9");
		List<String> actual2 = Arrays.asList(p2.mouvementsPossibles(PlateauFousFous.JBLANC));
		List<String> expected2 = Arrays.asList("D5-E6","D5-F3","D5-B3","E2-F3");
		assertThat(actual2, is(expected2));
		
		PlateauFousFous p3 = new PlateauFousFous();
		p3.setFromFile("plateauTest10");
		List<String> actual3 = Arrays.asList(p3.mouvementsPossibles(PlateauFousFous.JBLANC));
		List<String> expected3 = Arrays.asList("D5-E6","D5-F3","D5-B3","E2-F3","E8-F7","E8-H5","E8-D7","E8-A4");
		assertThat(actual3, is(expected3));
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
