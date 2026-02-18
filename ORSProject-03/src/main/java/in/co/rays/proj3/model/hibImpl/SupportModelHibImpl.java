package in.co.rays.proj3.model.hibImpl;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.SupportDTO;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.SupportModelInt;
import in.co.rays.proj3.utill.HibDataSource;

public class SupportModelHibImpl implements SupportModelInt {

    @Override
    public long add(SupportDTO dto) throws DatabaseException, DuplicateRecordException {

        SupportDTO existDto = findByUserName(dto.getUserName());
        if (existDto != null) {
            throw new DuplicateRecordException("User Name already exists");
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
            throw new DatabaseException("Exception in Support add " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(SupportDTO dto) throws DatabaseException {

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
            throw new DatabaseException("Exception in Support delete " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void update(SupportDTO dto) throws DatabaseException, DuplicateRecordException {

        SupportDTO existDto = findByUserName(dto.getUserName());
        if (existDto != null && existDto.getId() != dto.getId()) {
            throw new DuplicateRecordException("User Name already exists");
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
            throw new DatabaseException("Exception in Support update " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public SupportDTO findByPK(long pk) throws DatabaseException {

        Session session = null;
        SupportDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (SupportDTO) session.get(SupportDTO.class, pk);

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in getting Support by PK");
        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public SupportDTO findByUserName(String userName) throws DatabaseException {

        Session session = null;
        SupportDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(SupportDTO.class);
            criteria.add(Restrictions.eq("userName", userName));

            List list = criteria.list();
            if (list.size() == 1) {
                dto = (SupportDTO) list.get(0);
            }

        } catch (HibernateException e) {
            e.printStackTrace();
            throw new DatabaseException("Exception in findByUserName Support " + e.getMessage());
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
            Criteria criteria = session.createCriteria(SupportDTO.class);

            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in Support list");
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(SupportDTO dto, int pageNo, int pageSize) throws DatabaseException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(SupportDTO.class);

            if (dto != null) {

                if (dto.getId() != null && dto.getId() > 0) {
                    criteria.add(Restrictions.eq("id", dto.getId()));
                }

                if (dto.getUserName() != null && dto.getUserName().length() > 0) {
                    criteria.add(Restrictions.like("userName", dto.getUserName() + "%"));
                }

                if (dto.getIssueType() != null && dto.getIssueType().length() > 0) {
                    criteria.add(Restrictions.like("issueType", dto.getIssueType() + "%"));
                }

                if (dto.getIssueDescription() != null && dto.getIssueDescription().length() > 0) {
                    criteria.add(Restrictions.like("issueDescription", dto.getIssueDescription() + "%"));
                }
                
                if (dto.getTicketStatus() != null && dto.getTicketStatus().length() > 0) {
                    criteria.add(Restrictions.like("ticketStatus", dto.getTicketStatus() + "%"));
                }
            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in Support search");
        } finally {
            session.close();
        }

        return list;
    }
}