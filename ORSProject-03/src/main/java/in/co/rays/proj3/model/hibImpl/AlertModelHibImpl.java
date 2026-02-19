package in.co.rays.proj3.model.hibImpl;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.AlertDTO;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.AlertModelInt;
import in.co.rays.proj3.utill.HibDataSource;

public class AlertModelHibImpl implements AlertModelInt {

    @Override
    public long add(AlertDTO dto) throws DatabaseException, DuplicateRecordException {

        AlertDTO existDto = findByAlertCode(dto.getAlertCode());
        if (existDto != null) {
            throw new DuplicateRecordException("Alert Code already exists");
        }

        Session session = HibDataSource.getSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.save(dto);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DatabaseException("Exception in Alert add " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(AlertDTO dto) throws DatabaseException {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.delete(dto);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DatabaseException("Exception in Alert delete " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void update(AlertDTO dto) throws DatabaseException, DuplicateRecordException {

        AlertDTO existDto = findByAlertCode(dto.getAlertCode());
        if (existDto != null && existDto.getId() != dto.getId()) {
            throw new DuplicateRecordException("Alert Code already exists");
        }

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(dto);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DatabaseException("Exception in Alert update " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public AlertDTO findByPK(long pk) throws DatabaseException {

        Session session = null;
        AlertDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (AlertDTO) session.get(AlertDTO.class, pk);

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in getting Alert by PK");
        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public AlertDTO findByAlertCode(String alertCode) throws DatabaseException {

        Session session = null;
        AlertDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(AlertDTO.class);
            criteria.add(Restrictions.eq("alertCode", alertCode));

            List list = criteria.list();
            if (list.size() == 1) {
                dto = (AlertDTO) list.get(0);
            }

        } catch (HibernateException e) {
            e.printStackTrace();
            throw new DatabaseException("Exception in findByAlertCode Alert " + e.getMessage());
        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public List list() throws DatabaseException {
        return list(0, 0);
    }

    public List list(int pageNo, int pageSize) throws DatabaseException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(AlertDTO.class);

            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in Alert list");
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(AlertDTO dto, int pageNo, int pageSize) throws DatabaseException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(AlertDTO.class);

            if (dto != null) {

                if (dto.getId() != null && dto.getId() > 0) {
                    criteria.add(Restrictions.eq("id", dto.getId()));
                }

                if (dto.getAlertCode() != null && dto.getAlertCode().length() > 0) {
                    criteria.add(Restrictions.like("alertCode", dto.getAlertCode() + "%"));
                }

                if (dto.getAlertType() != null && dto.getAlertType().length() > 0) {
                    criteria.add(Restrictions.like("alertType", dto.getAlertType() + "%"));
                }

                if (dto.getAlertTime() != null) {
                    criteria.add(Restrictions.eq("alertTime", dto.getAlertTime()));
                }
                
                if (dto.getAlertStatus() != null && dto.getAlertStatus().length() > 0) {
                    criteria.add(Restrictions.like("alertStatus", dto.getAlertStatus() + "%"));
                }
            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in Alert search");
        } finally {
            session.close();
        }

        return list;
    }
}