package br.com.clayton.cm;

import br.com.clayton.cm.modelo.Tabuleiro;
import br.com.clayton.cm.visao.TabuleiroConsole;

public class Aplicacao {
	
	public static void main(String[] args) {
		
		Tabuleiro tabuleiro = new Tabuleiro(6, 6, 6);			
		new TabuleiroConsole(tabuleiro);
	}

}

