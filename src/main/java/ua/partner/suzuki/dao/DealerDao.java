package ua.partner.suzuki.dao;

import java.util.List;

import ua.partner.suzuki.domain.dealer.Dealer;

public interface DealerDao {

	boolean isExist(String login);

	Dealer add(Dealer entity);

	Dealer get(String login) throws DAOException;

	List<Dealer> getAll() throws DAOException;

	Dealer update(String login, Dealer entity);

	boolean delete(String login);

	boolean init() throws DAOException;

	boolean writeMapToFile() throws DAOException;
}
