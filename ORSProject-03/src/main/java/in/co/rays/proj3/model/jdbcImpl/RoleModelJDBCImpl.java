package in.co.rays.proj3.model.jdbcImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj3.dto.RoleDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.RoleModelInt;
import in.co.rays.proj3.utill.JDBCDataSource;

public class RoleModelJDBCImpl implements RoleModelInt{
	
	@Override
	public long add(RoleDTO dto) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		Long pk = 0l;
		RoleDTO exist = findByName(dto.getName());
		if (exist != null) {
			throw new DuplicateRecordException("Role already Exists");
		}
		try {
			conn = JDBCDataSource.getConnection();
			pk = getNextPk();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("insert into st_role values(?,?,?,?,?,?,?)");
			pstmt.setLong(1, pk);
			pstmt.setString(2, dto.getName());
			pstmt.setString(3, dto.getDescription());
			pstmt.setString(4, dto.getCreatedBy());
			pstmt.setString(5, dto.getModifiedBy());
			pstmt.setTimestamp(6, dto.getCreatedDateTime());
			pstmt.setTimestamp(7, dto.getModifiedDateTime());
			int i = pstmt.executeUpdate();
			System.out.println("New Role Added " + i);
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Add rollback exception " + ex.getMessage());
			}
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in add Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
	}
	
	@Override
	public void update(RoleDTO dto) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		RoleDTO exist = findByName(dto.getName());
		if (exist != null && exist.getId() != dto.getId()) {
			throw new DuplicateRecordException("Role already Exist");
		}
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"update st_role set name = ?, description = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getDescription());
			pstmt.setString(3, dto.getCreatedBy());
			pstmt.setString(4, dto.getModifiedBy());
			pstmt.setTimestamp(5, dto.getCreatedDateTime());
			pstmt.setTimestamp(6, dto.getModifiedDateTime());
			pstmt.setLong(7, dto.getId());
			int i = pstmt.executeUpdate();
			System.out.println("Role updated " + i);
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Update rollback exception " + ex.getMessage());
			}
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in Update Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}
	
	@Override
	public void delete(RoleDTO dto) throws ApplicationException {
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from st_role where id = ?");
			pstmt.setLong(1, dto.getId());
			int i = pstmt.executeUpdate();
			System.out.println("Role deleted " + i);
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in delete Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}
	
	@Override
	public RoleDTO findByPK(long pk) throws ApplicationException {
		Connection conn = null;
		RoleDTO dto = null;
		StringBuffer sql = new StringBuffer("select * from st_role where id = ?");
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new RoleDTO();
				dto.setId(rs.getLong(1));
				dto.setName(rs.getString(2));
				dto.setDescription(rs.getString(3));
				dto.setCreatedBy(rs.getString(4));
				dto.setModifiedBy(rs.getString(5));
				dto.setCreatedDateTime(rs.getTimestamp(6));
				dto.setModifiedDateTime(rs.getTimestamp(7));
			}
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in FindByPk Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return dto;
	}
	
	@Override
	public RoleDTO findByName(String name) throws ApplicationException {
		Connection conn = null;
		RoleDTO dto = null;
		StringBuffer sql = new StringBuffer("select * from st_role where name = ?");
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new RoleDTO();
				dto.setId(rs.getLong(1));
				dto.setName(rs.getString(2));
				dto.setDescription(rs.getString(3));
				dto.setCreatedBy(rs.getString(4));
				dto.setModifiedBy(rs.getString(5));
				dto.setCreatedDateTime(rs.getTimestamp(6));
				dto.setModifiedDateTime(rs.getTimestamp(7));
			}
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in findByName Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return dto;
	}
	
	@Override
	public List<RoleDTO> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	@Override
	public List<RoleDTO> search(RoleDTO dto, int pageNo, int pageSize) throws ApplicationException {
		Connection conn = null;
		StringBuffer sql = new StringBuffer("select * from st_role where 1 = 1");
		List<RoleDTO> roleList = new ArrayList<RoleDTO>();

		if (dto != null) {
			if (dto.getId() > 0) {
				sql.append(" and id = " + dto.getId());
			}
			if (dto.getName() != null && dto.getName().length() > 0) {
				sql.append(" and name like '" + dto.getName() + "%'");
			}
			if (dto.getDescription() != null && dto.getDescription().length() > 0) {
				sql.append(" and description like '" + dto.getDescription() + "%'");
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
				dto = new RoleDTO();
				dto.setId(rs.getLong(1));
				dto.setName(rs.getString(2));
				dto.setDescription(rs.getString(3));
				dto.setCreatedBy(rs.getString(4));
				dto.setModifiedBy(rs.getString(5));
				dto.setCreatedDateTime(rs.getTimestamp(6));
				dto.setModifiedDateTime(rs.getTimestamp(7));
				roleList.add(dto);
			}
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in search Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return roleList;
	}

	public Long getNextPk() throws DatabaseException {
		Connection conn = null;
		Long pk = 0l;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_role");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getLong(1);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new DatabaseException("Exception : Exception In Getting pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk + 1l;
	}

}
