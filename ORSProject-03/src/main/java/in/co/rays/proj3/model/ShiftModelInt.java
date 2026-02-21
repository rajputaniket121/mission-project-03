package in.co.rays.proj3.model;

import java.util.List;
import in.co.rays.proj3.dto.ShiftDTO;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;

public interface ShiftModelInt {
    
    public long add(ShiftDTO dto) throws DatabaseException, DuplicateRecordException;
    public void delete(ShiftDTO dto) throws DatabaseException;
    public void update(ShiftDTO dto) throws DatabaseException, DuplicateRecordException;
    public List list() throws DatabaseException;
    public List search(ShiftDTO dto, int pageNo, int pageSize) throws DatabaseException;
    public ShiftDTO findByPK(long pk) throws DatabaseException;
    public ShiftDTO findByShiftCode(String shiftCode) throws DatabaseException;
}