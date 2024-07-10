import java.util.Calendar;
public class Tableau {
	public Tableau() {}
	
	
	
	public int[] creerTableau(int taille)
		throws TailleTableauException{
		if(taille<0)
			throw new TailleTableauException("La taille du tableau est negative ! Taille = "+String.valueOf(taille));
		else if(taille==0)
			throw new TailleTableauException("La taille du tableau est null ! Taille = "+String.valueOf(taille));
		else {
			int[] tab=new int[taille];
			for(int i=0; i<taille; i++)
				tab[i]=((int)(Math.random()*1500))+1;//Générer un nombre aléatoire entre 1 et 1500
			
			System.out.println("Voici le tableau cree :");
			this.afficherTableau(tab);
			return tab;
		}
	}
	
	private void afficherTableau(int[] tab){
        int i=1;
        for(int elem: tab){
            System.out.print(elem+" ");
            i++;
            if(i==26){//On affiche 25 elements du tableau par ligne
                System.out.println("");
                i=1;
            }
        }
        System.out.println("\n");
    }
	
	private int[] fusionnerTableau(int[] tab1, int[] tab2){
        int taille1=tab1.length, taille2=tab2.length;
        int[] tab=new int[taille1+taille2];
        
        int i=0, j=0, k=0;//(i, j, k) indice respectifs de (tab1, tab2, tab)
        while(i<taille1 && j<taille2){
            if(tab1[i]<tab2[j])
                tab[k++]=tab1[i++];
            else
                tab[k++]=tab2[j++];
        }
        /*
            On sort de la boucle que si un des deux tableaux (tab1 ou tab2) soit parcourru en entier.
            Il ne nous reste plus qu'à parcourrir le tableau qui reste.
        */
        
        if(i==taille1){//tab2 reste à parcourrir
            while(j<taille2)
                tab[k++]=tab2[j++];
        }else{//tab1 reste à parcourrir
            while(i<taille1)
                tab[k++]=tab1[i++];
        }
        
        return tab;
    }
	
	private long timeInMillis(){
        Calendar cal=Calendar.getInstance();
        
        return cal.getTimeInMillis();
    }
	
	//****************************TRI-FUSION SANS THREAD*************************
	
	public int[] trierTableau(int[] tab){
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
            
            return this.fusionnerTableau(trierTableau(sousTab1), trierTableau(sousTab2));
        }
    }
	
	public void triFusionSansThread(int[] tab) {
		long debut=this.timeInMillis();
		
		int[] tabTrie=this.trierTableau(tab);
		
		long fin=this.timeInMillis();
		System.out.println("Voici le tableau trie avec la methode sans Thread : ");
		this.afficherTableau(tabTrie);
		System.out.println("Le tri-fusion sans Thread a duree "+(fin-debut)+" millisecondes environ.\n");
		
	}
	
	//****************************TRI-FUSION AVEC THREAD*************************
	
	private int[] trierTableauThread(int[] tab) {
		
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
            
            TableauThread thread1=new TableauThread(sousTab1);
            TableauThread thread2=new TableauThread(sousTab2);
            thread1.start();
            thread2.start();
            
            try {
            	/*
            		On force le Thread principal à attendre que thread1 et thread2
            		aient fini leur traitement respectif avant de poursuivre son excecution
            	*/
            	
	            thread1.join();
	            thread2.join();
            }catch(InterruptedException e){
            	e.printStackTrace();
            }
            
            return this.fusionnerTableau(thread1.getTabTrie(), thread2.getTabTrie());
        }
		
		
	}
	
	public void triFusionAvecThread(int[] tab) {
		long debut=this.timeInMillis();
		
		int[] tabTrie=this.trierTableauThread(tab);
		
		long fin=this.timeInMillis();
		System.out.println("Voici le tableau trie avec la methode dotee de Thread : ");
		this.afficherTableau(tabTrie);
		System.out.println("Le tri-fusion avec Thread a duree "+(fin-debut)+" millisecondes environ.\n");
	}
}
