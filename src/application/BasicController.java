package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

// Contr�leur parent, est h�rit� par tous les autres contr�leurs.
// Permet de ne pas duppliquer certaines parties du code dans les autres contr�leurs
public class BasicController implements Initializable {

	// L'ic�ne de l'application en haut � gauche et le bouton pour fermer en haut � droite
	@FXML
	ImageView icon, closeBtn;		

	@FXML
	public void closeButton() {
		getStage(icon).close();
	}

	@FXML
	public void changeBtnImg() {
		closeBtn.setImage(new Image(getClass().getResource("img/quitc.png").toString()));
	}

	// Initialise les ic�nes (images)
	public void initIcons() {		
		icon.setImage(new Image(getClass().getResource("img/zombi.png").toString()));
		closeBtn.setImage(new Image(getClass().getResource("img/quit.png").toString()));
	}

	// Permet de r�cup�rer l'�l�ment 'Stage' � partir de n'importe quel �l�ment de n'importe quel contr�leur
	public Stage getStage(Node node) {
		Stage stage = (Stage) node.getScene().getWindow();
		return stage;
	}

	// Cette m�thode est appel�e en premier par les autres contr�leurs
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initIcons();
	}

}
