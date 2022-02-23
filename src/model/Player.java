package model;

public class Player implements Comparable<Player>{
	@Override
	public String toString() {
		return name + ", " + nbCerveaux;
	}

	private String name;
	private int nbCerveaux;
	
	public Player(String name, int nbCerveaux) {
		super();
		this.name = name;
		this.nbCerveaux = nbCerveaux;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNbCerveaux() {
		return nbCerveaux;
	}

	public void setNbCerveaux(int nbCerveaux) {
		this.nbCerveaux = nbCerveaux;
	}

	@Override
	public int compareTo(Player otherPlayer) {
		return Integer.compare(getNbCerveaux(), otherPlayer.getNbCerveaux());
	}

}
