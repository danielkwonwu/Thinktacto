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
	
	public Game (Player a, Player b) {
		this.a = a;
		this.b = b;
		this.history = new LinkedList<Slot>();
		this.availableSlots = initialSlots();
	}
	
	public Set<Slot> initialSlots() {
		Set<Slot> init = new HashSet<Slot>();
		for (int i = 1; i <= 9; i++) {
			Slot n = new Slot(i, false);
			init.add(n);
		}
		return init;
	}
	
	public Player firstToGo() {
		double chance = Math.random();
		System.out.println(chance);
		if (chance > 0.5) {
			return this.a;
		}
		else {
			return this.b;
		}
	}
	
	public void startGame() {
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Player a = new Player("a",false);
		Player b = new Player("b",false);
		Game k = new Game (a,b);
		k.firstToGo();
	}

}
