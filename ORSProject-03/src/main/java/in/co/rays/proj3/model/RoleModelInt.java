package in.co.rays.proj3.model;

import java.util.List;

import in.co.rays.proj3.dto.RoleDTO;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;

/**
 * Interface of Role model
 * @author Aniket Rajput
 *
 */
public interface RoleModelInt {
public long add(RoleDTO dto) throws DatabaseException,DuplicateRecordException;
public void delete(RoleDTO dto) throws DatabaseException;
public void update(RoleDTO dto) throws DatabaseException,DuplicateRecordException;
public List list()throws DatabaseException;
public List search(RoleDTO dto,int pageNo,int pageSize) throws DatabaseException;
public RoleDTO findByPK(long pk) throws DatabaseException;
public RoleDTO findByName(String name) throws DatabaseException;
}
