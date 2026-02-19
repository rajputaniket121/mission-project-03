package in.co.rays.proj3.model;

import java.util.List;
import in.co.rays.proj3.dto.AttendanceDTO;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;

public interface AttendanceModelInt {
    
    public long add(AttendanceDTO dto) throws DatabaseException, DuplicateRecordException;
    public void delete(AttendanceDTO dto) throws DatabaseException;
    public void update(AttendanceDTO dto) throws DatabaseException, DuplicateRecordException;
    public List list() throws DatabaseException;
    public List search(AttendanceDTO dto, int pageNo, int pageSize) throws DatabaseException;
    public AttendanceDTO findByPK(long pk) throws DatabaseException;
    public AttendanceDTO findByPersonName(String personName) throws DatabaseException;
}