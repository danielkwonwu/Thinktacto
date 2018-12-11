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
		while (!this.a.win && !this.b.win && this.availableSlots.size() != 0){
			if (this.aFirst) {
				this.a.addSlot(getSlotByInt(this.a.selectSlot()));
				this.b.addSlot(getSlotByInt(this.b.selectSlot()));
			}
			else {
				this.b.addSlot(getSlotByInt(this.b.selectSlot()));
				this.a.addSlot(getSlotByInt(this.a.selectSlot()));
			}
		}
	}
	
	public Slot getSlotByInt(int i) {
		Slot ans = new Slot(0,false);
		for (Slot slot: this.availableSlots) {
			if (slot.slotPosition == i) {
				ans = slot;
			}
		}
		return ans;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Player a = new Player("a",false);
		Player b = new Player("b",false);
		Game k = new Game (a,b);
	}

}
