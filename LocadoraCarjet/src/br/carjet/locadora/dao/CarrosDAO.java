package br.carjet.locadora.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.carjet.locadora.model.Carros;

public class CarrosDAO extends DAO<Carros> {
	
	public boolean create (Carros carros) {
		
		boolean retorno = false;
		Connection conn = getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO carros ");
		sql.append("	(modelo, categoria, preco, estoque) ");
		sql.append("VALUES ");
		sql.append("	( ? , ? , ? , ? ) ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, carros.getModelo());
			stat.setString(2, carros.getCategoria());
			if (carros.getPreco() != null)
				stat.setFloat(3, carros.getPreco());
			else
				stat.setNull(3, java.sql.Types.FLOAT);
			
			if (carros.getEstoque() != null)
				stat.setInt(4, carros.getEstoque());
			else
				stat.setNull(4, java.sql.Types.INTEGER);
			
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

	public boolean update(Carros carros) {
		boolean retorno = false;
		Connection conn = getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE carros ");
		sql.append("	SET modelo=?, categoria=?, preco=?, estoque=? ");
		sql.append("WHERE ");
		sql.append("	id = ? ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, carros.getModelo());
			stat.setString(2, carros.getCategoria());
			stat.setFloat(3, carros.getPreco());
			stat.setInt(4, carros.getEstoque());
			stat.setInt(5, carros.getId());
			
			stat.execute();
			
			conn.commit();

			System.out.println("Alteração realizada com sucesso.");
			
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

	public boolean delete(int id) {
		boolean retorno = false;
		Connection conn = getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM carros ");
		sql.append("WHERE ");
		sql.append("	id = ? ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, id);
			
			stat.execute();
			
			conn.commit();

			System.out.println("Remoção realizada com sucesso.");
			
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

	public List<Carros> findAll() {
		List<Carros> listaCarros = new ArrayList<Carros>();
		Connection conn = getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append(" 	id, modelo, categoria, preco, estoque ");
		sql.append("FROM ");
		sql.append("	carros ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			
			ResultSet rs = stat.executeQuery();
			
			Carros carros = null;
			
			while(rs.next()) {
				carros = new Carros();
				carros.setId(rs.getInt("id"));
				carros.setModelo(rs.getString("modelo"));
				carros.setCategoria(rs.getString("categoria"));
				carros.setPreco(rs.getFloat("preco"));
				carros.setEstoque(rs.getInt("estoque"));
				listaCarros.add(carros);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeStatement(stat);
			closeConnection(conn);
		}
		return listaCarros;
	}

	public List<Carros> findByModelo(String modelo) {
		List<Carros> listaCarros = new ArrayList<Carros>();
		Connection conn = getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append(" 	id, modelo, categoria, preco, estoque ");
		sql.append("FROM ");
		sql.append("	carros ");
		sql.append("WHERE ");
		sql.append("	modelo ilike ? ");
		sql.append("ORDER BY modelo ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, "%" + modelo  + "%");
			
			ResultSet rs = stat.executeQuery();
			
			Carros carros = null;
			
			while(rs.next()) {
				carros = new Carros();
				carros.setId(rs.getInt("id"));
				carros.setModelo(rs.getString("modelo"));
				carros.setCategoria(rs.getString("categoria"));
				carros.setPreco(rs.getFloat("preco"));
				carros.setEstoque(rs.getInt("estoque"));
				listaCarros.add(carros);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeStatement(stat);
			closeConnection(conn);
		}
		return listaCarros;
	}	
	
	public List<Carros> findByCategoria(String categoria) {
		List<Carros> listaCarros = new ArrayList<Carros>();
		Connection conn = getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append(" 	id, modelo, categoria, preco, estoque ");
		sql.append("FROM ");
		sql.append("	carros ");
		sql.append("WHERE ");
		sql.append("	categoria ilike ? ");
		sql.append("ORDER BY categoria ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, "%" + categoria  + "%");
			
			ResultSet rs = stat.executeQuery();
			
			Carros carros = null;
			
			while(rs.next()) {
				carros = new Carros();
				carros.setId(rs.getInt("id"));
				carros.setModelo(rs.getString("modelo"));
				carros.setCategoria(rs.getString("categoria"));
				carros.setPreco(rs.getFloat("preco"));
				carros.setEstoque(rs.getInt("estoque"));
				listaCarros.add(carros);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeStatement(stat);
			closeConnection(conn);
		}
		return listaCarros;
	}	
	
	public Carros findById(int id) {
		Carros carros = null;
		Connection conn = getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append(" 	id, modelo, categoria, preco, estoque ");
		sql.append("FROM ");
		sql.append("	carros ");
		sql.append("WHERE ");
		sql.append("	id = ? ");

		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, id);
			
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				carros = new Carros();
				carros.setId(rs.getInt("id"));
				carros.setModelo(rs.getString("modelo"));
				carros.setCategoria(rs.getString("categoria"));
				carros.setPreco(rs.getFloat("preco"));
				carros.setEstoque(rs.getInt("estoque"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeStatement(stat);
			closeConnection(conn);
		}
		return carros;
	}

}