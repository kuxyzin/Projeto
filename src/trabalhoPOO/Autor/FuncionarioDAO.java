package trabalhoPOO.Autor;

import java.sql.SQLException;
import java.util.List;

public interface FuncionarioDAO {
	Funcionario adicionar(Funcionario funcionario) throws SQLException;
	void atualizar(long id, Funcionario funcionario) throws SQLException;
	void remover(long id) throws SQLException;
	List<Funcionario> procurarPorTitulo(String titulo) throws SQLException;
	Funcionario procurarPorId(long id) throws SQLException;
	List<Funcionario> procurarAll() throws SQLException;
}
