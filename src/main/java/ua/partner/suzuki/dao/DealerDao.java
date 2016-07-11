package ua.partner.suzuki.dao;

import java.util.List;

import ua.partner.suzuki.domain.Dealer;

public interface DealerDao {
	
	Dealer get(String name);
	
	List<Dealer> getAll();

}
