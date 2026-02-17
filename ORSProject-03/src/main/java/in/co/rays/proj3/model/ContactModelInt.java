package in.co.rays.proj3.model;

import java.util.List;
import in.co.rays.proj3.dto.ContactDTO;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;

public interface ContactModelInt {
    
    public long add(ContactDTO dto) throws DatabaseException, DuplicateRecordException;
    public void delete(ContactDTO dto) throws DatabaseException;
    public void update(ContactDTO dto) throws DatabaseException, DuplicateRecordException;
    public List list() throws DatabaseException;
    public List search(ContactDTO dto, int pageNo, int pageSize) throws DatabaseException;
    public ContactDTO findByPK(long pk) throws DatabaseException;
    public ContactDTO findByEmail(String email) throws DatabaseException;
    public ContactDTO findByMobileNo(String mobileNo) throws DatabaseException;
}