package in.co.rays.proj3.model.jdbcImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.proj3.dto.SubjectDTO;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.SubjectModelInt;
import in.co.rays.proj3.utill.JDBCDataSource;

/**
 * JDBC implements of Subject model
 * @author Aniket rajput
 *
 */
public class SubjectModelJDBCImpl implements SubjectModelInt {

    private static Logger log = Logger.getLogger(SubjectModelJDBCImpl.class);

    @Override
    public long add(SubjectDTO dto) throws DatabaseException, DuplicateRecordException {
        log.debug("Model add Started");
        Connection conn = null;
        long pk = 0L;
        SubjectDTO exist = findByName(dto.getName());
        if(exist != null) {
            log.error("Subject already exists");
            throw new DuplicateRecordException("Subject already Exist");
        }
        try {
            conn = JDBCDataSource.getConnection();
            pk = getNextPk();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("insert into st_subject values(?,?,?,?,?,?,?,?,?)");
            pstmt.setLong(1, pk);
            pstmt.setString(2, dto.getName());
            pstmt.setLong(3, dto.getCourseId());
            pstmt.setString(4, dto.getCourseName());
            pstmt.setString(5, dto.getDescription());
            pstmt.setString(6, dto.getCreatedBy());
            pstmt.setString(7, dto.getModifiedBy());
            pstmt.setTimestamp(8, dto.getCreatedDateTime());
            pstmt.setTimestamp(9, dto.getModifiedDateTime());
            int i = pstmt.executeUpdate();
            log.info("New Subject Added " + i);
            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new DatabaseException("Exception : Add rollback exception " + ex.getMessage());
            }
            e.printStackTrace();
            throw new DatabaseException("Exception : Exception in Add Subject");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model add End");
        return pk;
    }

    @Override
    public void delete(SubjectDTO dto) throws DatabaseException {
        delete(dto.getId());
    }

