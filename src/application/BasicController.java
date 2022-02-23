package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

// Contrôleur parent, est hérité par tous les autres contrôleurs.
// Permet de ne pas duppliquer certaines parties du code dans les autres contrôleurs
public class BasicController implements Initializable {

	// L'icône de l'application en haut à gauche et le bouton pour fermer en haut à droite
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

	// Initialise les icônes (images)
	public void initIcons() {		
		icon.setImage(new Image(getClass().getResource("img/zombi.png").toString()));
		closeBtn.setImage(new Image(getClass().getResource("img/quit.png").toString()));
	}

	// Permet de récupérer l'élément 'Stage' à partir de n'importe quel élément de n'importe quel contrôleur
	public Stage getStage(Node node) {
		Stage stage = (Stage) node.getScene().getWindow();
		return stage;
	}

	// Cette méthode est appelée en premier par les autres contrôleurs
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initIcons();
	}

}
