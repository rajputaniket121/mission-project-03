package in.co.rays.proj3.model.jdbcImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj3.dto.CollegeDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.CollegeModelInt;
import in.co.rays.proj3.utill.JDBCDataSource;


public class CollegeModelJDBCImpl implements CollegeModelInt {

    @Override
    public long add(CollegeDTO dto) throws ApplicationException, DuplicateRecordException {
        Connection conn = null;
        long pk = 0L;
        CollegeDTO exist = fingByName(dto.getName());
        if (exist != null) {
            throw new DuplicateRecordException("College already Exist");
        }
        try {
            conn = JDBCDataSource.getConnection();
            pk = getNextPK();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("insert into st_college values(?,?,?,?,?,?,?,?,?,?)");
            pstmt.setLong(1, pk);
            pstmt.setString(2, dto.getName());
            pstmt.setString(3, dto.getAddress());
            pstmt.setString(4, dto.getState());
            pstmt.setString(5, dto.getCity());
            pstmt.setString(6, dto.getPhoneNo());
            pstmt.setString(7, dto.getCreatedBy());
            pstmt.setString(8, dto.getModifiedBy());
            pstmt.setTimestamp(9, dto.getCreatedDateTime());
            pstmt.setTimestamp(10, dto.getModifiedDateTime());
            int i = pstmt.executeUpdate();
            System.out.println("New College Added " + i);
            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception : Add rollback exception " + ex.getMessage());
            }
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception in add College");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return pk;
    }

    @Override
    public void delete(CollegeDTO dto) throws ApplicationException {
        delete(dto.getId());
    }

    public void delete(long id) throws ApplicationException {
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("delete from st_college where id = ?");
            pstmt.setLong(1, id);
            int i = pstmt.executeUpdate();
            System.out.println("College deleted " + i);
            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
            }
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception in delete College");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    @Override
    public void update(CollegeDTO dto) throws ApplicationException, DuplicateRecordException {
        Connection conn = null;
        CollegeDTO exist = fingByName(dto.getName());
        if (exist != null && exist.getId() != dto.getId()) {
            throw new DuplicateRecordException("College already Exist");
        }
        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_college set name = ?, address = ?,state = ?, city = ? , phone_no = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");
            pstmt.setString(1, dto.getName());
            pstmt.setString(2, dto.getAddress());
            pstmt.setString(3, dto.getState());
            pstmt.setString(4, dto.getCity());
            pstmt.setString(5, dto.getPhoneNo());
            pstmt.setString(6, dto.getCreatedBy());
            pstmt.setString(7, dto.getModifiedBy());
            pstmt.setTimestamp(8, dto.getCreatedDateTime());
            pstmt.setTimestamp(9, dto.getModifiedDateTime());
            pstmt.setLong(10, dto.getId());
            int i = pstmt.executeUpdate();
            System.out.println("College updated " + i);
            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception : Update rollback exception " + ex.getMessage());
            }
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception in Update College");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    @Override
    public List<CollegeDTO> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    @Override
    public List<CollegeDTO> list(int pageNo, int pageSize) throws ApplicationException {
        return search(null, pageNo, pageSize);
    }

    @Override
    public List<CollegeDTO> search(CollegeDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    @Override
    public List<CollegeDTO> search(CollegeDTO dto, int pageNo, int pageSize) throws ApplicationException {
        Connection conn = null;
        StringBuffer sql = new StringBuffer("select * from st_college where 1 = 1");
        List<CollegeDTO> collegeList = new ArrayList<CollegeDTO>();

        if (dto != null) {
            if (dto.getId() > 0) {
                sql.append(" and id = " + dto.getId());
            }
            if (dto.getName() != null && dto.getName().length() > 0) {
                sql.append(" and name like '" + dto.getName() + "%'");
            }
            if (dto.getAddress() != null && dto.getAddress().length() > 0) {
                sql.append(" and address like '" + dto.getAddress() + "%'");
            }
            if (dto.getState() != null && dto.getState().length() > 0) {
                sql.append(" and state like '" + dto.getState() + "%'");
            }
            if (dto.getCity() != null && dto.getCity().length() > 0) {
                sql.append(" and city like '" + dto.getCity() + "%'");
            }
            if (dto.getPhoneNo() != null && dto.getPhoneNo().length() > 0) {
                sql.append(" and phone_no like '" + dto.getPhoneNo() + "%'");
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
                dto = new CollegeDTO();
                dto.setId(rs.getLong(1));
                dto.setName(rs.getString(2));
                dto.setAddress(rs.getString(3));
                dto.setState(rs.getString(4));
                dto.setCity(rs.getString(5));
                dto.setPhoneNo(rs.getString(6));
                dto.setCreatedBy(rs.getString(7));
                dto.setModifiedBy(rs.getString(8));
                dto.setCreatedDateTime(rs.getTimestamp(9));
                dto.setModifiedDateTime(rs.getTimestamp(10));
                collegeList.add(dto);
            }
            pstmt.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception in search College");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return collegeList;
    }

    @Override
    public CollegeDTO findByPK(long pk) throws ApplicationException {
        Connection conn = null;
        CollegeDTO dto = null;
        StringBuffer sql = new StringBuffer("select * from st_college where id = ?");
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dto = new CollegeDTO();
                dto.setId(rs.getLong(1));
                dto.setName(rs.getString(2));
                dto.setAddress(rs.getString(3));
                dto.setState(rs.getString(4));
                dto.setCity(rs.getString(5));
                dto.setPhoneNo(rs.getString(6));
                dto.setCreatedBy(rs.getString(7));
                dto.setModifiedBy(rs.getString(8));
                dto.setCreatedDateTime(rs.getTimestamp(9));
                dto.setModifiedDateTime(rs.getTimestamp(10));
            }
            pstmt.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception in FindByPk College");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return dto;
    }

    @Override
    public CollegeDTO fingByName(String name) throws ApplicationException {
        Connection conn = null;
        CollegeDTO dto = null;
        StringBuffer sql = new StringBuffer("select * from st_college where name = ?");
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dto = new CollegeDTO();
                dto.setId(rs.getLong(1));
                dto.setName(rs.getString(2));
                dto.setAddress(rs.getString(3));
                dto.setState(rs.getString(4));
                dto.setCity(rs.getString(5));
                dto.setPhoneNo(rs.getString(6));
                dto.setCreatedBy(rs.getString(7));
                dto.setModifiedBy(rs.getString(8));
                dto.setCreatedDateTime(rs.getTimestamp(9));
                dto.setModifiedDateTime(rs.getTimestamp(10));
            }
            pstmt.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception in FindByName College");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return dto;
    }

    private long getNextPK() throws ApplicationException {
        Connection conn = null;
        long pk = 0L;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_college");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                pk = rs.getLong(1);
            }
            pstmt.close();
            rs.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception : Exception In Getting pk");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
		return pk;
    }
}