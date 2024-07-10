
public class Test {

	public static void main(String[] args) {
		Tableau maClasse=new Tableau();
		
		try{
			int[] tableau=maClasse.creerTableau(500);
			maClasse.triFusionSansThread(tableau);
			maClasse.triFusionAvecThread(tableau);
		}catch(TailleTableauException e){
			e.printStackTrace();
		}
	}
}
