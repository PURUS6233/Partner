package ua.partner.suzuki.service;

import java.util.List;

import ua.partner.suzuki.domain.obm.Registration;

public interface RegistrationService {

	Registration add(Registration registration) throws ServiceException;

	Registration get(String engineNumber) throws ServiceException;

	List<Registration> getAll() throws ServiceException;

	Registration update(Registration entity) throws ServiceException;

	Registration remove(String engineNumber) throws ServiceException;

}
