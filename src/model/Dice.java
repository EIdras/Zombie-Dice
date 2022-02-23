package model;

import java.util.ArrayList;

import model.Enumerations.DiceFaces;

public class Dice {
    private String color;

    private ArrayList<DiceFaces> faces;

    public Dice (String color) {

        this.color = color;
        this.faces = new ArrayList<>();

        if (color.toLowerCase() == "green") {
            this.faces.add(DiceFaces.brain);
            this.faces.add(DiceFaces.brain);
            this.faces.add(DiceFaces.brain);
            this.faces.add(DiceFaces.steps);
            this.faces.add(DiceFaces.steps);
            this.faces.add(DiceFaces.shotgun);
        } else if (color.toLowerCase() == "yellow") {
            this.faces.add(DiceFaces.brain);
            this.faces.add(DiceFaces.brain);
            this.faces.add(DiceFaces.steps);
            this.faces.add(DiceFaces.steps);
            this.faces.add(DiceFaces.shotgun);
            this.faces.add(DiceFaces.shotgun);
        } else if (color.toLowerCase() == "red") {
            this.faces.add(DiceFaces.brain);
            this.faces.add(DiceFaces.steps);
            this.faces.add(DiceFaces.steps);
            this.faces.add(DiceFaces.shotgun);
            this.faces.add(DiceFaces.shotgun);
            this.faces.add(DiceFaces.shotgun);
        }
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public ArrayList<DiceFaces> getFaces() {
        return faces;
    }

    public void setFaces(ArrayList<DiceFaces> faces) {
        this.faces = faces;
    }

	@Override
	public String toString() {
		return "Dé " + color;
	}
    
}
