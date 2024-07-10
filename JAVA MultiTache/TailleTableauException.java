// Generer une erreur si la taille du tableau n'est pas un entier strictement positif.
public class TailleTableauException extends Exception {
	
	public TailleTableauException(String str) {
		
		super(str);
	}
}
