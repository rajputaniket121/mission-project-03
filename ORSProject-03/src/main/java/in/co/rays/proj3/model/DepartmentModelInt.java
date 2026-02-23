package in.co.rays.proj3.model;

import java.util.List;
import in.co.rays.proj3.dto.DepartmentDTO;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;

public interface DepartmentModelInt {
    
    public long add(DepartmentDTO dto) throws DatabaseException, DuplicateRecordException;
    public void delete(DepartmentDTO dto) throws DatabaseException;
    public void update(DepartmentDTO dto) throws DatabaseException, DuplicateRecordException;
    public List list() throws DatabaseException;
    public List search(DepartmentDTO dto, int pageNo, int pageSize) throws DatabaseException;
    public DepartmentDTO findByPK(long pk) throws DatabaseException;
    public DepartmentDTO findByDepartmentCode(String departmentCode) throws DatabaseException;
}