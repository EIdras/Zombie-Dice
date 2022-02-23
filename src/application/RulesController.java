package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// Contr�leur li� � la page o� les r�gles du jeu sont affich�es
public class RulesController extends BasicController implements Initializable{
	
	@FXML ImageView rulesView;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		super.initialize(arg0, arg1);
		rulesView.setImage(new Image("file:src/application/img/rules.png"));	// Image des r�gles du jeu
	}
	
	// Permet de retourner au menu (� la premi�re fen�tre)
	@FXML public void returnMenu(){
		Main.setScreen(0);
	}
	
	public void initDragWindow() {
		Main.initDragWindow(getStage(rulesView));
	}
}