    public void delete(long id) throws DatabaseException {
        log.debug("Model delete Started");
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("delete from st_subject where id = ?");
            pstmt.setLong(1, id);
            int i = pstmt.executeUpdate();
            log.info("Subject deleted " + i);
            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new DatabaseException("Exception : Delete rollback exception " + ex.getMessage());
            }
            e.printStackTrace();
            throw new DatabaseException("Exception : Exception in delete Subject");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model delete End");
    }

    @Override
    public void update(SubjectDTO dto) throws DatabaseException, DuplicateRecordException {
        log.debug("Model update Started");
        Connection conn = null;
        SubjectDTO exist = findByName(dto.getName());
        if(exist != null && exist.getId() != dto.getId()) {
            log.error("Subject already exists");
            throw new DuplicateRecordException("Subject already Exist");
        }
        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_subject set name = ?, course_id = ?, course_name = ?, description = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");
            pstmt.setString(1, dto.getName());
            pstmt.setLong(2, dto.getCourseId());
            pstmt.setString(3, dto.getCourseName());
            pstmt.setString(4, dto.getDescription());
            pstmt.setString(5, dto.getCreatedBy());
            pstmt.setString(6, dto.getModifiedBy());
            pstmt.setTimestamp(7, dto.getCreatedDateTime());
            pstmt.setTimestamp(8, dto.getModifiedDateTime());
            pstmt.setLong(9, dto.getId());
            int i = pstmt.executeUpdate();
            log.info("Subject updated " + i);
            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new DatabaseException("Exception : Update rollback exception " + ex.getMessage());
            }
            e.printStackTrace();
            throw new DatabaseException("Exception : Exception in Update Subject");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model update End");
    }

    @Override
    public List<SubjectDTO> list() throws DatabaseException {
        log.debug("Model list Started");
        return search(null, 0, 0);
    }

    @Override
    public List<SubjectDTO> list(int pageNo, int pageSize) throws DatabaseException {
        log.debug("Model list Started");
        return search(null, pageNo, pageSize);
    }

    @Override
    public List<SubjectDTO> search(SubjectDTO dto) throws DatabaseException {
        log.debug("Model search Started");
        return search(dto, 0, 0);
    }

    @Override
    public List<SubjectDTO> search(SubjectDTO dto, int pageNo, int pageSize) throws DatabaseException {
        log.debug("Model search Started");
        Connection conn = null;
        StringBuffer sql = new StringBuffer("select * from st_subject where 1 = 1");
        List<SubjectDTO> subjectList = new ArrayList<SubjectDTO>();

        if (dto != null) {
            if (dto.getId() > 0) {
                sql.append(" and id = " + dto.getId());
            }
            if (dto.getName() != null && dto.getName().length() > 0) {
                sql.append(" and name like '" + dto.getName() + "%'");
            }
            if (dto.getCourseId() != null && dto.getCourseId() > 0) {
                sql.append(" and course_id like '" + dto.getCourseId() + "%'");
            }
            if (dto.getCourseName() != null && dto.getCourseName().length() > 0) {
                sql.append(" and course_name like '" + dto.getCourseName() + "%'");
            }
            if (dto.getDescription() != null && dto.getDescription().length() > 0) {
                sql.append(" and description like '" + dto.getDescription() + "%'");
            }
        }
        
        if(pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + ", " + pageSize);
        }

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                dto = new SubjectDTO();
                dto.setId(rs.getLong(1));
                dto.setName(rs.getString(2));
                dto.setCourseId(rs.getLong(3));
                dto.setCourseName(rs.getString(4));
                dto.setDescription(rs.getString(5));
                dto.setCreatedBy(rs.getString(6));
                dto.setModifiedBy(rs.getString(7));
                dto.setCreatedDateTime(rs.getTimestamp(8));
                dto.setModifiedDateTime(rs.getTimestamp(9));
                subjectList.add(dto);
            }
            pstmt.close();
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            e.printStackTrace();
            throw new DatabaseException("Exception : Exception in search Subject");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model search End");
        return subjectList;
    }

    @Override
    public SubjectDTO findByPK(long pk) throws DatabaseException {
        log.debug("Model findByPK Started");
        Connection conn = null;
        SubjectDTO dto = null;
        StringBuffer sql = new StringBuffer("select * from st_subject where id = ?");
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dto = new SubjectDTO();
                dto.setId(rs.getLong(1));
                dto.setName(rs.getString(2));
                dto.setCourseId(rs.getLong(3));
                dto.setCourseName(rs.getString(4));
                dto.setDescription(rs.getString(5));
                dto.setCreatedBy(rs.getString(6));
                dto.setModifiedBy(rs.getString(7));
                dto.setCreatedDateTime(rs.getTimestamp(8));
                dto.setModifiedDateTime(rs.getTimestamp(9));
            }
            pstmt.close();
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            e.printStackTrace();
            throw new DatabaseException("Exception : Exception in findByPK Subject");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model findByPK End");
        return dto;
    }

    @Override
    public SubjectDTO findByName(String name) throws DatabaseException {
        log.debug("Model findByName Started");
        Connection conn = null;
        SubjectDTO dto = null;
        StringBuffer sql = new StringBuffer("select * from st_subject where name = ?");
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dto = new SubjectDTO();
                dto.setId(rs.getLong(1));
                dto.setName(rs.getString(2));
                dto.setCourseId(rs.getLong(3));
                dto.setCourseName(rs.getString(4));
                dto.setDescription(rs.getString(5));
                dto.setCreatedBy(rs.getString(6));
                dto.setModifiedBy(rs.getString(7));
                dto.setCreatedDateTime(rs.getTimestamp(8));
                dto.setModifiedDateTime(rs.getTimestamp(9));
            }
            pstmt.close();
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            e.printStackTrace();
            throw new DatabaseException("Exception : Exception in findByName Subject");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model findByName End");
        return dto;
    }

    private long getNextPk() throws DatabaseException {
        log.debug("Model getNextPk Started");
        Connection conn = null;
        long pk = 0L;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_subject");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                pk = rs.getLong(1);
            }
            pstmt.close();
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            throw new DatabaseException("Exception : Exception In Getting pk");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return pk + 1L;
    }
}