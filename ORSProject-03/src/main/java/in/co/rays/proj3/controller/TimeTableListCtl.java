package in.co.rays.proj3.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.CourseDTO;
import in.co.rays.proj3.dto.SubjectDTO;
import in.co.rays.proj3.dto.TimetableDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.model.CourseModelInt;
import in.co.rays.proj3.model.SubjectModelInt;
import in.co.rays.proj3.model.TimetableModelInt;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.utill.DataUtility;
import in.co.rays.proj3.utill.PropertyReader;
import in.co.rays.proj3.utill.ServletUtility;

@WebServlet(name = "TimeTableListCtl",urlPatterns = {"/ctl/TimeTableListCtl"})
public class TimeTableListCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request) {
		CourseModelInt model = ModelFactory.getInstance().getCourseModel();
		SubjectModelInt subModel = ModelFactory.getInstance().getSubjectModel();
		try {
			List<CourseDTO> courseList = model.list();
			request.setAttribute("courseList", courseList);
			List<SubjectDTO> subjectList = subModel.list();
			request.setAttribute("subjectList", subjectList);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		TimetableDTO dto = new TimetableDTO();
		dto.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));
		dto.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		dto.setExamDate(DataUtility.getDate(request.getParameter("examDate")));
		return dto;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		TimetableDTO dto = (TimetableDTO) populateDTO(req);
		TimetableModelInt model = ModelFactory.getInstance().getTimetableModel();
		try {
			List<TimetableDTO> list= model.search(dto, pageNo, pageSize);
			List<TimetableDTO> next= model.search(dto, pageNo+1, pageSize);
			if(list.isEmpty() || list==null) {
				ServletUtility.setErrorMessage("No Records found", req);
			}
			ServletUtility.setList(list, req);
			ServletUtility.setDto(dto, req);
			ServletUtility.setPageNo(pageNo, req);
			ServletUtility.setPageSize(pageSize, req);
			req.setAttribute("nextListSize", next.size());
		} catch (ApplicationException e) {
            e.printStackTrace();
            if(e.getClass().toString().equals(e.toString())) {
            	 ServletUtility.handleExceptionDBDown(e, req, resp,getView());
            }else {
            	ServletUtility.handleException(e, req, resp);
            }
            return;
        }
		ServletUtility.forward(getView(), req, resp);
	}
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<TimetableDTO> list = null;
		List<TimetableDTO> next = null;
		
		int pageNo = DataUtility.getInt(req.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(req.getParameter("pageSize"));
		
		pageNo = (pageNo==0)? 1 : pageNo;
		pageSize = (pageSize==0)? (DataUtility.getInt(PropertyReader.getValue("page.size")))  : pageSize;
		TimetableDTO dto = (TimetableDTO) populateDTO(req);
		TimetableModelInt model = ModelFactory.getInstance().getTimetableModel();
		
		String op = DataUtility.getString(req.getParameter("operation"));
		
		try {
			
		if(OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op)) {
			
			if(OP_SEARCH.equalsIgnoreCase(op)) {
				pageNo=1;
				dto = (TimetableDTO) populateDTO(req);
				
			}else if(OP_NEXT.equalsIgnoreCase(op)) {
				pageNo++;
				
			}else if(OP_PREVIOUS.equalsIgnoreCase(op)) {
				pageNo--;
			}
			
			
		} else if(OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, req, resp);
			return;
		}else if(OP_NEW.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.TIMETABLE_CTL, req, resp);
			return;
			
		}else if(OP_DELETE.equalsIgnoreCase(op)) {
			pageNo=1;
			String[] ids = req.getParameterValues("ids");	
			if(ids!=null && ids.length>0) {
				TimetableDTO delete = new TimetableDTO();
				for(String id  : ids) {
					delete.setId(DataUtility.getLong(id));
					model.delete(delete);
					ServletUtility.setSuccessMessage("Deleted Successfully !!!", req);
				}
			}else {
				ServletUtility.setErrorMessage("Select Atleast one Checkbox", req);
			}
			
		}else if(OP_BACK.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, req, resp);
			return;
		}
		
		list= model.search(dto, pageNo, pageSize);
		next = model.search(dto, pageNo+1, pageSize);
			if(list.isEmpty() || list==null) {
				ServletUtility.setErrorMessage("No Records found", req);
			}
			ServletUtility.setList(list, req);
			ServletUtility.setDto(dto, req);
			ServletUtility.setPageNo(pageNo, req);
			ServletUtility.setPageSize(pageSize, req);
			req.setAttribute("nextListSize", next.size());
		} catch (ApplicationException e) {
            e.printStackTrace();
            if(e.getClass().toString().equals(e.toString())) {
            	 ServletUtility.handleExceptionDBDown(e, req, resp,getView());
            }else {
            	ServletUtility.handleException(e, req, resp);
            }
            return;
        }
		ServletUtility.forward(getView(), req, resp);
	}
	
	
	@Override
	protected String getView() {
		return ORSView.TIMETABLE_LIST_VIEW;
	}

	
}