// MultiThreading
public class TableauSurMultiProceseur extends Thread{
	
	// Declartion de 2 tableau
	private int[] tabNonTrie, tabTrie;
	
	// Declaration d'une classe classeTableau
	private Tableau classeTableau = new Tableau();
	
	public TableauSurMultiProceseur (int[] tab) {	
		this.tabNonTrie=tab;
	}
	
	// Getter
	public int[] getTabTrie() {	
		return this.tabTrie;
	}

	// MultiTreding, run(), join(), start(), ..... et execution du fonction de trie de tableu en parallele
	public void run() {
		this.tabTrie = classeTableau.triAvecMultiProcesseur(this.tabNonTrie);
	}
}
