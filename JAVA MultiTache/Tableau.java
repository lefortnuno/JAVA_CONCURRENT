import java.util.Calendar;

public class Tableau {

	public Tableau() {}
	
	// Fonction de mise en form du rendu
	public void styliste(String css) {
		for (int i=0; i<55; i++) {
			System.out.print(css);
		}
		System.out.println("");
	}
	
	// Nom des developpeur du devoir
	public void nomAutheur() {
		String tete = "+++ >>> ";
		String queu = " <<< +++";
		String css = "+";
		
		styliste(css);
		
		System.out.println(tete+ " LEFORT Nomenjanahary Nuno Num 2132    " +queu);
		System.out.println(tete+ " RAZAFINJATOVO Nambinina Num 2108      " +queu);
		System.out.println(tete+ " RABEHARIJAONA Santatriniaina Num 2101 " +queu);
		
		styliste(css);
		System.out.println("\n");
	}
	
	// Creer le tableau
	public int[] creerTableau(int taille) throws TailleTableauException {
		
		String message = "+ >> VOICI LE TABLEAU CREER ET NON TRIER : taille=";
		String css = "+";
		
		if(taille<0)
			throw new TailleTableauException("La taille du tableau est negative ! Taille = "+String.valueOf(taille));
		else if(taille==0)
			throw new TailleTableauException("La taille du tableau est null ! Taille = "+String.valueOf(taille));
		else {
			
			int[] tab = new int[taille];
			int x = taille*21;
			
			for(int i=0; i<taille; i++) 
				tab[i] = ((int)(Math.random()*x))+1; //Générer un nombre aléatoire entre 1 et tailleTableau
			
			styliste(css);
			System.out.println(message+taille);
			styliste(css);
			
			this.afficherTableau(tab);
			System.out.println("\n");
			return tab;
		}
	}
	
	// Afficher le contenu du tableau
	private void afficherTableau(int[] tab){
        for(int elemement: tab){	
            System.out.print("|" + elemement);
        }
        System.out.println("| \n");
    }
	
	// Fusionner 02 tableau
	private int[] fusionnerTableau(int[] tab1, int[] tab2){
		
        int taille1 = tab1.length, taille2 = tab2.length;
        
        int[] tab = new int[taille1+taille2];
        
        int i=0, j=0, k=0; //(i, j, k) indice respectifs de (tab1, tab2, tab)
        while(i<taille1 && j<taille2){
            if(tab1[i] < tab2[j])
                tab[k++] = tab1[i++];
            else
                tab[k++] = tab2[j++];
        }
        
        /*
            On sort de la boucle que si un des deux tableaux (tab1 ou tab2) ai ete parcourru en entier.
            Il ne nous reste plus qu'à parcourrir le tableau qui reste.
        */
        if(i == taille1){ //il ne reste plus qu'a parcourrir tab2
            while(j < taille2)
                tab[k++] = tab2[j++];
        }else{ //il ne reste plus qu'a parcourrir tab1
            while(i<taille1)
                tab[k++] = tab1[i++];
        }
        
        return tab;
    }

	// Voir le temps que ca va mettre pour compiler
	private long timeInMillis(){
        Calendar cal=Calendar.getInstance();
        
        return cal.getTimeInMillis();
    }
	

	//****************************TRI SEQUENTIEL SANS PARALLELISME ET MULTITACHES*************************
	public int[] trierTableauSeq(int[] tab){
        if(tab.length==1)
            return tab;
        else
        	for(int i=0; i<tab.length; i++) 
        		for(int j=i+1; j<tab.length; j++)
        			if(tab[i] > tab[j]) {
        				int c = tab[i];
        				tab[i] = tab[j];
        				tab[j] = c;
        			}
        
        return tab;
    }
	
	public void methodeSequenctiel(int[] tab) {
		String message = "+ >> TABLEAU TRIER : METHODE \" TACHES SEQUENTIELS \" ";
		String messageT = "+ >> DUREE METHODE \" TACHES SEQUENTIELS \" : ";
		String css = "+";
		
		long debut=this.timeInMillis();
		int[] tabTrie=this.trierTableauSeq(tab);
		long fin=this.timeInMillis();
		
		styliste(css);
		System.out.println(message);
		styliste(css);
		
		this.afficherTableau(tabTrie);
		System.out.println(messageT+(fin-debut)+" millisecondes environ.\n\n\n");
		
	}
	
	
	//****************************TRI-FUSION PAR MULTITACHES EN PARALLELISME*************************
	public int[] triAvecMultiProcesseur(int[] tab){
        if(tab.length==1)
            return tab;
        else{
            int taille=tab.length;
            int[] sousTab1, sousTab2=new int[taille/2];
            
            if(taille%2==0)
                sousTab1=new int[taille/2];
            else
                sousTab1=new int[(taille/2)+1];
            
            for(int i=0, j=0; i<taille; i++){
                if(i<sousTab1.length)
                    sousTab1[i]=tab[i];//Remplir le sousTab1
                else
                    sousTab2[j++]=tab[i];//Remplir le sousTab2
            }
            
            return this.fusionnerTableau(triAvecMultiProcesseur(sousTab1), triAvecMultiProcesseur(sousTab2));
        }
    }

	public void methodeMultiProcesseur(int[] tab) {
		
		String message = "+ >> TABLEAU TRIER : METHODE \" TACHES EN PARALLELISME \" ";
		String messageTemps = "+ >> DUREE METHODE \" TACHES EN PARALLELISME \" : ";
		String css = "+";
		
		long debut=this.timeInMillis();	
		int[] tabTrie=this.triAvecMultiProcesseur(tab);
		long fin=this.timeInMillis();
		
		styliste(css);
		System.out.println(message);
		styliste(css);
		
		this.afficherTableau(tabTrie);
		System.out.println( messageTemps +(fin-debut)+" millisecondes environ.\n");
	}
}
