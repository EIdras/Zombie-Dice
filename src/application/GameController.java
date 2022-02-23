package application;

import model.Dice;
import model.Enumerations.DiceFaces;
import model.Player;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// Contr�leur de jeu, contient toute la logique du jeu
public class GameController extends BasicController implements Initializable {

	private static int nbTotalDices = 0, totalNbBrains = 0, turnNbBrains = 0, nbShotguns = 0;
	private static Player currentPlayer;
	private static boolean islastTurn = false;
	private String difficulty;

	ArrayList<Dice> gobelet = new ArrayList<Dice>();			// Liste de d�s contenus dans le gobelet
	ArrayList<Dice> desJeu = new ArrayList<Dice>();				// Liste des d�s tir�s par le joueur
	ArrayList<Dice> stepsTemp = new ArrayList<Dice>();			// Liste de d�s temporaire contenant les d�s "empreintes" obtenus afin de les rejouer
	ArrayList<Dice> brainsTemp = new ArrayList<Dice>();			// Liste de d�s temporaire contenant les d�s "cerveau" obtenus afin de les remettre dans le gobelet

	ArrayList<Player> players = new ArrayList<Player>();		// Liste des joueurs de la partie
	ArrayList<Player> playerListScore = new ArrayList<Player>();// Liste des joueurs affich�s sur la vue des scores

	Image gobeletImg = new Image(getClass().getResource("img/gobelet.png").toString());
	Image brainImg = new Image(getClass().getResource("img/brain.png").toString());
	Image gunImg = new Image(getClass().getResource("img/gun.png").toString());

	Image redShotgunImg = new Image(getClass().getResource("img/red_shotgun.png").toString());
	Image redBrainImg = new Image(getClass().getResource("img/red_brain.png").toString());
	Image redStepsImg = new Image(getClass().getResource("img/red_steps.png").toString());

	Image yellowShotgunImg = new Image(getClass().getResource("img/yellow_shotgun.png").toString());
	Image yellowBrainImg = new Image(getClass().getResource("img/yellow_brain.png").toString());
	Image yellowStepsImg = new Image(getClass().getResource("img/yellow_steps.png").toString());

	Image greenShotgunImg = new Image(getClass().getResource("img/green_shotgun.png").toString());
	Image greenBrainImg = new Image(getClass().getResource("img/green_brain.png").toString());
	Image greenStepsImg = new Image(getClass().getResource("img/green_steps.png").toString());

