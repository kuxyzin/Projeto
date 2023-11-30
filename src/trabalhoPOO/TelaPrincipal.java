package trabalhoPOO;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import trabalhoPOO.Autor.FuncionarioBoundary;
import trabalhoPOO.Departamento.DepartamentoBoundary;

public class TelaPrincipal extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		
		Tela funcionario = new FuncionarioBoundary();
		funcionario.start();
		
		Tela departamento = new DepartamentoBoundary();
		departamento.start();
		
		BorderPane principal = new BorderPane();
		
		Button btnAutores = new Button("Fucionario");
		btnAutores.setOnAction(e -> {
			principal.setCenter(funcionario.render());
		});
		Button btnLivros = new Button("Departamento");
		btnLivros.setOnAction( e-> {
			principal.setCenter(departamento.render());
		});
		
		principal.setTop(new HBox(btnLivros, btnAutores));
		
		Scene scn = new Scene(principal, 800, 600);
		
		stage.setScene(scn);
		stage.setTitle("Sistema de Funcionarios");
		stage.show();
	}
	
	public static void main(String[] args) {
		Application.launch(TelaPrincipal.class, args);
	}

}
