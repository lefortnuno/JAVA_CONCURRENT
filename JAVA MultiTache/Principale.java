/**
 * 
 */
/**
 * @author -GROUPE_LOST-
	* LEFORT Nomenjanahary Nuno Num 2132
	* RAZAFINJATOVO Nambinina Num 2108
	* RABEHARIJAONA Santatriniaina Num 2101
 *
 */
public class Principale {
	
	public static void main (String[] args) {

		int taille = ((int)(Math.random()*5000))+1;
		String messageTaille = "+++ >>> taille tableau = "+ taille + " <<< +++ \n\n";
		Tableau maTabClasse = new Tableau();
		
		try {
			
			// Print Nom developpeur du projet
			maTabClasse.nomAutheur();
			
			int[] tableau = maTabClasse.creerTableau(taille);
			
			// Methode de trie sequentiel
			maTabClasse.methodeSequenctiel(tableau);
			
			// Methode de trie parallelisme 
			maTabClasse.methodeMultiProcesseur(tableau);
			
			//La taille du tableau est aleatoire, on l'affiche a l'utilisateur ici.
			System.out.println( messageTaille );
			
		} catch (TailleTableauException e) {
			e.printStackTrace();
		}
	}
}
