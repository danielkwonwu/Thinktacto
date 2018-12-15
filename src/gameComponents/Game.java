package gameComponents;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Game {
	
		Player a;
		Player b;
		List<Slot> history;
		Set<Slot> availableSlots;
		boolean aFirst;
		
	
	public Game (Player a, Player b) {
		this.a = a;
		this.b = b;
		this.history = new LinkedList<Slot>();
		this.availableSlots = initialSlots();
		this.aFirst = AGoesFirst();
	}
	
	public Set<Slot> initialSlots() {
		Set<Slot> init = new HashSet<Slot>();
		for (int i = 1; i <= 9; i++) {
			Slot n = new Slot(i, false);
			init.add(n);
		}
		return init;
	}
	
	public boolean AGoesFirst() {
		double chance = Math.random();
		if (chance > 0.5) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void startGame() {
			if (this.aFirst) {
				System.out.println("a first");
				AddByPlayerPrior(a);
				AddByPlayerPrior(b);
			}
			else {
				System.out.println("b first");
				AddByPlayerPrior(b);
				AddByPlayerPrior(a);
			}
		}
	
	public Slot getSlotByInt(int i) {
		Slot nul = new Slot(0,false);
		for (Slot slot: this.availableSlots) {
			if (slot.slotPosition == i) {
				return slot;
			}
		}
		return nul;
	}
	
	public void AddByPlayerPrior(Player a) {
		List<Integer> prior = a.SlotPriorities();
		System.out.println(a.name + " Player priority" + prior);
		for (Integer i : prior) {
			if (checkAvail(i)) {
				a.addSlot(getSlotByInt(i));
				System.out.println("Player " + a.name + " takes Slot " + i + ".");
				this.availableSlots.remove(getSlotByInt(i));
				System.out.println("size of available slots " + this.availableSlots.size());
				break;
			}
		}
	}
	
	public boolean checkAvail (int i) {
		boolean flag = false;
		for (Slot s : this.availableSlots) {
			if (s.slotPosition == i) {
				flag = true;
			}
		}
		return flag;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Player a = new Player("a",false);
		Player b = new Player("b",false);
		Game k = new Game (a,b);
		k.startGame();
		k.startGame();
		k.startGame();
		k.startGame();
	}

}
