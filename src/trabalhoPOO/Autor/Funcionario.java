package trabalhoPOO.Autor;

public class Funcionario {
	private long id;
	private String nome;
	private String email;
	private int departamentoId;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getDepartamentoId() {
		return departamentoId;
	}
	public void setDepartamentoId(int departamentoId) {
		this.departamentoId = departamentoId;
	}
}
