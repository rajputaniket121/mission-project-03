package in.co.rays.proj3.model;

import java.util.List;
import in.co.rays.proj3.dto.AnnouncementDTO;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;

public interface AnnouncementModelInt {
    
    public long add(AnnouncementDTO dto) throws DatabaseException, DuplicateRecordException;
    public void delete(AnnouncementDTO dto) throws DatabaseException;
    public void update(AnnouncementDTO dto) throws DatabaseException, DuplicateRecordException;
    public List list() throws DatabaseException;
    public List search(AnnouncementDTO dto, int pageNo, int pageSize) throws DatabaseException;
    public AnnouncementDTO findByPK(long pk) throws DatabaseException;
    public AnnouncementDTO findByAnnouncementCode(String announcementCode) throws DatabaseException;
}