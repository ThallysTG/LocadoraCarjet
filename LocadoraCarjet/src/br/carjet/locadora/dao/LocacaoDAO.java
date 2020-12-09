package br.carjet.locadora.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.carjet.locadora.model.ItemLocacao;
import br.carjet.locadora.model.Carros;
import br.carjet.locadora.model.TipoUsuario;
import br.carjet.locadora.model.Usuario;
import br.carjet.locadora.model.Locacao;

public class LocacaoDAO extends DAO<Locacao> {
	
	@Override
	public boolean create(Locacao locacao) {
		
		boolean retorno = false;
		Connection conn = getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO public.locacao");
		sql.append(" (data, idusuario) ");
		sql.append("VALUES ");
		sql.append(" (?, ?) " );
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			stat.setDate(1, Date.valueOf(locacao.getData()));
			stat.setInt(2, locacao.getUsuario().getId());
			
			stat.execute();
			
			// obtendo o id gerado pela tabela do banco de dados
			ResultSet rs = stat.getGeneratedKeys();
			rs.next();
			locacao.setId(rs.getInt("id"));
			// inserindo os itens de locacao
			
			ItemLocacaoDAO dao = new ItemLocacaoDAO();
			for (ItemLocacao itemLocacao : locacao.getListaItemLocacao()) {
				// informando quem eh o pai da crianca
				itemLocacao.setLocacao(locacao);
				// salvando no banco de dados
//				if (dao.create(itemLocacao) == false) {
//					throw new Exception("Erro ao incluir um item de locacao");
//				}
				if (createItemLocacao(itemLocacao, conn) == false) {
					throw new Exception("Erro ao incluir um item de locacao");
				}				
				
			}
			
			conn.commit();

			System.out.println("Inclusão realizada com sucesso.");
			
			retorno = true;

		} catch (SQLException e) {
			e.printStackTrace();
			rollback(conn);
		} catch (Exception e) {
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeStatement(stat);
			closeConnection(conn);
		}
		return retorno;	
		
	}
	
	// 
	private boolean createItemLocacao(ItemLocacao itemLocacao, Connection conn) {
		
		boolean retorno = false;
//		Connection conn = getConnection();
		
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
			
//			conn.commit();
			
			retorno = true;
		} catch (SQLException e) {
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeStatement(stat);
	//		closeConnection(conn);
		}
		return retorno;	
		
	}
	
	public List<Locacao> findByUsuario(int idUsuario) {
		List<Locacao> listaLocacao = new ArrayList<Locacao>();
		Connection conn = getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  v.id, ");
		sql.append("  v.data, ");
		sql.append("  u.id as idusuario, ");
		sql.append("  u.nome, ");
		sql.append("  u.login,  ");
		sql.append("  u.senha, ");
		sql.append("  u.email, ");
		sql.append("  u.tipousuario, ");
		sql.append("  u.datanascimento ");					
		sql.append("FROM ");
		sql.append("  public.locacao v, ");
		sql.append("  public.usuario u ");
		sql.append("WHERE ");
		sql.append("  v.idusuario = u.id AND ");
		sql.append("  u.id = ? ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());

			stat.setInt(1, idUsuario);
			
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				Locacao locacao = new Locacao();
				locacao.setId(rs.getInt("id"));
				locacao.setData( rs.getDate("data").toLocalDate() );
				locacao.setUsuario(new Usuario());
				locacao.getUsuario().setId(rs.getInt("idusuario"));
				locacao.getUsuario().setNome(rs.getString("nome"));
				locacao.getUsuario().setLogin(rs.getString("login"));
				locacao.getUsuario().setSenha(rs.getString("senha"));
				locacao.getUsuario().setEmail(rs.getString("email"));
				locacao.getUsuario().setTipoUsuario(TipoUsuario.valueOf(rs.getInt("tipousuario")));
				Date data = rs.getDate("datanascimento");
				locacao.getUsuario().setDataNascimento(data == null? null : data.toLocalDate());
				// e os itens de locacao?!!?
				ItemLocacaoDAO dao = new ItemLocacaoDAO();
				locacao.setListaItemLocacao(dao.findByLocacao(locacao));
				listaLocacao.add(locacao);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeStatement(stat);
			closeConnection(conn);
		}
		return listaLocacao;
	}

	@Override
	public List<Locacao> findAll() {
		List<Locacao> listaLocacao = new ArrayList<Locacao>();
		Connection conn = getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  v.id, ");
		sql.append("  v.data, ");
		sql.append("  u.idusuario, ");
		sql.append("  u.nome, ");
		sql.append("  u.login,  ");
		sql.append("  u.senha, ");
		sql.append("  u.email, ");
		sql.append("  u.tipousuario, ");
		sql.append("  u.datanascimento ");					
		sql.append("FROM ");
		sql.append("  public.locacao v, ");
		sql.append("  public.usuario u ");
		sql.append("WHERE ");
		sql.append("  v.idusuario = u.id ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
					
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				Locacao locacao = new Locacao();
				locacao.setId(rs.getInt("id"));
				locacao.setData( rs.getDate("data").toLocalDate() );
				locacao.setUsuario(new Usuario());
				locacao.getUsuario().setId(rs.getInt("idusuario"));
				locacao.getUsuario().setNome(rs.getString("nome"));
				locacao.getUsuario().setLogin(rs.getString("login"));
				locacao.getUsuario().setSenha(rs.getString("senha"));
				locacao.getUsuario().setEmail(rs.getString("email"));
				locacao.getUsuario().setTipoUsuario(TipoUsuario.valueOf(rs.getInt("tipousuario")));
				Date data = rs.getDate("datanascimento");
				locacao.getUsuario().setDataNascimento(data == null? null : data.toLocalDate());
				// e os itens de locacao?!!?
				//locacao.setListaItemLocacao(listaItemLocacao);
				
				listaLocacao.add(locacao);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeStatement(stat);
			closeConnection(conn);
		}
		return listaLocacao;
	}
	
	@Override
	public Locacao findById(int id) {
		Locacao locacao = null;
		Connection conn = getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  v.id, ");
		sql.append("  v.data, ");
		sql.append("  u.idusuario, ");
		sql.append("  u.nome, ");
		sql.append("  u.login,  ");
		sql.append("  u.senha, ");
		sql.append("  u.email, ");
		sql.append("  u.tipousuario, ");
		sql.append("  u.datanascimento ");					
		sql.append("FROM ");
		sql.append("  public.locacao v, ");
		sql.append("  public.usuario u ");
		sql.append("WHERE ");
		sql.append("  v.idusuario = u.id AND ");
		sql.append("  u.id = ? ");

		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, id);
			
			ResultSet rs = stat.executeQuery();
			while(rs.next()) {
				locacao = new Locacao();
				locacao.setId(rs.getInt("id"));
				locacao.setData( rs.getDate("data").toLocalDate() );
				locacao.setUsuario(new Usuario());
				locacao.getUsuario().setId(rs.getInt("idusuario"));
				locacao.getUsuario().setNome(rs.getString("nome"));
				locacao.getUsuario().setLogin(rs.getString("login"));
				locacao.getUsuario().setSenha(rs.getString("senha"));
				locacao.getUsuario().setEmail(rs.getString("email"));
				locacao.getUsuario().setTipoUsuario(TipoUsuario.valueOf(rs.getInt("tipousuario")));
				Date data = rs.getDate("datanascimento");
				locacao.getUsuario().setDataNascimento(data == null? null : data.toLocalDate());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeStatement(stat);
			closeConnection(conn);
		}
		return locacao;
	}			

	
	@Override
	public boolean update(Locacao locacao)  {
		return false;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}

}