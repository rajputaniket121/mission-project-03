package in.co.rays.proj3.model;

import java.util.List;
import in.co.rays.proj3.dto.SupportDTO;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;

public interface SupportModelInt {
    
    public long add(SupportDTO dto) throws DatabaseException, DuplicateRecordException;
    public void delete(SupportDTO dto) throws DatabaseException;
    public void update(SupportDTO dto) throws DatabaseException, DuplicateRecordException;
    public List list() throws DatabaseException;
    public List search(SupportDTO dto, int pageNo, int pageSize) throws DatabaseException;
    public SupportDTO findByPK(long pk) throws DatabaseException;
    public SupportDTO findByUserName(String userName) throws DatabaseException;
}