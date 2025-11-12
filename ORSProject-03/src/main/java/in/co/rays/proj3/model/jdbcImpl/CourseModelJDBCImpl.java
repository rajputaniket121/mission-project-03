package in.co.rays.proj3.model.jdbcImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.proj3.dto.CourseDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.CourseModelInt;
import in.co.rays.proj3.utill.JDBCDataSource;

/**
 * JDBC implements of Course model
 * @author Aniket Rajput
 *
 */
public class CourseModelJDBCImpl implements CourseModelInt {

    private static Logger log = Logger.getLogger(CourseModelJDBCImpl.class);

    @Override
    public long add(CourseDTO dto) throws ApplicationException, DuplicateRecordException {
        log.debug("Model add Started");
        Connection conn = null;
        long pk = 0L;
        CourseDTO exist = findByName(dto.getCourseName());
        if (exist != null) {
            log.error("Course already exists");
            throw new DuplicateRecordException("Course already Exist");
        }
        try {
            conn = JDBCDataSource.getConnection();
            pk = getNextPk();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("insert into st_course values(?,?,?,?,?,?,?,?)");
            pstmt.setLong(1, pk);
            pstmt.setString(2, dto.getCourseName());
            pstmt.setString(3, dto.getDescription());
            pstmt.setString(4, dto.getDuration());
            pstmt.setString(5, dto.getCreatedBy());
            pstmt.setString(6, dto.getModifiedBy());
            pstmt.setTimestamp(7, dto.getCreatedDateTime());
            pstmt.setTimestamp(8, dto.getModifiedDateTime());
            int i = pstmt.executeUpdate();
            log.info("New Course Added " + i);
            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception : Add rollback exception " + ex.getMessage());
            }
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception in add Course");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model add End");
        return pk;
    }

    @Override
    public void delete(CourseDTO dto) throws ApplicationException {
        delete(dto.getId());
    }

    public void delete(long id) throws ApplicationException {
        log.debug("Model delete Started");
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("delete from st_course where id = ?");
            pstmt.setLong(1, id);
            int i = pstmt.executeUpdate();
            log.info("Course deleted " + i);
            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
            }
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception in delete Course");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model delete End");
    }

    @Override
    public void update(CourseDTO dto) throws ApplicationException, DuplicateRecordException {
        log.debug("Model update Started");
        Connection conn = null;
        CourseDTO exist = findByName(dto.getCourseName());
        if (exist != null && exist.getId() != dto.getId()) {
            log.error("Course already exists");
            throw new DuplicateRecordException("Course already Exist");
        }
        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_course set name = ?, description = ?, duration = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");
            pstmt.setString(1, dto.getCourseName());
            pstmt.setString(2, dto.getDescription());
            pstmt.setString(3, dto.getDuration());
            pstmt.setString(4, dto.getCreatedBy());
            pstmt.setString(5, dto.getModifiedBy());
            pstmt.setTimestamp(6, dto.getCreatedDateTime());
            pstmt.setTimestamp(7, dto.getModifiedDateTime());
            pstmt.setLong(8, dto.getId());
            int i = pstmt.executeUpdate();
            log.info("Course updated " + i);
            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception : Update rollback exception " + ex.getMessage());
            }
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception in Update Course");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model update End");
    }

    @Override
    public List<CourseDTO> list() throws ApplicationException {
        log.debug("Model list Started");
        return search(null, 0, 0);
    }

    @Override
    public List<CourseDTO> list(int pageNo, int pageSize) throws ApplicationException {
        log.debug("Model list Started");
        return search(null, pageNo, pageSize);
    }

    @Override
    public List<CourseDTO> search(CourseDTO dto) throws ApplicationException {
        log.debug("Model search Started");
        return search(dto, 0, 0);
    }

    @Override
    public List<CourseDTO> search(CourseDTO dto, int pageNo, int pageSize) throws ApplicationException {
        log.debug("Model search Started");
        Connection conn = null;
        StringBuffer sql = new StringBuffer("select * from st_course where 1 = 1");
        List<CourseDTO> courseList = new ArrayList<CourseDTO>();

        if (dto != null) {
            if (dto.getId() > 0) {
                sql.append(" and id = " + dto.getId());
            }
            if (dto.getCourseName() != null && dto.getCourseName().length() > 0) {
                sql.append(" and name like '" + dto.getCourseName() + "%'");
            }
            if (dto.getDescription() != null && dto.getDescription().length() > 0) {
                sql.append(" and description like '" + dto.getDescription() + "%'");
            }
            if (dto.getDuration() != null && dto.getDuration().length() > 0) {
                sql.append(" and duration like '" + dto.getDuration() + "%'");
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + ", " + pageSize);
        }

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                dto = new CourseDTO();
                dto.setId(rs.getLong(1));
                dto.setCourseName(rs.getString(2));
                dto.setDescription(rs.getString(3));
                dto.setDuration(rs.getString(4));
                dto.setCreatedBy(rs.getString(5));
                dto.setModifiedBy(rs.getString(6));
                dto.setCreatedDateTime(rs.getTimestamp(7));
                dto.setModifiedDateTime(rs.getTimestamp(8));
                courseList.add(dto);
            }
            pstmt.close();
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception in search Course");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model search End");
        return courseList;
    }

    @Override
    public CourseDTO findByPK(long pk) throws ApplicationException {
        log.debug("Model findByPK Started");
        Connection conn = null;
        CourseDTO dto = null;
        StringBuffer sql = new StringBuffer("select * from st_course where id = ?");
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dto = new CourseDTO();
                dto.setId(rs.getLong(1));
                dto.setCourseName(rs.getString(2));
                dto.setDescription(rs.getString(3));
                dto.setDuration(rs.getString(4));
                dto.setCreatedBy(rs.getString(5));
                dto.setModifiedBy(rs.getString(6));
                dto.setCreatedDateTime(rs.getTimestamp(7));
                dto.setModifiedDateTime(rs.getTimestamp(8));
            }
            pstmt.close();
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception in FindByPk Course");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model findByPK End");
        return dto;
    }

    @Override
    public CourseDTO findByName(String name) throws ApplicationException {
        log.debug("Model findByName Started");
        Connection conn = null;
        CourseDTO dto = null;
        StringBuffer sql = new StringBuffer("select * from st_course where name = ?");
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dto = new CourseDTO();
                dto.setId(rs.getLong(1));
                dto.setCourseName(rs.getString(2));
                dto.setDescription(rs.getString(3));
                dto.setDuration(rs.getString(4));
                dto.setCreatedBy(rs.getString(5));
                dto.setModifiedBy(rs.getString(6));
                dto.setCreatedDateTime(rs.getTimestamp(7));
                dto.setModifiedDateTime(rs.getTimestamp(8));
            }
            pstmt.close();
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception in findByName Course");
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
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_course");
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
        log.debug("Model getNextPk End");
        return pk + 1L;
    }
}