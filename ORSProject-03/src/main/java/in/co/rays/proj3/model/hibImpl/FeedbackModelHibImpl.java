package in.co.rays.proj3.model.hibImpl;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.FeedbackDTO;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.FeedbackModelInt;
import in.co.rays.proj3.utill.HibDataSource;

public class FeedbackModelHibImpl implements FeedbackModelInt {

    @Override
    public long add(FeedbackDTO dto) throws DatabaseException, DuplicateRecordException {

        FeedbackDTO existDto = findByFeedbackCode(dto.getFeedbackCode());
        if (existDto != null) {
            throw new DuplicateRecordException("Feedback Code already exists");
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
            throw new DatabaseException("Exception in Feedback add " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(FeedbackDTO dto) throws DatabaseException {

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
            throw new DatabaseException("Exception in Feedback delete " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void update(FeedbackDTO dto) throws DatabaseException, DuplicateRecordException {

        FeedbackDTO existDto = findByFeedbackCode(dto.getFeedbackCode());
        if (existDto != null && existDto.getId() != dto.getId()) {
            throw new DuplicateRecordException("Feedback Code already exists");
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
            throw new DatabaseException("Exception in Feedback update " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public FeedbackDTO findByPK(long pk) throws DatabaseException {

        Session session = null;
        FeedbackDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (FeedbackDTO) session.get(FeedbackDTO.class, pk);

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in getting Feedback by PK");
        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public FeedbackDTO findByFeedbackCode(String feedbackCode) throws DatabaseException {

        Session session = null;
        FeedbackDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(FeedbackDTO.class);
            criteria.add(Restrictions.eq("feedbackCode", feedbackCode));

            List list = criteria.list();
            if (list.size() == 1) {
                dto = (FeedbackDTO) list.get(0);
            }

        } catch (HibernateException e) {
            e.printStackTrace();
            throw new DatabaseException("Exception in findByFeedbackCode Feedback " + e.getMessage());
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
            Criteria criteria = session.createCriteria(FeedbackDTO.class);

            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in Feedback list");
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(FeedbackDTO dto, int pageNo, int pageSize) throws DatabaseException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(FeedbackDTO.class);

            if (dto != null) {

                if (dto.getId() != null && dto.getId() > 0) {
                    criteria.add(Restrictions.eq("id", dto.getId()));
                }

                if (dto.getFeedbackCode() != null && dto.getFeedbackCode().length() > 0) {
                    criteria.add(Restrictions.like("feedbackCode", dto.getFeedbackCode() + "%"));
                }

                if (dto.getCustomerName() != null && dto.getCustomerName().length() > 0) {
                    criteria.add(Restrictions.like("customerName", dto.getCustomerName() + "%"));
                }

                if (dto.getRating() != null && dto.getRating().length() > 0) {
                    criteria.add(Restrictions.like("rating", dto.getRating()+ "%"));
                }

                if (dto.getComments() != null && dto.getComments().length() > 0) {
                    criteria.add(Restrictions.like("comments", dto.getComments() + "%"));
                }
                
                if (dto.getFeedbackDate() != null) {
                    criteria.add(Restrictions.eq("feedbackDate", dto.getFeedbackDate()));
                }
            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in Feedback search");
        } finally {
            session.close();
        }

        return list;
    }
}