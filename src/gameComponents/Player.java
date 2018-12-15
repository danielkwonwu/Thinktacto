package gameComponents;

import java.util.Collections;
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
	
	public boolean addSlot(Slot slot) {
		if (!slot.taken) {
			this.possessedSlots.add(slot);
			slot.setTaken(true);
			this.win = checkWin();
			System.out.println("Player " + this.name + " took Slot " + slot.slotPosition + ".");
			return true;
		}
		else {
			return false;
		}
	}
	
	public void printSlots() {
		for (Slot slot : this.possessedSlots) {
		System.out.println(slot.slotPosition + " " + slot.taken);
		}
	}
	
	public Set<Set<Integer>> getWinset (){
		Set <Set<Integer>> winset = new HashSet<Set<Integer>>();
		Set <Integer> winset1 = new HashSet<Integer>(); winset1.add(1); winset1.add(2); winset1.add(3);
		Set <Integer> winset2 = new HashSet<Integer>(); winset2.add(4); winset2.add(5); winset2.add(6);
		Set <Integer> winset3 = new HashSet<Integer>(); winset3.add(7); winset3.add(8); winset3.add(9);
		Set <Integer> winset4 = new HashSet<Integer>(); winset4.add(1); winset4.add(4); winset4.add(7);
		Set <Integer> winset5 = new HashSet<Integer>(); winset5.add(2); winset5.add(5); winset5.add(8);
		Set <Integer> winset6 = new HashSet<Integer>(); winset6.add(3); winset6.add(6); winset6.add(9);
		Set <Integer> winset7 = new HashSet<Integer>(); winset7.add(1); winset7.add(5); winset7.add(9);
		Set <Integer> winset8 = new HashSet<Integer>(); winset8.add(3); winset8.add(5); winset8.add(7);
		winset.add(winset1);winset.add(winset2);winset.add(winset3);winset.add(winset4);winset.add(winset5);winset.add(winset6);winset.add(winset7);winset.add(winset8);
		return winset;
	}
	
	public boolean checkWin() {
		boolean flag = false;
		Set <Set<Integer>> winset = getWinset();
		
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
		Set <Set<Integer>> winsets = getWinset();
		if (this.possessedSlots.size() == 0) { 				//initial move
			int[] appInWinset = {0,3,2,3,2,4,2,3,2,3};
			int max = getMax(appInWinset);
			for (int i = 0; i < max; i++) {
				for (Integer a: randomMix(PointersByValue(max - i, appInWinset))){
					prior.add(a);
				}
			}
		}
		if (this.possessedSlots.size() == 1) {
			int max = getMax(this.possessedSlots.get(0).slotRelations());
			for (int i = 0; i < max; i++) {
				for (Integer a: randomMix(PointersByValue(max - i, this.possessedSlots.get(0).slotRelations())))
					prior.add(a);
				}
		}
		if (this.possessedSlots.size() >= 2) {
			List<Integer> aligned = new LinkedList<Integer>(); 
			List<Integer> superaligned = new LinkedList<Integer>();
			for (Slot a : this.possessedSlots) {
				for (Slot b: this.possessedSlots) {
					if (!a.equals(b) && isAligned(a,b)) {
						for (Set<Integer> p : winsets) {
							if (p.contains(a.slotPosition) && p.contains(b.slotPosition)) {
								p.remove(a.slotPosition); p.remove(b.slotPosition);
								for(Integer q: p) {
									if (aligned.contains(q)) {
										superaligned.add(q);
									}
									else{
										aligned.add(q);
									}
								}
							}
						}
					}
				}
			}
			prior.addAll(randomMix(superaligned));
			prior.addAll(randomMix(aligned));
		}
		return prior;
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
	
	public static List<Integer> randomMix(List<Integer> addThis) {
		List<Integer> mixer = new LinkedList<Integer>();
		int initSize = addThis.size();
		for (int i = 0; i < initSize; i++) {
			double rand1 = Math.random()*addThis.size();
			mixer.add(addThis.get((int)rand1));
			addThis.remove((int)rand1);
		}
		return mixer;
	}
	
	public static boolean isAligned (Slot a, Slot b) {
		boolean flag = false;
		Set<Integer> setSlots = new HashSet<Integer>();
		for (int i = 1; i <= 9; i++) {
			setSlots.add(i);
		}
		if (setSlots.contains(a.slotPosition) && setSlots.contains(b.slotPosition)) {
			if (a.slotPosition == 1) {
				if(b.slotPosition == 6 || b.slotPosition == 8) {
				}
				else {
					flag = true;
				}
			}
			if (a.slotPosition == 2) {
				if(b.slotPosition == 4 || b.slotPosition == 6 || b.slotPosition == 7 || b.slotPosition == 9) {
				}
				else {
					flag = true;
				}
			}
			if (a.slotPosition == 3) {
				if(b.slotPosition == 4 || b.slotPosition == 8) {
				}
				else {
					flag = true;
				}
			}
			if (a.slotPosition == 4) {
				if(b.slotPosition == 2 || b.slotPosition == 3 || b.slotPosition == 8 || b.slotPosition == 9) {
				}
				else {
					flag = true;
				}
			}
			if (a.slotPosition == 5) {
					flag = true;
			}
			if (a.slotPosition == 6) {
				if(b.slotPosition == 1 || b.slotPosition == 2 || b.slotPosition == 7 || b.slotPosition == 8) {
				}
				else {
					flag = true;
				}
			}
			if (a.slotPosition == 7) {
				if(b.slotPosition == 2 || b.slotPosition == 6) {
				}
				else {
					flag = true;
				}
			}
			if (a.slotPosition == 8) {
				if(b.slotPosition == 1 || b.slotPosition == 4 || b.slotPosition == 3 || b.slotPosition == 6) {
				}
				else {
					flag = true;
				}
			}
			if (a.slotPosition == 9) {
				if(b.slotPosition == 2 || b.slotPosition == 4) {
				}
				else {
					flag = true;
				}
			}
		}
		return flag;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Player k = new Player("k",false);
		k.addSlot(new Slot(5,false));
		k.addSlot(new Slot(1,false));
		k.addSlot(new Slot(7,false));
		k.addSlot(new Slot(8,false));
		System.out.println(k.SlotPriorities());
		
		//k.addSlot(new Slot(1,false));
		//k.addSlot(new Slot(3,false));
		//k.rateSlots(k.possessedSlots);


		//k.addSlot(new Slot(1,false));
		//k.printSlots();
		
		
	}

}
