package in.co.rays.proj3.model.jdbcImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.proj3.dto.StudentDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.StudentModelInt;
import in.co.rays.proj3.utill.JDBCDataSource;

/**
 * JDBC implements of Student model
 * @author Aniket Rajput
 *
 */
public class StudentModelJDBCImpl implements StudentModelInt {

    private static Logger log = Logger.getLogger(StudentModelJDBCImpl.class);

    @Override
    public long add(StudentDTO dto) throws ApplicationException, DuplicateRecordException {
        log.debug("Model add Started");
        Connection conn = null;
        long pk = 0L;
        
        StudentDTO exist = findByEmailId(dto.getEmail());
        if(exist != null) {
            log.error("Email Id already exists");
            throw new DuplicateRecordException("Email Id already Exist");
        }
        try {
            conn = JDBCDataSource.getConnection();
            pk = getNextPK();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("insert into st_student values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pstmt.setLong(1, pk);
            pstmt.setString(2, dto.getFirstName());
            pstmt.setString(3, dto.getLastName());
            pstmt.setDate(4, new java.sql.Date(dto.getDob().getTime()));
            pstmt.setString(5, dto.getGender());
            pstmt.setString(6, dto.getMobileNo());
            pstmt.setString(7, dto.getEmail());
            pstmt.setLong(8, dto.getCollegeId());
            pstmt.setString(9, dto.getCollegeName());
            pstmt.setString(10, dto.getCreatedBy());
            pstmt.setString(11, dto.getModifiedBy());
            pstmt.setTimestamp(12, dto.getCreatedDateTime());
            pstmt.setTimestamp(13, dto.getModifiedDateTime());
            int i = pstmt.executeUpdate();
            log.info("New Student Added " + i);
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
            throw new ApplicationException("Exception : Exception in add Student");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model add End");
        return pk;
    }

    @Override
    public void delete(StudentDTO dto) throws ApplicationException {
        delete(dto.getId());
    }

    public void delete(long id) throws ApplicationException {
        log.debug("Model delete Started");
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("delete from st_student where id = ?");
            pstmt.setLong(1, id);
            int i = pstmt.executeUpdate();
            log.info("Student deleted " + i);
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
            throw new ApplicationException("Exception : Exception in delete Student");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model delete End");
    }

    @Override
    public void update(StudentDTO dto) throws ApplicationException, DuplicateRecordException {
        log.debug("Model update Started");
        Connection conn = null;
        StudentDTO exist = findByEmailId(dto.getEmail());
        if(exist != null && exist.getId() != dto.getId()) {
            log.error("Email Id already exists");
            throw new DuplicateRecordException("Email Id already Exist");
        }
        
        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_student set first_name = ?, last_name = ? , dob = ? , gender = ? , mobile_no = ? , email = ? , college_id = ?, college_name = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");
            pstmt.setString(1, dto.getFirstName());
            pstmt.setString(2, dto.getLastName());
            pstmt.setDate(3, new java.sql.Date(dto.getDob().getTime()));
            pstmt.setString(4, dto.getGender());
            pstmt.setString(5, dto.getMobileNo());
            pstmt.setString(6, dto.getEmail());
            pstmt.setLong(7, dto.getCollegeId());
            pstmt.setString(8, dto.getCollegeName());
            pstmt.setString(9, dto.getCreatedBy());
            pstmt.setString(10, dto.getModifiedBy());
            pstmt.setTimestamp(11, dto.getCreatedDateTime());
            pstmt.setTimestamp(12, dto.getModifiedDateTime());
            pstmt.setLong(13, dto.getId());
            int i = pstmt.executeUpdate();
            log.info("Student updated " + i);
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
            throw new ApplicationException("Exception : Exception in Update Student");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model update End");
    }

    @Override
    public List<StudentDTO> list() throws ApplicationException {
        log.debug("Model list Started");
        return search(null, 0, 0);
    }

    @Override
    public List<StudentDTO> list(int pageNo, int pageSize) throws ApplicationException {
        log.debug("Model list Started");
        return search(null, pageNo, pageSize);
    }

    @Override
    public List<StudentDTO> search(StudentDTO dto) throws ApplicationException {
        log.debug("Model search Started");
        return search(dto, 0, 0);
    }

    @Override
    public List<StudentDTO> search(StudentDTO dto, int pageNo, int pageSize) throws ApplicationException {
        log.debug("Model search Started");
        Connection conn = null;
        StringBuffer sql = new StringBuffer("select * from st_student where 1 = 1");
        List<StudentDTO> studentList = new ArrayList<StudentDTO>();

        if (dto != null) {
            if (dto.getId()!=null && dto.getId() > 0) {
                sql.append(" and id = " + dto.getId());
            }
            if (dto.getFirstName() != null && dto.getFirstName().length() > 0) {
                sql.append(" and first_name like '" + dto.getFirstName() + "%'");
            }
            if (dto.getLastName() != null && dto.getLastName().length() > 0) {
                sql.append(" and last_name like '" + dto.getLastName() + "%'");
            }
            if (dto.getDob() != null) {
                sql.append(" and dob like '" + new java.sql.Date(dto.getDob().getTime()) + "%'");
            }
            if (dto.getGender() != null && dto.getGender().length() > 0) {
                sql.append(" and gender like '" + dto.getGender() + "%'");
            }
            if (dto.getMobileNo() != null && dto.getMobileNo().length() > 0) {
                sql.append(" and mobile_no like '" + dto.getMobileNo() + "%'");
            }
            if (dto.getEmail() != null && dto.getEmail().length() > 0) {
                sql.append(" and email like '" + dto.getEmail() + "%'");
            }
            if (dto.getCollegeId() != null && dto.getCollegeId() > 0) {
                sql.append(" and college_id like '" + dto.getCollegeId() + "%'");
            }
            if (dto.getCollegeName() != null && dto.getCollegeName().length() > 0) {
                sql.append(" and college_name like '" + dto.getCollegeName() + "%'");
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
                dto = new StudentDTO();
                dto.setId(rs.getLong(1));
                dto.setFirstName(rs.getString(2));
                dto.setLastName(rs.getString(3));
                dto.setDob(rs.getDate(4));
                dto.setGender(rs.getString(5));
                dto.setMobileNo(rs.getString(6));
                dto.setEmail(rs.getString(7));
                dto.setCollegeId(rs.getLong(8));
                dto.setCollegeName(rs.getString(9));
                dto.setCreatedBy(rs.getString(10));
                dto.setModifiedBy(rs.getString(11));
                dto.setCreatedDateTime(rs.getTimestamp(12));
                dto.setModifiedDateTime(rs.getTimestamp(13));
                studentList.add(dto);
            }
            pstmt.close();
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception in search Student");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model search End");
        return studentList;
    }

    @Override
    public StudentDTO findByPK(long pk) throws ApplicationException {
        log.debug("Model findByPK Started");
        Connection conn = null;
        StudentDTO dto = null;
        StringBuffer sql = new StringBuffer("select * from st_student where id = ?");
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dto = new StudentDTO();
                dto.setId(rs.getLong(1));
                dto.setFirstName(rs.getString(2));
                dto.setLastName(rs.getString(3));
                dto.setDob(rs.getDate(4));
                dto.setGender(rs.getString(5));
                dto.setMobileNo(rs.getString(6));
                dto.setEmail(rs.getString(7));
                dto.setCollegeId(rs.getLong(8));
                dto.setCollegeName(rs.getString(9));
                dto.setCreatedBy(rs.getString(10));
                dto.setModifiedBy(rs.getString(11));
                dto.setCreatedDateTime(rs.getTimestamp(12));
                dto.setModifiedDateTime(rs.getTimestamp(13));
            }
            pstmt.close();
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception in FindByPK Student");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model findByPK End");
        return dto;
    }

	@Override
	public StudentDTO findByEmailId(String emailId) throws ApplicationException {
		Connection conn = null;
		StudentDTO bean = null;
		StringBuffer sql = new StringBuffer("select * from st_student where email = ?");
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, emailId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new StudentDTO();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setDob(rs.getDate(4));
				bean.setGender(rs.getString(5));;
				bean.setMobileNo(rs.getString(6));
				bean.setEmail(rs.getString(7));;
				bean.setCollegeId(rs.getLong(8));
				bean.setCollegeName(rs.getString(9));;
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreatedDateTime(rs.getTimestamp(12));
				bean.setModifiedDateTime(rs.getTimestamp(13));
			}
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in FindByEmail Student");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}
	
	public Long getNextPK() throws DatabaseException {
		Connection conn = null;
		Long pk = 0l;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt=conn.prepareStatement("select max(id) from st_student");
			ResultSet rs =  pstmt.executeQuery();
			while(rs.next()) {
				pk = rs.getLong(1);
			}
			rs.close();
			pstmt.close();
			
		}catch (Exception e) {
			throw new DatabaseException("Exception : Exception In Getting pk");
		}
		finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk+1;
	}
    

}