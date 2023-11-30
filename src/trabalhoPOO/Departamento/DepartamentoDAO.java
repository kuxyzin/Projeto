package trabalhoPOO.Departamento;

import java.sql.SQLException;
import java.util.List;

public interface DepartamentoDAO {
	Departamento adicionar(Departamento departamento) throws SQLException;
	void atualizar(long id, Departamento departamento) throws SQLException;
	void remover(long id) throws SQLException;
	List<Departamento> procurarPorTitulo(String titulo) throws SQLException;
	Departamento procurarPorId(long id) throws SQLException;
	List<Departamento> procurarAll() throws SQLException;
	void limparTabela() throws SQLException;
}
