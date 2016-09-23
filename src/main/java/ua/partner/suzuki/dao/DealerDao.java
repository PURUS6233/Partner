package ua.partner.suzuki.dao;

import java.util.List;

import ua.partner.suzuki.domain.dealer.Dealer;

public interface DealerDao {
	
	boolean find (String login);
	
	void add (Dealer entity);
	
	Dealer get(String login, String password) throws DAOException;
	
	List<Dealer> getAll();
	
	void update (Dealer entity, String login);
	
	void delete (String login);
}
