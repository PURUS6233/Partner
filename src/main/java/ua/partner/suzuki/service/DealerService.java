package ua.partner.suzuki.service;

import java.util.List;

import ua.partner.suzuki.domain.dealer.Dealer;

public interface DealerService {
	
	Dealer add (Dealer dealer) throws ServiceException;
	
	Dealer get(String Login) throws ServiceException;
	
	List<Dealer> getAll() throws ServiceException ;
	
	Dealer update (Dealer entity) throws ServiceException ;
	
	Dealer remove (String login) throws ServiceException ;

}
