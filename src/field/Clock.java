package field;

import auxClasses.Chain;
import auxClasses.OutOfBoundsException;
import basicItems.Card;

public class Clock {
	private Card[] clockInUse;
	private Chain<Card> buffer;
	private Chain<Card> over7;

	public Clock() {
		clockInUse = new Card[7];
		buffer = new Chain<Card>();
		over7 = new Chain<Card>();
	}

	public int cardsInClock() {
		int i = 0;
		boolean stop = false;
		for (; i < clockInUse.length && !stop;) {
			stop = clockInUse[i] == null;
			if (!stop)
				i++;
		}
		return i;
	}

	public void bufferDamage(Card card) {
		buffer.add(card);
	}

	public void clearBuffer() {
		buffer.setHead(null);
	}

	public Card unbuffer() throws OutOfBoundsException {
		Card card = buffer.getData(1);
		buffer.remove(1);
		return card;
	}

	public void transferBuffer() throws OutOfBoundsException {
		int fixIndex = buffer.getIndex();
		for (int i = 1; i <= fixIndex && i < 7; i++) {
			clockInUse[i - 1] = this.unbuffer();
		}
		if (buffer.getHead() != null) {
			fixIndex = buffer.getIndex();
			for (int i = 1; i <= fixIndex; i++) {
				over7.add(this.unbuffer());
			}
		}
	}

	public void clearClock() {
		clockInUse = new Card[7];
	}

	public Card heal(int i) { // 0<=i<=6
		Card card = clockInUse[i];
		for (; i < clockInUse.length - 1; i++) {
			clockInUse[i] = clockInUse[i + 1];
		}
		clockInUse[6] = null;
		return card;
	}

	public void levelUp() throws OutOfBoundsException {
		clearClock();
		for (int i = 1; i < over7.getIndex(); i++) {
			bufferDamage(over7.getData(i));
		}
		over7.setHead(null);
		transferBuffer();
	}

	public Card cardFromClock(int i) {// 0<=i<=6
		return clockInUse[i];
	}
}