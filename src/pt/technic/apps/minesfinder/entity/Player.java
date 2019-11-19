package pt.technic.apps.minesfinder.entity;

import java.io.Serializable;
import java.util.ArrayList;

import pt.technic.apps.minesfinder.util.PlayerListener;

/**
 *
 * @author Gabriel Massadas
 */
public class Player implements Serializable {

	private String name;
	private long score;

	private transient ArrayList<PlayerListener> listeners;

	public Player() {
		name = "Anonymous";
		score = -1;
		listeners = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public long getScore() {
		return score;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setScore(long score) {
		this.score = score;
	}

	public void setRecord(String name, long score) {
		this.name = name;
		this.score = score;
		notifyRecordTableUpdated();
	}

	public void addRecordTableListener(PlayerListener list) {
		if (listeners == null) {
			listeners = new ArrayList<>();
		}
		listeners.add(list);
	}

	public void removeRecordTableListener(PlayerListener list) {
		if (listeners != null) {
			listeners.remove(list);
		}
	}

	private void notifyRecordTableUpdated() {
		if (listeners != null) {
			for (PlayerListener list : listeners) {
				list.recordUpdated(this);
			}
		}
	}
}
