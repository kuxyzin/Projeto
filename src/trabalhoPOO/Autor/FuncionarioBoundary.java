package trabalhoPOO.Autor;

import java.sql.SQLException;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import trabalhoPOO.Tela;

public class FuncionarioBoundary implements Tela {
	private TextField txtNome = new TextField();
	private TextField txtEmail = new TextField();
	private TextField txtDepartamentoId = new TextField();
	
	private FuncionarioControl control;
	private BorderPane principal;
	
	private TableView<Funcionario> table = new TableView<>();
	
	
	public FuncionarioBoundary() { 
		
	}

	
	public void adicionar() {
		try { 
			control.adicionar();
		} catch (SQLException e) { 
			Alert a = new Alert(AlertType.ERROR, "Erro ao gravar o livro, contate o Administrador", ButtonType.OK);
		a.showAndWait();
		}
	}
	
	public void pesquisar() {
		try { 
			control.pesquisar();
		} catch (SQLException e) { 
			Alert a = new Alert(AlertType.ERROR, "Erro ao pesquisar no banco de dados, contate o Administrador", ButtonType.OK);
			a.showAndWait();
		}
	}
	
	public void ligacoes() { 
		Bindings.bindBidirectional(txtNome.textProperty(), control.nomeProperty());
		Bindings.bindBidirectional(txtEmail.textProperty(), control.emailProperty());
		Bindings.bindBidirectional(txtDepartamentoId.textProperty(), control.departamentoIdProperty(), (StringConverter)new IntegerStringConverter());
	}
	
	public void abastecerTableView() { 
		TableColumn<Funcionario, String> colTitulo = new TableColumn<>("Nome");
		colTitulo.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("nome"));
		
		TableColumn<Funcionario, String> colEmail = new TableColumn<>("Email");
		colEmail.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("email"));
		
		TableColumn<Funcionario, Integer> colDepartamento = new TableColumn<>("Departamento");
		colDepartamento.setCellValueFactory(new PropertyValueFactory<Funcionario, Integer>("departamentoId"));
		
		TableColumn<Funcionario, Void> colAcoes = 
				new TableColumn<>("Ações");
		Callback<TableColumn<Funcionario, Void>, TableCell<Funcionario, Void>>
			callBack = new Callback<>() { 
						
			@Override
			public TableCell<Funcionario, Void> 
					call(TableColumn<Funcionario, Void> col) { 				
				TableCell<Funcionario, Void> tCell = new TableCell<>() {
					
					final Button btnExcluir = new Button("Excluir");
					{
						btnExcluir.setOnAction( e -> {
							Funcionario funcionario = table.getItems().get(getIndex());  
							try { 
								control.excluir(funcionario);	
							} catch (SQLException err) { 
								Alert a = new Alert(AlertType.ERROR, 
									"Erro ao excluir o registro,"
									+ "contate o Administrador", ButtonType.OK);
								a.showAndWait();
							}
						} );
					}
					
					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) { 
							setGraphic(null); 
						} else { 
							setGraphic(btnExcluir);
						}
					}
				};
				return tCell;
			}			
		};
		
		colAcoes.setCellFactory(callBack);
		
		double quarto = 600.0 / 4.0;
		colTitulo.setPrefWidth(quarto);
		colEmail.setPrefWidth(quarto);
		colDepartamento.setPrefWidth(quarto);
		colAcoes.setPrefWidth(quarto);
		
		
		table.getColumns().addAll(colTitulo, colEmail, colDepartamento, colAcoes);
		table.setItems( control.getLista() );
		
		table.getSelectionModel().getSelectedItems().addListener(
				new ListChangeListener<Funcionario>() {
					@Override
					public void onChanged(Change<? extends Funcionario> funcionario) {
						if (! funcionario.getList().isEmpty()) { 
							control.fromEntity(funcionario.getList().get(0));
						}
					} 
				}
		);
	}
	
	@Override
	public void start() {
		
		try {
			control = new FuncionarioControl();
		} catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			Alert a = new Alert(AlertType.ERROR, 
				"Erro ao acessar o banco de dados,"
				+ "contate o Administrador", ButtonType.OK);
			a.showAndWait();
		}
		
		principal = new BorderPane();
		
		GridPane grid = new GridPane();
		principal.setTop(grid);
		principal.setCenter(table);
		
		principal.setPadding(new Insets(20, 10, 20, 10));
		
		grid.setPadding(new Insets(20, 10, 20, 10));
		
		grid.add(new Label("Nome: "), 0, 0);
		grid.add(txtNome, 1, 0);
		
		grid.add(new Label("Email: "), 0, 1);
		grid.add(txtEmail, 1, 1);
		
		grid.add(new Label("Departamento: "), 0, 2);
		grid.add(txtDepartamentoId, 1, 2);
		
		grid.setVgap(5);
		
		ligacoes();
		abastecerTableView();
		
		Button btnNovo = new Button("Novo");
		btnNovo.setOnAction( e -> control.novo() );
		
		Button btnSalvar = new Button("Salvar");
		btnSalvar.setOnAction(e -> adicionar());
		
		Button btnPesquisar = new Button("Pesquisar");
		btnPesquisar.setOnAction(e -> pesquisar());
		
		FlowPane painelBotoes = new FlowPane();
		painelBotoes.getChildren().addAll(btnSalvar, btnPesquisar);
		
		
		grid.add(btnNovo, 0, 3);
		grid.add(painelBotoes, 1, 3);
		pesquisar();
	}
	
	@Override
	public Pane render() { 
		return principal;
	}

}