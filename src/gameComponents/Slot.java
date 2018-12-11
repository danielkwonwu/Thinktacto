package gameComponents;


public class Slot {

	boolean taken;
	int slotPosition;
	
	public Slot (int slotPosition, boolean taken) {
		this.slotPosition = slotPosition;
		this.taken = taken;
	}
	
	
	public boolean isTaken() {
		return taken;
	}



	public void setTaken(boolean taken) {
		this.taken = taken;
	}



	public int getSlotPosition() {
		return slotPosition;
	}



	public void setSlotPosition(int slotPosition) {
		this.slotPosition = slotPosition;
	}


	
	public int[][] getWinSet() {
		int[][] winset = {{1,2,3},{4,5,6},{7,8,9},{1,4,7},{2,5,8},{3,6,9},{1,5,9},{3,5,7}};
		return winset;
	}
	
	/** slotRelations() rates other slots in respect to the current slot
	 * 
	 * @return
	 */
	public int[] slotRelations() {
		int [] ratings = new int[10];
		ratings[0] = 0;
		for (int i = 1; i < ratings.length; i++) {
			int count = 0;
			for (int p = 0; p < this.getWinSet().length; p++) {
				boolean senseCurrentSlot = false;
				boolean senseRespectiveSlot = false;
				for (int q = 0; q < this.getWinSet()[p].length; q++) {
					if (this.getWinSet()[p][q] == this.slotPosition) {
						senseCurrentSlot = true;
					}
				}
				for (int r = 0; r < this.getWinSet()[p].length; r++) {
					if (this.getWinSet()[p][r] == i) {
						senseRespectiveSlot = true;
					}
				}
				if (senseCurrentSlot && senseRespectiveSlot) {
					count++;
				}
			}
			ratings[i] = count;
		}
		return ratings;
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Slot k = new Slot(5, false);
		int[] p = k.slotRelations();
		for (int i = 0; i < p.length; i++) {
			System.out.println(p[i]);
		}
		k.getWinSet();
	}

}
