package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// Contrôleur lié à la page où les règles du jeu sont affichées
public class RulesController extends BasicController implements Initializable{
	
	@FXML ImageView rulesView;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		super.initialize(arg0, arg1);
		rulesView.setImage(new Image("file:src/application/img/rules.png"));	// Image des règles du jeu
	}
	
	// Permet de retourner au menu (à la première fenêtre)
	@FXML public void returnMenu(){
		Main.setScreen(0);
	}
	
	public void initDragWindow() {
		Main.initDragWindow(getStage(rulesView));
	}
}
