package br.carjet.locadora.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.carjet.locadora.model.TipoUsuario;
import br.carjet.locadora.model.Usuario;

public class UsuarioLoginDAO extends DAO<Usuario> {
	
	public boolean create (Usuario obj) {
		
		int num = 1;
		
		boolean retorno = false;
		Connection conn = getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO usuario ");
		sql.append("	(nome, login, senha, datanascimento, email, tipousuario) ");
		sql.append("VALUES ");
		sql.append("	( ? , ? , ? , ? , ? , ? ) ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, obj.getNome());
			stat.setString(2, obj.getLogin());
			stat.setString(3, obj.getSenha());
			stat.setDate(4, java.sql.Date.valueOf(obj.getDataNascimento()));
			stat.setString(5, obj.getEmail());
			stat.setInt(6, num);
			
			stat.execute();
			
			conn.commit();

			System.out.println("Inclusão realizada com sucesso.");
			
			retorno = true;

		} catch (SQLException e) {
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeStatement(stat);
			closeConnection(conn);
		}
		return retorno;
	}

	
	
	public Usuario findById(int id) {
		Usuario usuario = null;
		Connection conn = getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append(" 	id, nome, login, senha, datanascimento, email, tipousuario ");
		sql.append("FROM ");
		sql.append("	usuario ");
		sql.append("WHERE ");
		sql.append("	id = ? ");

		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, id);
			
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				usuario = new Usuario();
				usuario.setId(rs.getInt("id"));
				usuario.setNome(rs.getString("nome"));
				usuario.setLogin(rs.getString("login"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setDataNascimento(rs.getDate("datanascimento").toLocalDate());
				usuario.setEmail(rs.getString("email"));
				usuario.setTipoUsuario(TipoUsuario.valueOf(rs.getInt("tipousuario")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeStatement(stat);
			closeConnection(conn);
		}
		return usuario;
	}
	
	public Usuario verificarLoginSenha(String login, String senha) {
		Usuario usuario = null;
		Connection conn = getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append(" 	id, nome, login, senha, datanascimento, email, tipousuario ");
		sql.append("FROM ");
		sql.append("	usuario ");
		sql.append("WHERE ");
		sql.append("	login = ? ");
		sql.append("	AND senha = ? ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, login);
			stat.setString(2, senha);
			
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				usuario = new Usuario();
				usuario.setId(rs.getInt("id"));
				usuario.setNome(rs.getString("nome"));
				usuario.setLogin(rs.getString("login"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setDataNascimento(rs.getDate("datanascimento").toLocalDate());
				usuario.setEmail(rs.getString("email"));
				usuario.setTipoUsuario(TipoUsuario.valueOf(rs.getInt("tipousuario")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeStatement(stat);
			closeConnection(conn);
		}
		return usuario;
	}



	@Override
	public boolean update(Usuario entity) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public List<Usuario> findAll() {
		// TODO Auto-generated method stub
		return null;
	}


}