package in.co.rays.proj3.model;

import java.util.List;

import in.co.rays.proj3.dto.RoleDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;

/**
 * Interface of Role model
 * @author Aniket Rajput
 *
 */
public interface RoleModelInt {
public long add(RoleDTO dto) throws ApplicationException,DuplicateRecordException;
public void delete(RoleDTO dto) throws ApplicationException;
public void update(RoleDTO dto) throws ApplicationException,DuplicateRecordException;
public List list()throws ApplicationException;
public List search(RoleDTO dto,int pageNo,int pageSize) throws ApplicationException;
public RoleDTO findByPK(long pk) throws ApplicationException;
public RoleDTO findByName(String name) throws ApplicationException;
}
