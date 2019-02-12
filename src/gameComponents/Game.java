package gameComponents;

import java.awt.Color;
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
		this.visSetup();
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
		StdDraw.pause(500);
		System.out.println("Player " + a.name +"'s turn.");
		List<Integer> prior = a.SlotPriorities();
		for (Integer i : prior) {
			if (checkAvail(i)) {
				System.out.println("Player " + a.name + " takes Slot " + i + ".");
				addSymbol(a,i);
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
	
	public void visSetup() {
		StdDraw.setCanvasSize(800,800);
		StdDraw.setXscale(0,4);
		StdDraw.setYscale(0,4);
		StdDraw.setPenRadius(0.01);
		StdDraw.line(0.5,0.5,0.5,3.5);
		StdDraw.line(0.5,1.5,3.5,1.5);
		StdDraw.line(0.5,2.5,3.5,2.5);
		StdDraw.line(0.5,3.5,3.5,3.5);
		StdDraw.line(0.5,0.5,3.5,0.5);
		StdDraw.line(3.5,0.5,3.5,3.5);
		StdDraw.line(1.5,0.5,1.5,3.5);
		StdDraw.line(2.5,0.5,2.5,3.5);
	}
	
	public void addSymbol(Player player, int input) {
		int x = 0;
		int y = 0;
		System.out.println("input??? " + input);
		switch (input) {
		case 1: 
			x = 1; y = 3;
			break;
		case 2:
			x = 2; y = 3;
			break;
		case 3:
			x = 3; y = 3;
			break;
		case 4: 
			x = 1; y = 2;
			break;
		case 5:
			x = 2; y = 2;
			break;
		case 6:
			x = 3; y = 2;
			break;
		case 7: 
			x = 1; y = 1;
			break;
		case 8:
			x = 2; y = 1;
			break;
		case 9:
			x = 3; y = 1;
			break;
		}
		if (player.first) {
			xSymbol(x,y);
		}
		else {
			oSymbol(x,y);
		}
	}
	
	public void xSymbol(int x, int y) {
		StdDraw.setPenColor(Color.BLUE);
		StdDraw.line(x-0.3, y-0.3,x+0.3,y+0.3);
		StdDraw.line(x+0.3, y-0.3,x-0.3,y+0.3);
		StdDraw.setPenColor();
	}
	
	public void oSymbol(int x, int y) {
		StdDraw.setPenColor(Color.RED);
		StdDraw.circle(x,y,0.35);
		StdDraw.setPenColor();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Player a = new Player("a");
		Player b = new Player("b");
		Game k = new Game (a,b);
		k.startGame();
	}

}
