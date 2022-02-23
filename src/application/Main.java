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

	private static FXMLLoader menuLoader, gameLoader, endLoader, rulesLoader;	// D�claration de FXMLLoader
	private static List<Pane> screens = new ArrayList<>(); 						// Liste des �crans
	private static Scene scene;

	@Override
	public void start(Stage primaryStage) {
		try {
			// Instancie les FXMLLoader en leur liant le fichier fxml correspondant
			menuLoader = new FXMLLoader(getClass().getResource("menuPage.fxml"));
			gameLoader = new FXMLLoader(getClass().getResource("gamePage.fxml"));
			endLoader = new FXMLLoader(getClass().getResource("endPage.fxml"));
			rulesLoader = new FXMLLoader(getClass().getResource("rulesPage.fxml"));

			// Ajoute les FXMLLoader � la liste des �crans
			screens.add((BorderPane) menuLoader.load());
			screens.add((AnchorPane) gameLoader.load());
			screens.add((AnchorPane) endLoader.load());
			screens.add((AnchorPane) rulesLoader.load());

			scene = new Scene(screens.get(0));															// Affiche le premi�r �cran (fen�tre du menu)
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm()); 	// Lie le fichier CSS � la Scene

			primaryStage.initStyle(StageStyle.UNDECORATED);												// Retire les bordures de fen�tre Windows
			primaryStage.getIcons().add(new Image(getClass().getResource("img/zombi.png").toString())); // Ajoute un ic�ne � l'application
			primaryStage.setResizable(false);															// Redimensionnement de la fen�tre impossible
			initDragWindow(primaryStage);																// Permet de changer la fen�tre de position

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Renvoie l'�cran correspondant � l'index renseign�
	public static Pane getScreen(int index) {
		return screens.get(index);
	}

	// Permet de changer l'�cran affich� sur la sc�ne
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

	// Permet de d�placer la fen�tre de l'application en restant appuy� sur la souris
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
