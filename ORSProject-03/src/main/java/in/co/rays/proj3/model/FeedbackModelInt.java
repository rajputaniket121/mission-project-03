package in.co.rays.proj3.model;

import java.util.List;
import in.co.rays.proj3.dto.FeedbackDTO;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;

public interface FeedbackModelInt {
    
    public long add(FeedbackDTO dto) throws DatabaseException, DuplicateRecordException;
    public void delete(FeedbackDTO dto) throws DatabaseException;
    public void update(FeedbackDTO dto) throws DatabaseException, DuplicateRecordException;
    public List list() throws DatabaseException;
    public List search(FeedbackDTO dto, int pageNo, int pageSize) throws DatabaseException;
    public FeedbackDTO findByPK(long pk) throws DatabaseException;
    public FeedbackDTO findByFeedbackCode(String feedbackCode) throws DatabaseException;
}