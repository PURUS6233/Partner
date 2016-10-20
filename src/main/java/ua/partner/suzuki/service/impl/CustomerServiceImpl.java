package ua.partner.suzuki.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import ua.partner.suzuki.dao.CustomerDao;
import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.impl.CustomerDaoImpl;
import ua.partner.suzuki.domain.customer.Customer;
import ua.partner.suzuki.service.CustomerService;
import ua.partner.suzuki.service.ServiceException;

public class CustomerServiceImpl  extends AbstractService<Customer> implements CustomerService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	private CustomerDao obmDao = new CustomerDaoImpl();

	@Override
	protected Class<CustomerServiceImpl> getEntityClass() {
		return CustomerServiceImpl.class;
	}

	@Override
	protected CustomerDao getDaoEntity() {
		return obmDao;
	}
	
	@Override
	public Customer add(Customer customer) throws ServiceException {
		try {
			// Read Data from Json file to map
			getDaoEntity().init();
			// Check if the entity already exists in database
			logger.info("Check if entity is already exist", getEntityClass()
					.getSimpleName());
			Preconditions
					.checkState(!getDaoEntity().isExist(customer.getEngineNumber()));
			getDaoEntity().add(customer);
			getDaoEntity().writeMapToFile();

		} catch (IllegalStateException e) {
			logger.error("Entity with this engine number already exists!", e);
			throw new ServiceException("Can not add entity to map.", e);
		} catch (DAOException e) {
			logger.error("Problems occured while writing entity to json!", e);
			throw new ServiceException("Can not add entity to file.", e);
		}
		return customer;
	}
}
