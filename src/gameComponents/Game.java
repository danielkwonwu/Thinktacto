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
			this.a.first = true;
			return true;
		}
		else {
			this.b.first = true;
			return false;
		}
	}
	
	public void startGame() {
		while(!a.win && !b.win && this.availableSlots.size() > 0) {
			if (this.aFirst) {
				AddByPlayerPrior(a);
				this.exchangeSlotInfo();
				if(a.checkWin() || b.checkWin()) break;
				AddByPlayerPrior(b);
				this.exchangeSlotInfo();
			}
			else {
				AddByPlayerPrior(b);
				this.exchangeSlotInfo();
				if(a.checkWin() || b.checkWin()) break;
				AddByPlayerPrior(a);
				this.exchangeSlotInfo();
			}
		}
		if(this.availableSlots.size() == 0) {
			System.out.println("Draw");
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
		System.out.println("Player " + a.name +"'s turn.");
		List<Integer> prior = a.SlotPriorities();
		for (Integer i : prior) {
			if (checkAvail(i)) {
				System.out.println("Player " + a.name + " takes Slot " + i + ".");
				Slot mov = getSlotByInt(i);
				this.history.add(mov);
				printHistory();
				a.addSlot(mov);
				this.availableSlots.remove(getSlotByInt(i));
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
	
	public void printHistory() {
		String hist = "[ ";
		for (Slot s: this.history) {
			hist = hist + s.slotPosition + ", "; 
		}
		hist = hist + "]";
		System.out.println(hist);
	}
	
	public void exchangeSlotInfo() {
		this.a.opponentSlots = this.b.possessedSlots;
		this.b.opponentSlots = this.a.possessedSlots;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Player a = new Player("a",false);
		Player b = new Player("b",false);
		Game k = new Game (a,b);
		k.startGame();
	}

}
