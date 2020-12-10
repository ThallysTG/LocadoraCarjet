package br.carjet.locadora.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.carjet.locadora.model.Carros;
import br.carjet.locadora.model.ItemLocacao;
import br.carjet.locadora.model.Locacao;

public class ItemLocacaoDAO extends DAO<ItemLocacao> {
	
	@Override
	public boolean create(ItemLocacao itemLocacao) {
		
		boolean retorno = false;
		Connection conn = getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO public.itemLocacao ");
		sql.append("	(valor, idlocacao, idcarros) ");
		sql.append("VALUES ");
		sql.append("	(?, ?, ?) ");
		
		PreparedStatement stat = null;
		
		try {
			stat = conn.prepareStatement(sql.toString());
			
			stat.setFloat(1, itemLocacao.getValor());
			stat.setInt(2, itemLocacao.getLocacao().getId());
			stat.setInt(3, itemLocacao.getCarros().getId());
			stat.execute();
			
			conn.commit();
			
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

	public List<ItemLocacao> findByLocacao(Locacao locacao) {
		List<ItemLocacao> listaItemLocacao = new ArrayList<ItemLocacao>();
		Connection conn = getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  v.id, ");
		sql.append("  v.valor, ");
		sql.append("  v.idcarros, ");
		sql.append("  l.modelo, ");
		sql.append("  l.categoria, ");
		sql.append("  l.preco, ");
		sql.append("  l.estoque ");
		sql.append("FROM ");
		sql.append("  public.itemlocacao v, ");
		sql.append("  public.carros l ");
		sql.append("WHERE ");
		sql.append("  v.idcarros = l.id AND ");
		sql.append("  v.idlocacao = ? ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, locacao.getId());
			
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				ItemLocacao item = new ItemLocacao();
				item.setId(rs.getInt("id"));
				item.setValor(rs.getFloat("valor"));
				
				Carros carros = new Carros();
				carros.setId(rs.getInt("id"));
				carros.setModelo(rs.getString("modelo"));
				carros.setCategoria(rs.getString("categoria"));
				carros.setPreco(rs.getFloat("preco"));
				carros.setEstoque(rs.getInt("estoque"));
				
				item.setCarros(carros);
				
				item.setLocacao(locacao);
				
				listaItemLocacao.add(item);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeStatement(stat);
			closeConnection(conn);
		}
		return listaItemLocacao;
	}
	
	@Override
	public boolean update(ItemLocacao itemLocacao) {
		return false;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}

	@Override
	public List<ItemLocacao> findAll() {
		return null;
	}

	@Override
	public ItemLocacao findById(int id) {
		return null;
	}	

}