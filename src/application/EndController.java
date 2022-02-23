package application;

import model.Player;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// Contrôleur lié à la page de fin où se trouve le tableau des scores de la partie
public class EndController extends BasicController implements Initializable {

	@FXML
	ListView<String> liste = new ListView<>();	// Liste des joueurs
	@FXML
	ImageView crownView1, crownView2, crownView3;
	Image gold_crown = new Image(getClass().getResource("img/gold_crown.png").toString());
	Image silver_crown = new Image(getClass().getResource("img/silver_crown.png").toString());
	Image bronze_crown = new Image(getClass().getResource("img/bronze_crown.png").toString());

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		super.initialize(arg0, arg1);
		crownView1.setImage(gold_crown);
		crownView2.setImage(silver_crown);
		crownView3.setImage(bronze_crown);
	}

	public void initDragWindow() {
		Main.initDragWindow(getStage(liste));
	}

	// Remplit la liste avec la liste des joueurs du contrôleur du jeu, et les trie par score
	public void fillList(ArrayList<Player> players) {

		Collections.sort(players, Collections.reverseOrder());

		for (Player player : players) {
			liste.getItems().add(player.toString());
		}
	}
}
