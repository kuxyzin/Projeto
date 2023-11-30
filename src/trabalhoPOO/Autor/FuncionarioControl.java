package trabalhoPOO.Autor;

import trabalhoPOO.Departamento.*;
import java.sql.SQLException;
import java.util.List;

import javafx.beans.property.LongProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FuncionarioControl {
	private LongProperty id = new SimpleLongProperty(0);
	private StringProperty nome = new SimpleStringProperty("");
	private StringProperty email = new SimpleStringProperty("");
	private IntegerProperty departamentoId = new SimpleIntegerProperty(0);
	
	private FuncionarioDAO livroDao;
	private ObservableList<Funcionario> lista = FXCollections.observableArrayList();
	
	ObservableList<Departamento> departamentoList = FXCollections.observableArrayList();

	public FuncionarioControl() throws ClassNotFoundException, SQLException { 
		livroDao = new FuncionarioDAOImpl();
	}
	
	public void novo() { 
		id.set(0);
		nome.set("");
		email.set("");
		departamentoId.set(0);
	}
	
	public void excluir(Funcionario funcionario) throws SQLException { 
		livroDao.remover(funcionario.getId());
		selectAll();
	}
	
	public void fromEntity(Funcionario funcionario) { 
		id.set(funcionario.getId());
		nome.set(funcionario.getNome());
		email.set(funcionario.getEmail());
	}
	
	public void adicionar() throws SQLException { 
		if (id.get() == 0) {
			Funcionario funcionario = new Funcionario();
			funcionario.setId(id.get());
			funcionario.setNome(nome.get());
			funcionario.setEmail(email.get());
			funcionario.setDepartamentoId(departamentoId.get());
			funcionario = livroDao.adicionar(funcionario);
		} else { 
			Funcionario funcionario = new Funcionario();
			funcionario.setId(id.get());
			funcionario.setNome(nome.get());
			funcionario.setEmail(email.get());
			funcionario.setDepartamentoId(departamentoId.get());
			livroDao.atualizar(id.get(), funcionario);
		}
		selectAll();
	}
	
	public void pesquisar() throws SQLException { 
		lista.clear();
		List<Funcionario> lst = livroDao.procurarPorTitulo( nome.get() );
		lista.addAll(lst);		
	}
	
	public void selectAll () throws SQLException {
		lista.clear();
		List<Funcionario> lst = livroDao.procurarAll();
		lista.addAll(lst);
	}
	
	public StringProperty nomeProperty() { 
		return nome;
	}
	
	public StringProperty emailProperty() { 
		return email;
	}
	
	public IntegerProperty departamentoIdProperty() { 
		return departamentoId;
	}
	
	public ObservableList<Funcionario> getLista() { 
		return lista;
	}
	
	public ObservableList<Departamento> getDepartamentoList() { 
		return departamentoList;
	}

}