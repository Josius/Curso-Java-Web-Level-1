package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement; //JDBC
import java.sql.ResultSet; //JDBC armazena o retorno do BD temp em um obj
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class DAO.
 */
public class DAO {
	
	/**  Modulo de Conexao *. */
	// Parametros de conexao
	private String driver = "com.mysql.cj.jdbc.Driver";// para funcionar, precisa do driver na pasta lib
	
	/** The url. */
	private String url = "IP do servidor";// IP do servidor,
																										// nome do BD e
																										// o fuso
																										/** The user. */
																										// horário
	private String user = "usuario que pode acessar o BD";// usuario que pode acessar o BD
	
	/** The password. */
	private String password = "senhaDoBD";// senha do BD

	/**
	 * Conectar.
	 *
	 * @return the connection
	 */
	// Metodo de conexao
	private Connection conectar() {
		Connection con = null;// objeto para estabelecer uma sessão com o BD
		// Para evitar diversos erros ao se conectar a um BD [tais como:
		// senha/nomeDoBD/senhaUsuario errado/trocado, servidorDeBD indisponivel, usamos
		// o try catch
		try {
			Class.forName(driver);// Lê o BD e procura nele a info da variavel driver (linha8)
			con = DriverManager.getConnection(url, user, password);// estabelecendo uma conexao com o BD - caminhoAteBD,
																	// nomeBD, autenticacao
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	/**
	 * Inserir contato.
	 *
	 * @param contato the contato
	 */
	/* CRUD CREATE */
	public void inserirContato(JavaBeans contato) {
		String create = "insert into contatos (nome,fone,email) values (?,?,?)";
		try {
			// abrir conexao com BD
			Connection con = conectar();
			// preparar a query para execucao no BD
			PreparedStatement pst = con.prepareStatement(create);
			// substituir os parametros (?) pelo conteudo das vars do JavaBeans
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			// executar a query
			pst.executeUpdate();
			// encerrar conexao com BD
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Listar contatos.
	 *
	 * @return the array list
	 */
	/* CRUD READ */
	public ArrayList<JavaBeans> listarContatos() {
		// criando um obj para acessar a classe JavaBeans
		ArrayList<JavaBeans> contatos = new ArrayList<>();
		String read = "select * from contatos order by nome";
		try {
			// abrindo conexao com BD
			Connection con = conectar();
			// preparar a query para execucao no BD
			PreparedStatement pst = con.prepareStatement(read);
			ResultSet rs = pst.executeQuery();
			// o laco abaixo executa enquanto houver contatos
			while (rs.next()) {
				// vars de apoio que recebem os dados do BD
				String idcon = rs.getString(1);
				String nome = rs.getString(2);
				String fone = rs.getString(3);
				String email = rs.getString(4);
				// populando o arraylist
				contatos.add(new JavaBeans(idcon, nome, fone, email));
			}
			con.close();
			return contatos;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	/* CRUD UPDATE */
	/**
	 * Selecionar contato.
	 *
	 * @param contato the contato
	 */
	// selecionar o contato
	public void selecionarContato(JavaBeans contato) {
		String read2 = "select * from contatos where idcon = ?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read2);
			pst.setString(1, contato.getIdcon());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				// setar as variaveis JavaBeans
				contato.setIdcon(rs.getString(1));
				contato.setNome(rs.getString(2));
				contato.setFone(rs.getString(3));
				contato.setEmail(rs.getString(4));
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Alterar contato.
	 *
	 * @param contato the contato
	 */
	// editar o contato
	public void alterarContato(JavaBeans contato) {
		String update = "update contatos set nome=?, fone=?, email=? where idcon=?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(update);
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			pst.setString(4, contato.getIdcon());
			pst.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Deletar contato.
	 *
	 * @param contato the contato
	 */
	/* CRUD DELETE */
	public void deletarContato(JavaBeans contato) {
		String delete = "delete from contatos where idcon=?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(delete);
			pst.setString(1, contato.getIdcon());
			pst.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// Teste de conexao
	/*
	 * public void testeConexao() { //novamente, conectarBD precisa de try-catch try
	 * { Connection con = conectar(); System.out.println(con); con.close(); } catch
	 * (Exception e) { System.out.println(e); } }
	 */
}