	@FXML
	ImageView de1View, de2View, de3View, gobeletView, brainView, gunView;
	@FXML
	Canvas canvas;
	@FXML
	Label nbGreenDices_lbl, nbYellowDices_lbl, nbRedDices_lbl, lastTurn_lbl;
	@FXML
	Text playerName_txt, nextPlayerName_txt, gunNb_txt, brainNb_txt;
	@FXML
	ListView<String> scoreView;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		super.initialize(arg0, arg1);
		brainView.setImage(brainImg);
		gunView.setImage(gunImg);
	}

	public void initDragWindow() {
		Main.initDragWindow(getStage(playerName_txt));
	}

	// Lance les d�s du joueurs suivant des nombres al�atoires, affiche l'image correspondante au chiffre obtenu
	@FXML
	public void rollDice() {
		chooseDice();
		Random rand = new Random();		
		ImageView deView = null;
		int nbAleatoire;
		boolean finLancer = false;

		for (Dice de : desJeu) {
			nbAleatoire = rand.nextInt(6);						// Cr�ation d'un nombre al�atoire entre 1 et 6
			DiceFaces faceDe = de.getFaces().get(nbAleatoire);	// R�cup�re la face du d� correspondant � ce nombre

			switch (desJeu.indexOf(de)) {
			case 0:
				deView = de1View;
				break;
			case 1:
				deView = de2View;
				break;
			case 2:
				deView = de3View;
				break;
			default:
				System.err.println("Le d� tir� comporte une erreur");
			}

			switch (faceDe) {
			case brain: {
				addBrain(de);
				switch (de.getColor()) {
				case "green":
					deView.setImage(greenBrainImg);
					break;
				case "yellow":
					deView.setImage(yellowBrainImg);
					break;
				case "red":
					deView.setImage(redBrainImg);
					break;
				}
				break;
			}
			case shotgun: {
				if (addShotgun()) {
					finLancer = true;	// Permet de sortir de la boucle suivant le nombre de shotguns
				}
				
				switch (de.getColor()) {
				case "green":
					deView.setImage(greenShotgunImg);
					break;
				case "yellow":
					deView.setImage(yellowShotgunImg);
					break;
				case "red":
					deView.setImage(redShotgunImg);
					break;
				}
				break;
			}
			case steps: {
				addStep(de);
				switch (de.getColor()) {
				case "green":
					deView.setImage(greenStepsImg);
					break;
				case "yellow":
					deView.setImage(yellowStepsImg);
					break;
				case "red":
					deView.setImage(redStepsImg);
					break;
				}
				break;
			}
			}
			// Fin du tour car 3 shotguns donc sortie de la boucle
			if (finLancer)
				break;
		}
		if (finLancer) {
			createAlert();
			passTurn();
			desJeu.clear();
		}
	}

	// Termine la partie en affichant l'�cran des scores
	public void endScreen() {
		EndController endController = Main.getEndLoader().getController();
		endController.fillList(players);
		Main.setScreen(2);
		endController.initDragWindow();
	}

	
	// R�cup�re al�atoirement le nombre de d�s n�cessaires dans le gobelet, en mettant � jour les valeurs correspondantes
	@FXML
	public void chooseDice() {
		int nbDesRecuperes = stepsTemp.size();
		int nbDesATirer = 3 - nbDesRecuperes;
		if (gobelet.size() < nbDesATirer) {
			gobelet.addAll(brainsTemp);
		}
		desJeu.clear();
		for (Dice de : stepsTemp) {
			desJeu.add(de);
			int index = desJeu.indexOf(de);
			drawDices(de, index);
		}
		stepsTemp.clear();
		Random rand = new Random();
		for (int i = nbDesRecuperes; i < nbDesATirer + nbDesRecuperes; i++) {
			if (nbTotalDices == 0)
				nbTotalDices = 1;
			int nombreAleatoire = rand.nextInt(nbTotalDices);
			Dice deTire = gobelet.get(nombreAleatoire);
			desJeu.add(deTire);
			gobelet.remove(nombreAleatoire);
			drawDices(deTire, i);
			displayGobelet();
		}
	}

	// Passe un tour, r�initialise les compteurs et r�cup�re les points du joueur suivant.
	// est appel�e lors de l'appui sur "PASSER SON TOUR" ou bien lorsequ'un tour est perdu
	@FXML
	public void passTurn() {
		totalNbBrains += turnNbBrains;					// Ajout du nombre de cerveaux gagn�s ce tour au nombre total
		currentPlayer.setNbCerveaux(totalNbBrains);		// Le joueur r�cup�re ses cerveaux
		
		// V�rifie si un joueur a gagn� (cerveaux >= 13), si c'est le cas, c'est le dernier tour
		if (checkIfPlayerWon(currentPlayer)) {
			islastTurn = true;
			Font font = Font.loadFont(getClass().getResourceAsStream("/application/img/Danger.otf"), 30);	
			lastTurn_lbl.setFont(font);
			lastTurn_lbl.setVisible(true);				// Message d'information du dernier tour
		}
		
		// Conditions de fin du jeu, si le dernier joueur finit le dernier tour
		if ((players.indexOf(currentPlayer) == players.size() - 1) && (islastTurn)) {
			endScreen();
		}
		currentPlayer = players.get(getIndexNextPlayer(currentPlayer));
		playerName_txt.setText(currentPlayer.getName());
		nextPlayerName_txt.setText(players.get(getIndexNextPlayer(currentPlayer)).getName());
		clearCounters();
		clearArrays();
		setDifficulty(difficulty);
		fillList(players);
		totalNbBrains = currentPlayer.getNbCerveaux();
		turnNbBrains = 0;
		brainNb_txt.setText(String.valueOf(totalNbBrains));

		clearDices();
	}

	// M�thode qui v�rifie si un joueur a gagn�, renvoie vrai si le joueur a bien atteint les 13 cerveaux requis
	private boolean checkIfPlayerWon(Player playerToCheck) {
		if (playerToCheck.getNbCerveaux() >= 13) {
			return true;
		} else {
			return false;
		}
	}

	// Renvoie l'index dans la liste de joueurs du joueur suivant
	private int getIndexNextPlayer(Player player) {
		int nbJoueurs = players.size();
		int indexNextPlayer = players.indexOf(player) + 1;
		if (indexNextPlayer == nbJoueurs)
			indexNextPlayer = 0;
		return indexNextPlayer;
	}

	// Initialise le nombre de d�s de chaque couleur contenus dans le gobelet en fonction de la difficult� choisie
	public void setDifficulty(String difficulty) {
		// (facile : 8 � 3 � 2, moyen : 6 � 4 � 3, difficile : 4 � 5 � 4)
		this.difficulty = difficulty;
		switch (difficulty) {
		case "Facile":
			for (int i = 0; i < 8; i++)
				gobelet.add(new Dice("green"));
			for (int i = 0; i < 3; i++)
				gobelet.add(new Dice("yellow"));
			for (int i = 0; i < 2; i++)
				gobelet.add(new Dice("red"));
			break;
		case "Normal":
			for (int i = 0; i < 6; i++)
				gobelet.add(new Dice("green"));
			for (int i = 0; i < 4; i++)
				gobelet.add(new Dice("yellow"));
			for (int i = 0; i < 3; i++)
				gobelet.add(new Dice("red"));
			break;
		case "Difficile":
			for (int i = 0; i < 4; i++)
				gobelet.add(new Dice("green"));
			for (int i = 0; i < 5; i++)
				gobelet.add(new Dice("yellow"));
			for (int i = 0; i < 4; i++)
				gobelet.add(new Dice("red"));
			break;
		default:
			System.err.println("Erreur : mauvais choix de difficult�");
			break;
		}
		displayGobelet();
	}

	// Initialise les joueurs dans la liste 'players'
	public void setPlayers(ArrayList<Player> playersArray) {
		for (Player joueur : playersArray) {
			if (joueur.getName() != "") {
				players.add(joueur);
			}
		}
		currentPlayer = players.get(0);
		playerName_txt.setText(currentPlayer.getName());
		nextPlayerName_txt.setText(players.get(1).getName());
		fillList(players);
	}

	// M�thode d'affichage des d�s, plus particuli�rement de leur contour graphique
	// La couleur du contour est d�cid�e suivant la couleur du d� pass� en param�tre
	public void drawDices(Dice de, int index) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.rgb(200, 200, 200));
		switch (de.getColor()) {
		case "green":
			gc.setStroke(Color.rgb(100, 200, 100));
			break;
		case "yellow":
			gc.setStroke(Color.rgb(200, 200, 100));
			break;
		case "red":
			gc.setStroke(Color.rgb(200, 100, 100));
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + de.getColor());
		}
		clearDices();
		gc.setLineWidth(8);
		switch (index) {
		case 0:
			gc.fillRoundRect(5, 5, 150, 150, 75, 75);
			gc.strokeRoundRect(5, 5, 150, 150, 75, 75);
			break;
		case 1:
			gc.fillRoundRect(305, 5, 150, 150, 75, 75);
			gc.strokeRoundRect(305, 5, 150, 150, 75, 75);
			break;
		case 2:
			gc.fillRoundRect(605, 5, 150, 150, 75, 75);
			gc.strokeRoundRect(605, 5, 150, 150, 75, 75);
			break;
		}
	}

	// R�initialise les listes de d�s
	private void clearArrays() {
		brainsTemp.clear();
		stepsTemp.clear();
		gobelet.clear();
	}

	// R�initialise les compteurs, visuels et non visuels
	private void clearCounters() {
		turnNbBrains = 0;
		totalNbBrains = 0;
		nbShotguns = 0;
		gunNb_txt.setText("0");
		brainNb_txt.setText("0");
	}

	// Ajoute un cerveau au nombre de cerveaux gagn�s ce tour par le joueur
	private boolean addBrain(Dice de) {
		turnNbBrains++;
		if (totalNbBrains + turnNbBrains == 13) {
			return true;
		} else {
			brainsTemp.add(de);
			brainNb_txt.setText(String.valueOf(totalNbBrains + turnNbBrains));
			return false;
		}
	}

	// Remplit l'affichage de la liste des scores pendant le jeu. Les joueurs sont tri�s par leur nombre total de cerveaux.
	public void fillList(ArrayList<Player> players) {
		playerListScore.removeAll(playerListScore);
		playerListScore.addAll(players);
		scoreView.getItems().clear();
		Collections.sort(playerListScore, Collections.reverseOrder());
		for (Player player : playerListScore) {
			scoreView.getItems().add(player.toString());
		}
	}

	// Ajoute un d� empreinte � la liste des d�s empreintes
	private void addStep(Dice de) {
		stepsTemp.add(de);
	}

	// Si le joueur totalise 3 shotguns ce tour, fin du tour et perte des cerveaux gagn�s ce tour, sinon incr�mentation du nombre de shotguns
	private boolean addShotgun() {
		if (nbShotguns < 2) {
			nbShotguns++;
			gunNb_txt.setText(String.valueOf(nbShotguns));
			return false;
		} else {
			turnNbBrains = 0;
			return true;
		}
	}

	// Affiche une pop-up qui indique au joueur qu'il a perdu son tour en ayant r�cup�r� 3 shotguns
	public void createAlert() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION,
				"Votre tour est termin�. " + "C'est au tour de " + currentPlayer.getName(), ButtonType.OK);

		alert.setHeaderText("Vous avez cummul� 3 coups de fusil � pompe");
		alert.setTitle("PERDU");

		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(gunImg);

		DialogPane alertDialogPane = alert.getDialogPane();
		alertDialogPane.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		alertDialogPane.getStyleClass().add("alert");

		alert.showAndWait();
	}

	// R�initialise les images des d�s
	public void clearDices() {
		de1View.setImage(null);
		de2View.setImage(null);
		de3View.setImage(null);
	}

	// Met � jour le nombre de d�s contenus dans le gobelet
	private void displayGobelet() {
		gobeletView.setImage(gobeletImg);
		int nbGreenDices = 0, nbYellowDices = 0, nbRedDices = 0;
		for (Dice de : gobelet) {
			if (de.getColor() == "green")
				nbGreenDices++;
			if (de.getColor() == "yellow")
				nbYellowDices++;
			if (de.getColor() == "red")
				nbRedDices++;
		}
		nbGreenDices_lbl.setText(String.valueOf(nbGreenDices));
		nbYellowDices_lbl.setText(String.valueOf(nbYellowDices));
		nbRedDices_lbl.setText(String.valueOf(nbRedDices));
		nbTotalDices = nbGreenDices + nbYellowDices + nbRedDices;
	}

}
