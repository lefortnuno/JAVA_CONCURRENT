
class TableauThread extends Thread{
		private int[] tabNonTrie, tabTrie;
		private Tableau classeTableau=new Tableau();
		
		public TableauThread(int[] tab) {
			this.tabNonTrie=tab;
		}
		
		public int[] getTabTrie() {
			return this.tabTrie;
		}
		
		public void run() {
			this.tabTrie=classeTableau.trierTableau(this.tabNonTrie);
		}
	}