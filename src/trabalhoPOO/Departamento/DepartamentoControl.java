package trabalhoPOO.Departamento;

import java.sql.SQLException;
import java.util.List;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DepartamentoControl {
	private LongProperty id = new SimpleLongProperty(0);
	private StringProperty nome = new SimpleStringProperty("");
	
	private DepartamentoDAO departamentoDao;
	private ObservableList<Departamento> lista = FXCollections.observableArrayList();

	public DepartamentoControl() throws ClassNotFoundException, SQLException { 
		departamentoDao = new DepartamentoDAOImpl();
	}
	
	public void novo() { 
		id.set(0);
		nome.set("");
	}
	
	public void excluir(Departamento departamento) throws SQLException { 
		departamentoDao.remover(departamento.getId());
		selectAll();
	}
	
	public void fromEntity(Departamento departamento) { 
		id.set(departamento.getId());
		nome.set(departamento.getNome());
	}
	
	public void adicionar() throws SQLException { 
		if (id.get() == 0) {
			Departamento departamento = new Departamento();
			departamento.setId(id.get());
			departamento.setNome(nome.get());
			departamento = departamentoDao.adicionar(departamento);
		} else { 
			Departamento departamento = new Departamento();
			departamento.setId(id.get());
			departamento.setNome(nome.get());
			departamentoDao.atualizar(id.get(), departamento);
		}
		selectAll();
	}
	
	public void limparTabela () throws SQLException {
		departamentoDao.limparTabela();
		selectAll();
		System.out.println("limpou");
	}
	
	public void pesquisar() throws SQLException { 
		lista.clear();
		List<Departamento> lst = departamentoDao.procurarPorTitulo( nome.get() );
		lista.addAll(lst);		
	}
	
	public void selectAll () throws SQLException {
		lista.clear();
		List<Departamento> lst = departamentoDao.procurarAll();
		lista.addAll(lst);
	}
	
	public StringProperty nomeProperty() { 
		return nome;
	}
	
	public ObservableList<Departamento> getLista() { 
		return lista;
	}

}