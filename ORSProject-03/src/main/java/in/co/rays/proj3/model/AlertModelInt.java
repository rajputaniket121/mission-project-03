package in.co.rays.proj3.model;

import java.util.List;
import in.co.rays.proj3.dto.AlertDTO;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;

public interface AlertModelInt {
    
    public long add(AlertDTO dto) throws DatabaseException, DuplicateRecordException;
    public void delete(AlertDTO dto) throws DatabaseException;
    public void update(AlertDTO dto) throws DatabaseException, DuplicateRecordException;
    public List list() throws DatabaseException;
    public List search(AlertDTO dto, int pageNo, int pageSize) throws DatabaseException;
    public AlertDTO findByPK(long pk) throws DatabaseException;
    public AlertDTO findByAlertCode(String alertCode) throws DatabaseException;
}