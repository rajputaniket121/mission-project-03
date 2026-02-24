package in.co.rays.proj3.model.hibImpl;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.AnnouncementDTO;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.AnnouncementModelInt;
import in.co.rays.proj3.utill.HibDataSource;

public class AnnouncementModelHibImpl implements AnnouncementModelInt {

    @Override
    public long add(AnnouncementDTO dto) throws DatabaseException, DuplicateRecordException {

        AnnouncementDTO existDto = findByAnnouncementCode(dto.getAnnouncementCode());
        if (existDto != null) {
            throw new DuplicateRecordException("Announcement Code already exists");
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
            throw new DatabaseException("Exception in Announcement add " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(AnnouncementDTO dto) throws DatabaseException {

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
            throw new DatabaseException("Exception in Announcement delete " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void update(AnnouncementDTO dto) throws DatabaseException, DuplicateRecordException {

        AnnouncementDTO existDto = findByAnnouncementCode(dto.getAnnouncementCode());
        if (existDto != null && existDto.getId() != dto.getId()) {
            throw new DuplicateRecordException("Announcement Code already exists");
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
            throw new DatabaseException("Exception in Announcement update " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public AnnouncementDTO findByPK(long pk) throws DatabaseException {

        Session session = null;
        AnnouncementDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (AnnouncementDTO) session.get(AnnouncementDTO.class, pk);

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in getting Announcement by PK");
        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public AnnouncementDTO findByAnnouncementCode(String announcementCode) throws DatabaseException {

        Session session = null;
        AnnouncementDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(AnnouncementDTO.class);
            criteria.add(Restrictions.eq("announcementCode", announcementCode));

            List list = criteria.list();
            if (list.size() == 1) {
                dto = (AnnouncementDTO) list.get(0);
            }

        } catch (HibernateException e) {
            e.printStackTrace();
            throw new DatabaseException("Exception in findByAnnouncementCode Announcement " + e.getMessage());
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
            Criteria criteria = session.createCriteria(AnnouncementDTO.class);

            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in Announcement list");
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(AnnouncementDTO dto, int pageNo, int pageSize) throws DatabaseException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(AnnouncementDTO.class);

            if (dto != null) {

                if (dto.getId() != null && dto.getId() > 0) {
                    criteria.add(Restrictions.eq("id", dto.getId()));
                }

                if (dto.getAnnouncementCode() != null && dto.getAnnouncementCode().length() > 0) {
                    criteria.add(Restrictions.like("announcementCode", dto.getAnnouncementCode() + "%"));
                }

                if (dto.getTitle() != null && dto.getTitle().length() > 0) {
                    criteria.add(Restrictions.like("title", dto.getTitle() + "%"));
                }

                if (dto.getDescription() != null && dto.getDescription().length() > 0) {
                    criteria.add(Restrictions.like("description", dto.getDescription() + "%"));
                }
                
                if (dto.getPublishDate() != null) {
                    criteria.add(Restrictions.eq("publishDate", dto.getPublishDate()));
                }
            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in Announcement search");
        } finally {
            session.close();
        }

        return list;
    }
}