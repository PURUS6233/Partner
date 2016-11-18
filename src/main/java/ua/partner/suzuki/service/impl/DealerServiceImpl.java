package ua.partner.suzuki.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.DealerDao;
import ua.partner.suzuki.dao.postgres.PostgreDealerDao;
import ua.partner.suzuki.domain.dealer.Dealer;
import ua.partner.suzuki.service.DealerService;
import ua.partner.suzuki.service.ServiceException;

public class DealerServiceImpl implements DealerService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private DealerDao obmDao = new PostgreDealerDao();

	protected DealerDao getDaoEntity() {
		return obmDao;
	}

	protected Class<DealerServiceImpl> getEntityClass() {
		return DealerServiceImpl.class;
	}

	@Override
	public Dealer add(Dealer dealer) throws ServiceException {
		try {
			// Read Data from Json file to map
			getDaoEntity().init();
			// Check if the entity already exists in database
			logger.info("Check if entity is already exist", getEntityClass()
					.getSimpleName());
			Preconditions
					.checkState(!getDaoEntity().isExist(dealer.getLogin()));
			getDaoEntity().add(dealer);
			getDaoEntity().writeMapToFile();

		} catch (IllegalStateException e) {
			logger.error("Entity with this engine number already exists!", e);
			throw new ServiceException("Can not add entity to map.", e);
		} catch (DAOException e) {
			logger.error("Problems occured while writing entity to json!", e);
			throw new ServiceException("Can not add entity to file.", e);
		}
		return dealer;
	}

	@Override
	public Dealer get(String login) throws ServiceException {
		Dealer response;
		try {
			getDaoEntity().init();
			Preconditions.checkState(getDaoEntity().isExist(login));
			response = getDaoEntity().get(login);
		} catch (IllegalStateException e) {
			logger.error("Entity with this engine number already exists!", e);
			throw new ServiceException("Can not retrieve entity.", e);
		} catch (DAOException e) {
			logger.error("Can not read json file", e);
			throw new ServiceException("Can not retrieve entity", e);
		}
		return response;
	}

	@Override
	public List<Dealer> getAll() throws ServiceException {
		try {
			getDaoEntity().init();
			return getDaoEntity().getAll();
		} catch (DAOException e) {
			logger.error("Can not read entities from database", e);
			throw new ServiceException("Can not read entities from database", e);
		}
	}

	@Override
	public Dealer update(Dealer entity) throws ServiceException {
		Dealer response;
		try {
			getDaoEntity().init();
			logger.info("Entity of class '{}' updating to Map",
					getEntityClass().getSimpleName());
			response = getDaoEntity().update(entity.getLogin(), entity);
			logger.info("Writing data to json '{}'", getEntityClass()
					.getSimpleName());
			getDaoEntity().writeMapToFile();
		} catch (DAOException e) {
			logger.error("Problem occured during updating entitye"
					+ getEntityClass().getSimpleName(), e);
			throw new ServiceException(
					"Problem occured during updating entity", e);
		}
		return response;
	}

	@Override
	public Dealer remove(String login) throws ServiceException {
		Dealer response;
		try {
			getDaoEntity().init();
			logger.info("Entity of class '{}' updating to Map",
					getEntityClass().getSimpleName());
			Preconditions.checkState(getDaoEntity().isExist(login));
			response = getDaoEntity().get(login);
			getDaoEntity().delete(login);
			getDaoEntity().writeMapToFile();
		} catch (DAOException e) {
			logger.error("Problem occured during entity deleating"
					+ getEntityClass().getSimpleName(), e);
			throw new ServiceException(
					"Problem occured during entity deleating", e);
		}
		return response;
	}
}
