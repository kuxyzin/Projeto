package trabalhoPOO.Departamento;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoDAOImpl implements DepartamentoDAO {
	private static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
	private static final String JDBC_URL = "jdbc:mariadb://localhost:3307/empresa?allowMultiQueries=true&allowPublicKeyRetrieval=true&useSSL=false";
	private static final String JDBC_USER = "root";
	private static final String JDBC_PASS = "root";
	private Connection con;
	
	public DepartamentoDAOImpl() throws ClassNotFoundException, SQLException { 
		Class.forName(JDBC_DRIVER);
		con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

	}

	@Override
	public Departamento adicionar(Departamento departamento) throws SQLException {
		String sql = "INSERT INTO departamento "
				+ "(nome) "
				+ "VALUES (?)";
		
		PreparedStatement st = 
			con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		st.setString(1, departamento.getNome());
		st.executeUpdate();
		ResultSet rs = st.getGeneratedKeys();
		if (rs.next()) { 
			long id = rs.getLong(1);
			departamento.setId(id);
		}
		return departamento;
	}

	@Override
	public void atualizar(long id, Departamento departamento) throws SQLException {
		String sql = "UPDATE departamento SET nome = ? WHERE id = ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, departamento.getNome());
		st.setLong(2, id);
		st.executeUpdate();
		
	}

	@Override
	public void remover(long id) throws SQLException {
		String sql = "DELETE FROM departamento WHERE id = ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setLong(1, id);
		st.executeUpdate();
	}

	@Override
	public List<Departamento> procurarPorTitulo(String nome)
		throws SQLException {
		List<Departamento> lista = new ArrayList<>();
		String sql = "SELECT * FROM departamento WHERE nome "
				+ "LIKE ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%" + nome + "%");
		ResultSet rs = st.executeQuery();
		while( rs.next() ) { 
			Departamento departamento = new Departamento();
			departamento.setId(rs.getLong("id"));
			departamento.setNome(rs.getString("nome"));
			lista.add(departamento);
		}
		return lista;
	}

	@Override
	public Departamento procurarPorId(long id) throws SQLException {
		String sql = "SELECT * FROM departamento WHERE id = ? ";
		PreparedStatement st = con.prepareStatement(sql);
		st.setLong(1, id);
		ResultSet rs = st.executeQuery();
		if ( rs.next() ) { 
			Departamento departamento = new Departamento();
			departamento.setId(rs.getLong("id"));
			departamento.setNome(rs.getString("nome"));
			return departamento;
		}
		return null;
	}
	
	public List<Departamento> procurarAll()
			throws SQLException {
			List<Departamento> lista = new ArrayList<>();
			String sql = "SELECT * FROM departamento";
			PreparedStatement st = con.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while( rs.next() ) { 
				Departamento departamento = new Departamento();
				departamento.setId(rs.getLong("id"));
				departamento.setNome(rs.getString("nome"));
				lista.add(departamento);
			}
			return lista;
		}
	
	public void limparTabela() throws SQLException {
		String sql = "DELETE FROM departamento";
		PreparedStatement st = con.prepareStatement(sql);
		st.executeQuery();
	}

}
