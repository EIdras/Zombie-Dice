package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Player;

// Contr�leur du menu, o� l'on fait le choix des param�tres du jeu et rentre le nom des joueurs
public class MenuController extends BasicController implements Initializable {

	
	Font font = Font.loadFont(getClass().getResourceAsStream("/application/img/Danger.otf"), 100);	// Instanciation d'une police, pour le titre du jeu entre autres
	DropShadow shadowTitle = new DropShadow();														// Effets d'ombres pour certains textes
	DropShadow shadowLabels = new DropShadow();
	ArrayList<Player> tempPlayers = new ArrayList<Player>();										// Liste des joueurs temporaire, toutes les entr�es
																									// des TextField sont r�cup�r�es
	ArrayList<Player> players = new ArrayList<Player>();											// Liste des joueurs, les joueurs sans nom n'y figurent pas

	@FXML
	Label gameTitle, LblDifficult�, LblNbJoueurs;
	@FXML
	Button playBtn;
	@FXML
	ChoiceBox<String> difficultyChooser;
	@FXML
	Slider playerNumberChooser;
	@FXML
	ImageView icon, closeBtn;
	@FXML
	TextField j1Field, j2Field, j3Field, j4Field, j5Field, j6Field, j7Field, j8Field;

	// Fonction li�e au bouton "JOUER", appelle des m�thodes du GameController et change la fen�tre de l'application
	@FXML
	public void playButton() throws IOException {
		GameController gameController = Main.getGameLoader().getController();
		gameController.setDifficulty(getDifficulty());
		if (setPlayers(getPlayerNumber()))
			return;
		gameController.setPlayers(tempPlayers);
		Main.setScreen(1);						// Changement de fen�tre (jeu)
		gameController.initDragWindow();
	}

	
	// Fonction du bouton "REGLES", affiche la page de r�gles
	@FXML
	public void rulesButton() throws IOException {
		RulesController rulesController = Main.getRulesLoader().getController();
		Main.setScreen(3);
		rulesController.initDragWindow();
	}

	public String getDifficulty() {
		return difficultyChooser.getValue();
	}

	public int getPlayerNumber() {
		return (int) playerNumberChooser.getValue();
	}

	public ArrayList<Player> getPlayerNames() {
		return players;
	}

	// R�cup�re tous les noms des joueurs entr�s dans les 8 TextField, et pour chaque nom instancie un Player avec 0 point.
	// La liste des joueurs ne contient pas de joueur avec un nom vide.
	// Si il n'y a pas assez de joueurs renseign�s, une alerte indique � l'utilisateur de renseigner plus de noms.
	public boolean setPlayers(int nbPlayers) {
		tempPlayers.clear();
		players.clear();

		tempPlayers.add(new Player(j1Field.getText(), 0));
		tempPlayers.add(new Player(j2Field.getText(), 0));
		tempPlayers.add(new Player(j3Field.getText(), 0));
		tempPlayers.add(new Player(j4Field.getText(), 0));
		tempPlayers.add(new Player(j5Field.getText(), 0));
		tempPlayers.add(new Player(j6Field.getText(), 0));
		tempPlayers.add(new Player(j7Field.getText(), 0));
		tempPlayers.add(new Player(j8Field.getText(), 0));

		// Ajoute � la liste seulement les noms qui ne sont pas vides
		for (Player joueur : tempPlayers) {
			if (joueur.getName() != "") {
				players.add(joueur);
			}
		}
		if (players.size() <= 1) {
			// Cr�ation de l'alerte, cr�e une fen�tre suppl�mentaire lorseque seulement 0 ou 1 nom de joueur est renseign�
			Alert alert = new Alert(Alert.AlertType.INFORMATION,
					"Changez le nombre de joueurs en modifiant la valeur du slider !", ButtonType.OK);

			alert.setHeaderText("Vous devez entrer au moins 2 joueurs pour pouvoir continuer.");
			alert.setTitle("Pas assez de joueurs !");
			alert.showAndWait();
			return true;
		} else {
			return false;
		}
	}

	// Affiche les difficult�s disponibles dans le s�lecteur
	public void initializeDifficultyChooser() {
		ObservableList<String> difficulties = FXCollections.observableArrayList();
		difficulties.addAll("Facile", "Normal", "Difficile");
		difficultyChooser.setItems(difficulties);
		difficultyChooser.setValue("Normal");	// Valeur par d�faut
	}

	// Permet d'afficher � l'�cran le nombre de TextField correspondant au nombre de joueurs d�finis par le slider.
	// Une �coute sur la valeur de ce slider permet de cacher/afficher les entr�es de texte.
	public void playerEntryListener() {
		playerNumberChooser.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> obsValue, Number oldValue, Number newValue) {
				switch (newValue.intValue()) {
				case 1: {
					hideAll();
					j1Field.setVisible(true);
					j2Field.setVisible(false);
					break;
				}
				case 2: {
					hideAll();
					j1Field.setVisible(true);
					j2Field.setVisible(true);
					break;
				}
				case 3: {
					hideAll();
					j1Field.setVisible(true);
					j2Field.setVisible(true);
					j3Field.setVisible(true);
					break;
				}
				case 4: {
					hideAll();
					j1Field.setVisible(true);
					j2Field.setVisible(true);
					j3Field.setVisible(true);
					j4Field.setVisible(true);
					break;
				}
				case 5: {
					hideAll();
					j1Field.setVisible(true);
					j2Field.setVisible(true);
					j3Field.setVisible(true);
					j4Field.setVisible(true);
					j5Field.setVisible(true);
					break;
				}
				case 6: {
					hideAll();
					j1Field.setVisible(true);
					j2Field.setVisible(true);
					j3Field.setVisible(true);
					j4Field.setVisible(true);
					j5Field.setVisible(true);
					j6Field.setVisible(true);
					break;
				}
				case 7: {
					hideAll();
					j1Field.setVisible(true);
					j2Field.setVisible(true);
					j3Field.setVisible(true);
					j4Field.setVisible(true);
					j5Field.setVisible(true);
					j6Field.setVisible(true);
					j7Field.setVisible(true);
					break;
				}
				case 8: {
					j1Field.setVisible(true);
					j2Field.setVisible(true);
					j3Field.setVisible(true);
					j4Field.setVisible(true);
					j5Field.setVisible(true);
					j6Field.setVisible(true);
					j7Field.setVisible(true);
					j8Field.setVisible(true);
					break;
				}
				default:
					throw new IllegalArgumentException("Unexpected value: " + newValue.intValue());
				}
			}
		});
	}
	// Cache toutes les entr�es de nom de joueurs
	public void hideAll() {
		j1Field.setVisible(false);
		j2Field.setVisible(false);
		j3Field.setVisible(false);
		j4Field.setVisible(false);
		j5Field.setVisible(false);
		j6Field.setVisible(false);
		j7Field.setVisible(false);
		j8Field.setVisible(false);
	}

	// Applique une police sp�ciale et un effet de contour au titre et � certains labels
	public void decorateTitle() {
		gameTitle.setFont(font);
		shadowTitle.setColor(Color.rgb(255, 130, 120));
		shadowTitle.setSpread(40);
		gameTitle.setEffect(shadowTitle);
		shadowLabels.setColor(Color.rgb(0, 36, 2));
		shadowLabels.setSpread(10);
		LblDifficult�.setEffect(shadowLabels);
		LblNbJoueurs.setEffect(shadowLabels);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		super.initialize(arg0, arg1);
		initializeDifficultyChooser();
		playerEntryListener();
		decorateTitle();
	}

}
