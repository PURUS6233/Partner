package ua.partner.suzuki.domain;

import java.util.Collection;

public class DealersGroup {
	
	private Collection<Dealer> dealersGroup;
	
	public void addCustomer(Dealer dealer){
		dealersGroup.add(dealer);
	}
}
