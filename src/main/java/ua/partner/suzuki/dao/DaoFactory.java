package ua.partner.suzuki.dao;

/** Object Factory for DB communication**/
public interface DaoFactory<C> {

    public interface DaoCreator<C> {
        public GenericDao create(C context);
    }

    public C getConnection() throws DAOException;

    public GenericDao getDao(C context, Class dtoClass) throws DAOException;
}