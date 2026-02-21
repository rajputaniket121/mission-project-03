package in.co.rays.proj3.model.hibImpl;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.ShiftDTO;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.ShiftModelInt;
import in.co.rays.proj3.utill.HibDataSource;

public class ShiftModelHibImpl implements ShiftModelInt {

    @Override
    public long add(ShiftDTO dto) throws DatabaseException, DuplicateRecordException {

        ShiftDTO existDto = findByShiftCode(dto.getShiftCode());
        if (existDto != null) {
            throw new DuplicateRecordException("Shift Code already exists");
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
            throw new DatabaseException("Exception in Shift add " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(ShiftDTO dto) throws DatabaseException {

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
            throw new DatabaseException("Exception in Shift delete " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void update(ShiftDTO dto) throws DatabaseException, DuplicateRecordException {

        ShiftDTO existDto = findByShiftCode(dto.getShiftCode());
        if (existDto != null && existDto.getId() != dto.getId()) {
            throw new DuplicateRecordException("Shift Code already exists");
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
            throw new DatabaseException("Exception in Shift update " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public ShiftDTO findByPK(long pk) throws DatabaseException {

        Session session = null;
        ShiftDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (ShiftDTO) session.get(ShiftDTO.class, pk);

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in getting Shift by PK");
        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public ShiftDTO findByShiftCode(String shiftCode) throws DatabaseException {

        Session session = null;
        ShiftDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(ShiftDTO.class);
            criteria.add(Restrictions.eq("shiftCode", shiftCode));

            List list = criteria.list();
            if (list.size() == 1) {
                dto = (ShiftDTO) list.get(0);
            }

        } catch (HibernateException e) {
            e.printStackTrace();
            throw new DatabaseException("Exception in findByShiftCode Shift " + e.getMessage());
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
            Criteria criteria = session.createCriteria(ShiftDTO.class);

            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in Shift list");
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(ShiftDTO dto, int pageNo, int pageSize) throws DatabaseException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(ShiftDTO.class);

            if (dto != null) {

                if (dto.getId() != null && dto.getId() > 0) {
                    criteria.add(Restrictions.eq("id", dto.getId()));
                }

                if (dto.getShiftCode() != null && dto.getShiftCode().length() > 0) {
                    criteria.add(Restrictions.like("shiftCode", dto.getShiftCode() + "%"));
                }

                if (dto.getShiftName() != null && dto.getShiftName().length() > 0) {
                    criteria.add(Restrictions.like("shiftName", dto.getShiftName() + "%"));
                }

                if (dto.getShiftStatus() != null && dto.getShiftStatus().length() > 0) {
                    criteria.add(Restrictions.like("shiftStatus", dto.getShiftStatus() + "%"));
                }
            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in Shift search");
        } finally {
            session.close();
        }

        return list;
    }
}