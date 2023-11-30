package trabalhoPOO.Autor;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAOImpl implements FuncionarioDAO {
	private static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
	private static final String JDBC_URL = "jdbc:mariadb://localhost:3307/empresa?allowMultiQueries=true&allowPublicKeyRetrieval=true&useSSL=false";
	private static final String JDBC_USER = "root";
	private static final String JDBC_PASS = "root";
	private Connection con;
	
	public FuncionarioDAOImpl() throws ClassNotFoundException, SQLException { 
		Class.forName(JDBC_DRIVER);
		con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

	}

	@Override
	public Funcionario adicionar(Funcionario funcionario) throws SQLException {
		System.out.println(funcionario.getDepartamentoId());
		String sql = "INSERT INTO funcionario "
				+ "(nome, email, departamentoId) "
				+ "VALUES (?, ?, ?)";
		
		PreparedStatement st = 
			con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		st.setString(1, funcionario.getNome());
		st.setString(2, funcionario.getEmail());
		st.setInt(3, funcionario.getDepartamentoId());
		st.executeUpdate();
		ResultSet rs = st.getGeneratedKeys();
		if (rs.next()) {
			long id = rs.getLong(1);
			funcionario.setId(id);
		}
		return funcionario;
	}

	@Override
	public void atualizar(long id, Funcionario funcionario) throws SQLException {
		String sql = "UPDATE funcionario SET nome = ?, email = ?, departamentoId = ? WHERE id = ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, funcionario.getNome());
		st.setString(2, funcionario.getEmail());
		st.setInt(3, funcionario.getDepartamentoId());
		st.setLong(4, id);
		st.executeUpdate();
		
	}

	@Override
	public void remover(long id) throws SQLException {
		String sql = "DELETE FROM funcionario WHERE id = ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setLong(1, id);
		st.executeUpdate();
	}

	@Override
	public List<Funcionario> procurarPorTitulo(String nome)
		throws SQLException {
		List<Funcionario> lista = new ArrayList<>();
		String sql = "SELECT * FROM funcionario WHERE nome "
				+ "LIKE ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%" + nome + "%");
		ResultSet rs = st.executeQuery();
		while( rs.next() ) { 
			Funcionario funcionario = new Funcionario();
			funcionario.setId(rs.getLong("id"));
			funcionario.setNome(rs.getString("nome"));
			funcionario.setEmail(rs.getString("email"));
			funcionario.setDepartamentoId(rs.getInt("departamentoId"));
			lista.add(funcionario);
		}
		return lista;
	}

	@Override
	public Funcionario procurarPorId(long id) throws SQLException {
		String sql = "SELECT * FROM funcionario WHERE id = ? ";
		PreparedStatement st = con.prepareStatement(sql);
		st.setLong(1, id);
		ResultSet rs = st.executeQuery();
		if ( rs.next() ) { 
			Funcionario funcionario = new Funcionario();
			funcionario.setId(rs.getLong("id"));
			funcionario.setNome(rs.getString("nome"));
			funcionario.setEmail(rs.getString("email"));
			funcionario.setDepartamentoId(rs.getInt("departamentoId"));
			return funcionario;
		}
		return null;
	}
	
	public List<Funcionario> procurarAll()
			throws SQLException {
			List<Funcionario> lista = new ArrayList<>();
			String sql = "SELECT * FROM funcionario";
			PreparedStatement st = con.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while( rs.next() ) { 
				Funcionario funcionario = new Funcionario();
				funcionario.setId(rs.getLong("id"));
				funcionario.setNome(rs.getString("nome"));
				funcionario.setEmail(rs.getString("email"));
				funcionario.setDepartamentoId(rs.getInt("departamentoId"));
				lista.add(funcionario);
			}
			return lista;
		}

}
