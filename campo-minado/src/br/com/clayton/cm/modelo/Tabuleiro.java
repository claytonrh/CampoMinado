package br.com.clayton.cm.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import br.com.clayton.cm.excecao.ExplosionException;	

public class Tabuleiro {
	
	private int linhas; // REVISAO
	private int colunas;
	private int minas;
	
	private final List<Campo> campos = new ArrayList<>(); // REVISAO
	
	public Tabuleiro(int linhas, int colunas, int minas) {  // REVISAO
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;
		
		gerarCampos();
		associarVizinhos();	
		sortearMinas();		
	}
	
	public void abrir(int linha, int coluna) { // REVISAO
		try {
			campos.parallelStream()	
			.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
			.findFirst()	
			.ifPresent(c -> c.abrir());			
		} catch (ExplosionException e) {
			campos.forEach(c -> c.setAberto(true));
			throw e;
		}
		
	}
	public void alternarMarcacao(int linha, int coluna) { // REVISAO
		campos.parallelStream()	
		.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
		.findFirst()	
		.ifPresent(c -> c.alternarMarcacao());
	}

	private void gerarCampos() {	// REVISAO
		for (int linha = 0; linha <linhas; linha++) {
			for (int coluna = 0; coluna < colunas; coluna++) {
				campos.add(new Campo(linha, coluna));
			}			
		}  
	}

	private void associarVizinhos() {  // REVISADO
		for(Campo c1: campos) {
			for(Campo c2: campos) {
				c1.adicionarVizinho(c2);
			}
		}
		
	}

	private void sortearMinas() {  //  REVISADO
		long minasArmadas = 0;
		Predicate<Campo> minado = c -> c.isMinado();
		do {
			int aleatorio = (int) (Math.random() * campos.size());
			campos.get(aleatorio).minar();
			minasArmadas = campos.stream().filter(minado).count();			
			
		}while(minasArmadas < minas);
		
	}
	
	public boolean objetivoAlcançado() {  // REVISADO
			return campos.stream().allMatch(c -> c.objetivoAlcançado()); 
			
	}	
	
	public void reiniciar() {  // REVISAO
		campos.stream()	.forEach(c -> c.reiniciar());
		sortearMinas();		
	}
	
	public String toString () { // REVISAO
		StringBuilder sb = new StringBuilder(); 
	// utilizado quando tiver um numero muito grande de concatenações na String
		
		sb.append("  ");
		for (int c = 0; c <colunas; c++) {
			sb.append(" ");
			sb.append(c);
			sb.append(" ");					
		}
		sb.append("\n");		
		
		int i = 0;
		for (int l = 0; l < linhas; l++) {
			sb.append(l);			
			sb.append(" ");			
			for (int c =0; c < colunas; c++) {
				sb.append(" ");
				sb.append(campos.get(i));
				sb.append(" ");
				i++;
			}
			sb.append("\n");
		}
		return sb.toString ();
	}
	
}
