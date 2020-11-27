package br.carjet.locadora.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.carjet.locadora.application.Util;
import br.carjet.locadora.model.Carro;
import br.carjet.locadora.model.Perfil;
import br.carjet.locadora.model.Sexo;
import br.carjet.locadora.model.TipoCarro;
import br.carjet.locadora.model.Carro;

public class CarroDAO implements DAO<Carro> {

	@Override
	public void inserir(Carro obj) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ");
		sql.append("carro ");
		sql.append("  (nome, descricao, preco, estoque, tipo_carro) ");
		sql.append("VALUES ");
		sql.append("  ( ?, ?, ?, ?, ?) ");
		PreparedStatement stat = null;

		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, obj.getNome());
			stat.setString(2, obj.getDescricao());
			stat.setDouble(3, obj.getPreco());
			stat.setInt(4, obj.getEstoque());
			// ternario java
			stat.setObject(5, (obj.getTipoCarro() == null ? null : obj.getTipoCarro().getId()));

			stat.execute();
			// efetivando a transacao
			conn.commit();

		} catch (SQLException e) {

			System.out.println("Erro ao realizar um comando sql de insert.");
			e.printStackTrace();
			// cancelando a transacao
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.out.println("Erro ao realizar o rollback.");
				e1.printStackTrace();
			}
			exception = new Exception("Erro ao inserir");

		} finally {
			try {
				if (!stat.isClosed())
					stat.close();
			} catch (SQLException e) {
				System.out.println("Erro ao fechar o Statement");
				e.printStackTrace();
			}

			try {
				if (!conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				System.out.println("Erro a o fechar a conexao com o banco.");
				e.printStackTrace();
			}
		}

		if (exception != null)
			throw exception;
		
	}

	@Override
	public void alterar(Carro obj) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();

		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE carro SET ");
		sql.append("  nome = ?, ");
		sql.append("  descricao = ?, ");
		sql.append("  preco = ?, ");
		sql.append("  estoque = ?, ");
		sql.append("  tipo_carro = ? ");
		sql.append("WHERE ");
		sql.append("  id = ? ");

		PreparedStatement stat = null;

		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, obj.getNome());
			stat.setString(2, obj.getDescricao());
			stat.setDouble(3, obj.getPreco());
			stat.setInt(4, obj.getEstoque());
			// ternario java
			stat.setObject(5, (obj.getTipoCarro() == null ? null : obj.getTipoCarro().getId()));
			stat.setInt(6, obj.getId());

			stat.execute();
			// efetivando a transacao
			conn.commit();

		} catch (SQLException e) {

			System.out.println("Erro ao realizar um comando sql de insert.");
			e.printStackTrace();
			// cancelando a transacao
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.out.println("Erro ao realizar o rollback.");
				e1.printStackTrace();
			}
			exception = new Exception("Erro ao inserir");

		} finally {
			try {
				if (!stat.isClosed())
					stat.close();
			} catch (SQLException e) {
				System.out.println("Erro ao fechar o Statement");
				e.printStackTrace();
			}

			try {
				if (!conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				System.out.println("Erro a o fechar a conexao com o banco.");
				e.printStackTrace();
			}
		}

		if (exception != null)
			throw exception;

		
	}

	@Override
	public void excluir(Carro obj) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();

		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM carro WHERE id = ?");

		PreparedStatement stat = null;

		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, obj.getId());
			stat.execute();
			// efetivando a transacao
			conn.commit();

		} catch (SQLException e) {

			System.out.println("Erro ao realizar um comando sql de insert.");
			e.printStackTrace();
			// cancelando a transacao
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.out.println("Erro ao realizar o rollback.");
				e1.printStackTrace();
			}
			exception = new Exception("Erro ao inserir");

		} finally {
			try {
				if (!stat.isClosed())
					stat.close();
			} catch (SQLException e) {
				System.out.println("Erro ao fechar o Statement");
				e.printStackTrace();
			}

			try {
				if (!conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				System.out.println("Erro a o fechar a conexao com o banco.");
				e.printStackTrace();
			}
		}

		if (exception != null)
			throw exception;
	
	}

	@Override
	public List<Carro> obterTodos() throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();
		List<Carro> listaCarro = new ArrayList<Carro>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  m.id, ");
		sql.append("  m.nome, ");
		sql.append("  m.descricao, ");
		sql.append("  m.preco, ");
		sql.append("  m.estoque, ");
		sql.append("  m.tipo_carro ");
		sql.append("FROM  ");
		sql.append("  carro m ");
		sql.append("ORDER BY m.nome ");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());

			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				Carro carro = new Carro();
				carro.setId(rs.getInt("id"));
				carro.setNome(rs.getString("nome"));
				carro.setDescricao(rs.getString("descricao"));
				carro.setPreco(rs.getDouble("preco"));
				carro.setEstoque(rs.getInt("estoque"));
				carro.setTipoCarro(TipoCarro.valueOf(rs.getInt("tipo_carro")));

				listaCarro.add(carro);
			}

		} catch (SQLException e) {
			Util.addErrorMessage("Não foi possivel buscar os dados do carro.");
			e.printStackTrace();
			exception = new Exception("Erro ao executar um sql em carroDAO.");
		} finally {
			try {
				if (!stat.isClosed())
					stat.close();
			} catch (SQLException e) {
				System.out.println("Erro ao fechar o Statement");
				e.printStackTrace();
			}

			try {
				if (!conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				System.out.println("Erro a o fechar a conexao com o banco.");
				e.printStackTrace();
			}
		}

		if (exception != null)
			throw exception;

		return listaCarro;
	}

	@Override
	public Carro obterUm(Carro obj) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();
		
		Carro carro = null;

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  m.id, ");
		sql.append("  m.nome, ");
		sql.append("  m.descricao, ");
		sql.append("  m.preco, ");
		sql.append("  m.estoque, ");
		sql.append("  m.tipo_carro ");
		sql.append("FROM  ");
		sql.append("  carro m ");
		sql.append("WHERE m.id = ? ");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, obj.getId());

			ResultSet rs = stat.executeQuery();

			if (rs.next()) {
				carro = new Carro();
				carro.setId(rs.getInt("id"));
				carro.setNome(rs.getString("nome"));
				carro.setDescricao(rs.getString("descricao"));
				carro.setPreco(rs.getDouble("preco"));
				carro.setEstoque(rs.getInt("estoque"));
				carro.setTipoCarro(TipoCarro.valueOf(rs.getInt("tipo_carro")));
			}

		} catch (SQLException e) {
			Util.addErrorMessage("Não foi possivel buscar os dados do carro.");
			e.printStackTrace();
			exception = new Exception("Erro ao executar um sql em carroDAO.");
		} finally {
			try {
				if (!stat.isClosed())
					stat.close();
			} catch (SQLException e) {
				System.out.println("Erro ao fechar o Statement");
				e.printStackTrace();
			}

			try {
				if (!conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				System.out.println("Erro a o fechar a conexao com o banco.");
				e.printStackTrace();
			}
		}

		if (exception != null)
			throw exception;

		return carro;
	}

	public List<Carro> obterListaCarro(Integer tipo, String filtro) throws Exception {
		// tipo - 1 Nome; 2 Descricao
		Exception exception = null;
		Connection conn = DAO.getConnection();
		List<Carro> listacarro = new ArrayList<Carro>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  m.id, ");
		sql.append("  m.nome, ");
		sql.append("  m.descricao, ");
		sql.append("  m.preco, ");
		sql.append("  m.estoque, ");
		sql.append("  m.tipo_carro ");
		sql.append("FROM  ");
		sql.append("  carro m ");
		sql.append("WHERE ");
		sql.append("  upper(m.nome) LIKE upper( ? ) ");
		sql.append("  AND upper(m.descricao) LIKE upper( ? ) ");
		sql.append("ORDER BY m.nome ");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, tipo == 1 ? "%"+ filtro +"%" : "%");
			stat.setString(2, tipo == 2 ? "%"+ filtro +"%" : "%");

			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				Carro carro = new Carro();
				carro.setId(rs.getInt("id"));
				carro.setNome(rs.getString("nome"));
				carro.setDescricao(rs.getString("descricao"));
				carro.setPreco(rs.getDouble("preco"));
				carro.setEstoque(rs.getInt("estoque"));
				carro.setTipoCarro(TipoCarro.valueOf(rs.getInt("tipo_carro")));

				listacarro.add(carro);
			}

		} catch (SQLException e) {
			Util.addErrorMessage("Não foi possivel buscar os dados do carro.");
			e.printStackTrace();
			exception = new Exception("Erro ao executar um sql em carroDAO.");
		} finally {
			try {
				if (!stat.isClosed())
					stat.close();
			} catch (SQLException e) {
				System.out.println("Erro ao fechar o Statement");
				e.printStackTrace();
			}

			try {
				if (!conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				System.out.println("Erro a o fechar a conexao com o banco.");
				e.printStackTrace();
			}
		}

		if (exception != null)
			throw exception;

		return listacarro;
	}
	
	public List<Carro> obterListaCarroComEstoque(Integer tipo, String filtro) throws Exception {
		// tipo - 1 Nome; 2 Descricao
		Exception exception = null;
		Connection conn = DAO.getConnection();
		List<Carro> listacarro = new ArrayList<Carro>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  m.id, ");
		sql.append("  m.nome, ");
		sql.append("  m.descricao, ");
		sql.append("  m.preco, ");
		sql.append("  m.estoque, ");
		sql.append("  m.tipo_carro ");
		sql.append("FROM  ");
		sql.append("  carro m ");
		sql.append("WHERE ");
		sql.append("  upper(m.nome) LIKE upper( ? ) ");
		sql.append("  AND upper(m.descricao) LIKE upper( ? ) ");
		sql.append("  AND m.estoque > 0 ");
		sql.append("ORDER BY m.nome ");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, tipo == 1 ? "%"+ filtro +"%" : "%");
			stat.setString(2, tipo == 2 ? "%"+ filtro +"%" : "%");

			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				Carro carro = new Carro();
				carro.setId(rs.getInt("id"));
				carro.setNome(rs.getString("nome"));
				carro.setDescricao(rs.getString("descricao"));
				carro.setPreco(rs.getDouble("preco"));
				carro.setEstoque(rs.getInt("estoque"));
				carro.setTipoCarro(TipoCarro.valueOf(rs.getInt("tipo_carro")));

				listacarro.add(carro);
			}

		} catch (SQLException e) {
			Util.addErrorMessage("Não foi possivel buscar os dados do carro.");
			e.printStackTrace();
			exception = new Exception("Erro ao executar um sql em carroDAO.");
		} finally {
			try {
				if (!stat.isClosed())
					stat.close();
			} catch (SQLException e) {
				System.out.println("Erro ao fechar o Statement");
				e.printStackTrace();
			}

			try {
				if (!conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				System.out.println("Erro a o fechar a conexao com o banco.");
				e.printStackTrace();
			}
		}

		if (exception != null)
			throw exception;

		return listacarro;
	}
}
