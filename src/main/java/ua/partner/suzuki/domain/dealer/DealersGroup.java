package ua.partner.suzuki.domain.dealer;

import java.util.ArrayList;
import java.util.Collection;

public class DealersGroup {
	
	public DealersGroup(Dealer dealer){
		setDealersGroup(dealer);
	}
	
	private Collection<Dealer> dealers;
	
	public Collection<Dealer> getDealersGroup() {
		return dealers;
	}

	public void setDealersGroup(Dealer dealer) {
		this.dealers = addDealer(dealer);
	}
	
	public Collection<Dealer> addDealer(Dealer dealer){
		Collection<Dealer> dealers = new ArrayList<Dealer>();
		dealers.add(dealer);
	return dealers;
	}
}
