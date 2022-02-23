package application;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {

	private static FXMLLoader menuLoader, gameLoader, endLoader, rulesLoader;	// Déclaration de FXMLLoader
	private static List<Pane> screens = new ArrayList<>(); 						// Liste des écrans
	private static Scene scene;

	@Override
	public void start(Stage primaryStage) {
		try {
			// Instancie les FXMLLoader en leur liant le fichier fxml correspondant
			menuLoader = new FXMLLoader(getClass().getResource("menuPage.fxml"));
			gameLoader = new FXMLLoader(getClass().getResource("gamePage.fxml"));
			endLoader = new FXMLLoader(getClass().getResource("endPage.fxml"));
			rulesLoader = new FXMLLoader(getClass().getResource("rulesPage.fxml"));

			// Ajoute les FXMLLoader à la liste des écrans
			screens.add((BorderPane) menuLoader.load());
			screens.add((AnchorPane) gameLoader.load());
			screens.add((AnchorPane) endLoader.load());
			screens.add((AnchorPane) rulesLoader.load());

			scene = new Scene(screens.get(0));															// Affiche le premièr écran (fenêtre du menu)
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm()); 	// Lie le fichier CSS à la Scene

			primaryStage.initStyle(StageStyle.UNDECORATED);												// Retire les bordures de fenêtre Windows
			primaryStage.getIcons().add(new Image(getClass().getResource("img/zombi.png").toString())); // Ajoute un icône à l'application
			primaryStage.setResizable(false);															// Redimensionnement de la fenêtre impossible
			initDragWindow(primaryStage);																// Permet de changer la fenêtre de position

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Renvoie l'écran correspondant à l'index renseigné
	public static Pane getScreen(int index) {
		return screens.get(index);
	}

	// Permet de changer l'écran affiché sur la scène
	public static void setScreen(int index) {
		scene.setRoot(screens.get(index));
	}

	public static FXMLLoader getGameLoader() {
		return gameLoader;
	}

	public static FXMLLoader getMenuLoader() {
		return menuLoader;
	}

	public static FXMLLoader getRulesLoader() {
		return rulesLoader;
	}

	public static FXMLLoader getEndLoader() {
		return endLoader;
	}

	// Permet de déplacer la fenêtre de l'application en restant appuyé sur la souris
	public static void initDragWindow(Stage primaryStage) {
		Pane bp = (Pane) scene.getRoot();
		bp.setOnMousePressed(pressEvent -> {
			bp.setOnMouseDragged(dragEvent -> {
				primaryStage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
				primaryStage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
			});
		});
	}

	public static void main(String[] args) {
		launch(args);
	}

}
