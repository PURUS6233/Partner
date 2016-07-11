package ua.partner.suzuki.dao.impl.lile;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import ua.partner.suzuki.dao.CustomerDao;
import ua.partner.suzuki.domain.Customer;

public class CustomerDaoImpl extends AbstractFileDao<Customer> implements CustomerDao {

	@Override
	protected Class getEntityClass() {
		return Customer.class;
	}

	@Override
	protected Type getListType() {
		return new TypeToken<List<Customer>>(){}.getType();
	}

	@Override
	protected String getFileName() {
		return "customers.json";
	}

}
