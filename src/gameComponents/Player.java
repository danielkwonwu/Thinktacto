package gameComponents;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Player {
	
	String name;
	boolean auto;
	boolean win;
	LinkedList<Slot> possessedSlots;
	LinkedList<Slot> opponentSlots;
	
	public Player (String name, boolean auto) {
		this.name = name;
		this.auto = auto;
		this.win = false;
		this.possessedSlots = new LinkedList<Slot>();
		this.opponentSlots = new LinkedList<Slot>();
	}
	
	public void addSlot(Slot slot) {
		if (!slot.taken) {
		this.possessedSlots.add(slot);
		slot.setTaken(true);
		this.win = checkWin();
		System.out.println("Player " + this.name + " took Slot " + slot.slotPosition + ".");
		}
	}
	
	public void printSlots() {
		for (Slot slot : this.possessedSlots) {
		System.out.println(slot.slotPosition + " " + slot.taken);
		}
	}
	
	public boolean checkWin() {
		boolean flag = false;
		Set <Set<Integer>> winset = new HashSet<Set<Integer>>();
		Set <Integer> winset1 = new HashSet<Integer>(); winset1.add(1); winset1.add(2); winset1.add(3);
		Set <Integer> winset2 = new HashSet<Integer>(); winset2.add(4); winset2.add(5); winset2.add(6);
		Set <Integer> winset3 = new HashSet<Integer>(); winset3.add(7); winset3.add(8); winset3.add(9);
		Set <Integer> winset4 = new HashSet<Integer>(); winset4.add(1); winset4.add(4); winset4.add(7);
		Set <Integer> winset5 = new HashSet<Integer>(); winset5.add(2); winset5.add(5); winset5.add(8);
		Set <Integer> winset6 = new HashSet<Integer>(); winset6.add(3); winset6.add(6); winset6.add(9);
		Set <Integer> winset7 = new HashSet<Integer>(); winset7.add(1); winset7.add(5); winset7.add(9);
		Set <Integer> winset8 = new HashSet<Integer>(); winset8.add(3); winset8.add(5); winset7.add(7);
		winset.add(winset1);winset.add(winset2);winset.add(winset3);winset.add(winset4);winset.add(winset5);winset.add(winset6);winset.add(winset7);winset.add(winset8);
		
		if (this.possessedSlots.size() >= 3) {
			Set <Integer> playerSet = new HashSet<Integer>();
			for (Slot slot : this.possessedSlots) {
				playerSet.add(slot.slotPosition);
				if (playerSet.size() == 3) {
					for (Set <Integer> wins : winset) {
						if (playerSet.equals(wins)) {
							flag = true;
							break;
						}
					}
				}
				else {
					Set <Set<Integer>> playerSubsets = new HashSet<Set<Integer>>();
					for (Integer a : playerSet) {
						for (Integer b : playerSet) {
							for (Integer c: playerSet) {
								Set <Integer> pset = new HashSet<Integer>();
								pset.add(a); pset.add(b); pset.add(c);
								playerSubsets.add(pset);
							}
						}
					}
					for (Set<Integer> playerSubset : playerSubsets) {
						for (Set<Integer> wins : winset) {
							if (playerSubset.equals(wins)) {
								flag = true;
								break;
							}
						}
					}
				}
			}
		}
		if (flag) {
			System.out.println("Player has won.");
		}
		return flag;
	}
	public List<Integer> SlotPriorities() {
		List<Integer> prior = new LinkedList<Integer>();
		if (this.possessedSlots.size() == 0) { 				//initial move
			int[] appInWinset = {0,3,2,3,2,4,2,3,2,3};
			int max = getMax(appInWinset);
			prior.addAll(PointersByValue(max, appInWinset));
		}
		if (this.possessedSlots.size() == 1) {
			
		}
		return prior;
	}
	
	
	public int selectSlot() {
		if (this.possessedSlots.size() == 0 && this.opponentSlots.size() == 0) { 				//initial move
			int max = 0;
			int slotNumber = 0;
			for (int i = 1; i <= 9; i++) {
				Slot checker = new Slot (i, false);
				int [] k = checker.slotRelations();
				for (int q = 1; q <= 9; q++) {
					if (max < k[q]) {
						max = k[q];
						slotNumber = q;
					}
				}
			}
			System.out.println("Max val: " + max);
			return slotNumber;
		}
		else {																					//mid-game move
			int[] myRatings = rateSlots(this.possessedSlots);
			int[] opponentRatings = rateSlots(this.opponentSlots);
			return 0;
		}
	}
	
	public int[] rateSlots(List<Slot> input) {
		int[] ratings = new int[10];
		for (Slot each: input) {
			for (int k = 0; k < 10; k++) {
				if (k != each.slotPosition) {
					ratings[k] = ratings[k] + each.slotRelations()[k];
				}
			}
		}
		for (int i = 0; i < 10; i++) {
			System.out.println(ratings[i]);
		}
		return ratings;
	}
	
	public List<Integer> PointersByValue (int val, int[] values){
		List<Integer> ans = new LinkedList<Integer>();
		int max = 0;
		for (int i = 0; i < values.length; i++) {
			if (values[i] == val) {
				ans.add(i);
			}
		}
		return ans;
	}
	
	public int getMax(int[] values) {
		int max = 0;
		for (int i = 0; i < values.length; i++) {
			if (max < values[i]) {
				max = values[i];
			}
		}
		return max;
	}
	
	public void mixAndAdd(List<Integer> addTo, List<Integer> addThis) {
		List<Integer> mixer = new LinkedList<Integer>();
		
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Player k = new Player("k",false);
		k.addSlot(new Slot(5,false));
		k.addSlot(new Slot(1,false));
		//k.addSlot(new Slot(3,false));
		k.rateSlots(k.possessedSlots);


		//k.addSlot(new Slot(1,false));
		//k.printSlots();
		
	}

}
