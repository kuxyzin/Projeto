package trabalhoPOO.Departamento;

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
import trabalhoPOO.Tela;

public class DepartamentoBoundary implements Tela {
	private TextField txtNome = new TextField();
	
	private DepartamentoControl control;
	private BorderPane principal;
	
	private TableView<Departamento> table = new TableView<>();
	
	
	public DepartamentoBoundary() { 
		
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
	}
	
	public void abastecerTableView() { 
		TableColumn<Departamento, Integer> colId = new TableColumn<>("ID");
		colId.setCellValueFactory(new PropertyValueFactory<Departamento, Integer>("id"));
		
		TableColumn<Departamento, String> colTitulo = new TableColumn<>("Nome");
		colTitulo.setCellValueFactory(new PropertyValueFactory<Departamento, String>("nome"));
		
		TableColumn<Departamento, Void> colAcoes = 
				new TableColumn<>("Ações");
		Callback<TableColumn<Departamento, Void>, TableCell<Departamento, Void>>
			callBack = new Callback<>() { 
						
			@Override
			public TableCell<Departamento, Void> 
					call(TableColumn<Departamento, Void> col) { 				
				TableCell<Departamento, Void> tCell = new TableCell<>() {
					
					final Button btnExcluir = new Button("Excluir");
					{
						btnExcluir.setOnAction( e -> {
							Departamento departamento = table.getItems().get(getIndex());  
							try { 
								control.excluir(departamento);	
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
		colId.setPrefWidth(quarto);
		colTitulo.setPrefWidth(quarto);
		colAcoes.setPrefWidth(quarto);
		
		
		table.getColumns().addAll(colId, colTitulo, colAcoes);
		table.setItems( control.getLista() );
		
		table.getSelectionModel().getSelectedItems().addListener(
				new ListChangeListener<Departamento>() {
					@Override
					public void onChanged(Change<? extends Departamento> departamento) {
						if (! departamento.getList().isEmpty()) { 
							control.fromEntity(departamento.getList().get(0));
						}
					} 
				}
		);
	}
	
	public void limparTudo () {
		try {			
			control.limparTabela();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void start() {
		
		try {
			control = new DepartamentoControl();
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
		
		grid.setVgap(5);
		
		ligacoes();
		abastecerTableView();
		
		Button btnNovo = new Button("Novo");
		btnNovo.setOnAction( e -> control.novo() );
		
		Button btnSalvar = new Button("Salvar");
		btnSalvar.setOnAction(e -> adicionar());
		
		Button btnPesquisar = new Button("Pesquisar");
		btnPesquisar.setOnAction(e -> pesquisar());
		
		Button btnLimparTabela = new Button("Limpar Tudo");
		btnLimparTabela.setOnAction(e -> limparTudo());
		
		FlowPane painelBotoes = new FlowPane();
		painelBotoes.getChildren().addAll(btnSalvar, btnPesquisar, btnLimparTabela);
		
		
		grid.add(btnNovo, 0, 3);
		grid.add(painelBotoes, 1, 3);
		pesquisar();
	}
	
	@Override
	public Pane render() { 
		return principal;
	}

}