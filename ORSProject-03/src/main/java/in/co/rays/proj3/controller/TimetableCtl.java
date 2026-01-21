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
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.CourseModelInt;
import in.co.rays.proj3.model.SubjectModelInt;
import in.co.rays.proj3.model.TimetableModelInt;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.utill.DataUtility;
import in.co.rays.proj3.utill.DataValidator;
import in.co.rays.proj3.utill.PropertyReader;
import in.co.rays.proj3.utill.ServletUtility;

@WebServlet(name = "TimeTableCtl",urlPatterns = {"/ctl/TimeTableCtl"})
public class TimetableCtl extends BaseCtl {
	
	 @Override
	    protected void preload(HttpServletRequest request) {
	        CourseModelInt courseModel = ModelFactory.getInstance().getCourseModel();
	        SubjectModelInt subjectModel = ModelFactory.getInstance().getSubjectModel();
	        try {
	            List<CourseDTO> courseList =  courseModel.list();
	            List<SubjectDTO> subjectList = subjectModel.list();
	            request.setAttribute("courseList", courseList);
	            request.setAttribute("subjectList", subjectList);
	        } catch (ApplicationException e) {
	            throw new RuntimeException(e);
	        }
	    }

	    @Override
	    protected boolean validate(HttpServletRequest request) {
	        boolean pass = true;
	        String op = request.getParameter("operation");

	        if(OP_LOG_OUT.equalsIgnoreCase(op) || OP_RESET.equalsIgnoreCase(op)) {
	            return pass;
	        }

	        if(DataValidator.isNull(request.getParameter("courseId"))) {
	            request.setAttribute("courseId", PropertyReader.getValue("error.require","Course Name"));
	            pass = false;
	        }
	        else if(!DataValidator.isLong(request.getParameter("courseId"))) {
	            request.setAttribute("courseId", "Invalid Course Name");
	            pass = false;
	        }

	        if(DataValidator.isNull(request.getParameter("subjectId"))) {
	            request.setAttribute("subjectId", PropertyReader.getValue("error.require","Subject Name"));
	            pass = false;
	        } else if(!DataValidator.isLong(request.getParameter("subjectId"))) {
	            request.setAttribute("subjectId","Invalid Subject Name");
	            pass = false;
	        }

	        if (DataValidator.isNull(request.getParameter("semester"))) {
	            request.setAttribute("semester", PropertyReader.getValue("error.require", "Semester"));
	            pass = false;
	        }

	        if (DataValidator.isNull(request.getParameter("examDate"))) {
	            request.setAttribute("examDate", PropertyReader.getValue("error.require", "ExamDate"));
	            pass = false;
	        } else if (!DataValidator.isDate(request.getParameter("examDate"))) {
	            request.setAttribute("examDate", PropertyReader.getValue("error.date", "ExamDate"));
	            pass = false;
	        }
//	        else if (!DataValidator.isSunday(request.getParameter("examDate"))) {
//	            request.setAttribute("examDate", PropertyReader.getValue("error.date", "Exam Should not be on Sunday "));
//	            pass = false;
//	        }
	        
	        
	        if(DataValidator.isNull(request.getParameter("examTime"))){
	            request.setAttribute("examTime", PropertyReader.getValue("error.require", "ExamTime "));
	            pass = false;
	        }

	        if(DataValidator.isNull(request.getParameter("description"))){
	            request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
	            pass = false;
	        }

	        return pass;
	    }

	    @Override
	    protected BaseDTO populateDTO(HttpServletRequest request) {
	        TimetableDTO dto = new TimetableDTO();
	        dto.setId(DataUtility.getLong(request.getParameter("id")));
	        dto.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
	        dto.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));
	        dto.setSemester(DataUtility.getString(request.getParameter("semester")));
	        dto.setExamDate(DataUtility.getDate(request.getParameter("examDate")));
	        dto.setExamTime(DataUtility.getString(request.getParameter("examTime")));
	        dto.setDescription(DataUtility.getString(request.getParameter("description")));
	        return dto;
	    }

	    @Override
	    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    	Long id = DataUtility.getLong(req.getParameter("id"));
	    	TimetableModelInt model = ModelFactory.getInstance().getTimetableModel();
	    	if(id>0) {
	    		try {
	    			TimetableDTO dto = model.findByPK(id);
					ServletUtility.setDto(dto, req);
				} catch (ApplicationException e) {
	                e.printStackTrace();
	                if(e.getClass().toString().equals(e.toString())) {
	                	 ServletUtility.handleExceptionDBDown(e, req, resp,getView());
	                }else {
	                	ServletUtility.handleException(e, req, resp);
	                }
	                return;
	            }
	    	}
	        ServletUtility.forward(getView(), req, resp);
	    }

	    @Override
	    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        String op = DataUtility.getString(req.getParameter("operation"));
	        TimetableModelInt model = ModelFactory.getInstance().getTimetableModel();
	        if(TimetableCtl.OP_SAVE.equalsIgnoreCase(op)) {
	            TimetableDTO dto = (TimetableDTO) populateDTO(req);
	            TimetableDTO dto1;
	            TimetableDTO dto2;
	            TimetableDTO dto3;
	            
	            
	            try {
	            	dto1 = model.checkByCourseName(dto.getCourseId(), dto.getExamDate());

					dto2 = model.checkBySubjectName(dto.getCourseId(), dto.getSubjectId(), dto.getExamDate());

					dto3 = model.checkBySemester(dto.getCourseId(), dto.getSubjectId(), dto.getSemester(),
							dto.getExamDate());

					if (dto1 == null && dto2 == null && dto3 == null) {
						long pk = model.add(dto);
						ServletUtility.setDto(dto, req);
						ServletUtility.setSuccessMessage("Timetable added successfully", req);
					} else {
						dto = (TimetableDTO) populateDTO(req);
						ServletUtility.setDto(dto, req);
						ServletUtility.setErrorMessage("Timetable already exist!", req);
					}
	            }catch(DuplicateRecordException dre) {
	                ServletUtility.setDto(dto, req);
	                ServletUtility.setErrorMessage(" Already Exist !!!", req);
	            }catch (ApplicationException e) {
	                e.printStackTrace();
	                if(e.getClass().toString().equals(e.toString())) {
	                	 ServletUtility.handleExceptionDBDown(e, req, resp,getView());
	                }else {
	                	ServletUtility.handleException(e, req, resp);
	                }
	                return;
	            }
	            ServletUtility.forward(getView(), req, resp);
	        }else if(OP_RESET.equalsIgnoreCase(op)) {
	            ServletUtility.redirect(ORSView.TIMETABLE_CTL, req, resp);
	            return;
	        }else if(OP_UPDATE.equalsIgnoreCase(op)) {
	        	TimetableDTO dto = (TimetableDTO) populateDTO(req);
		       	try {
		               model.update(dto);
		               ServletUtility.setDto(dto, req);
		               ServletUtility.setSuccessMessage("Timetable Updated SuccessFully !!!", req);
		           }catch(DuplicateRecordException dre) {
		               ServletUtility.setDto(dto, req);
		               ServletUtility.setErrorMessage("Already Exist !!!", req);
		           }catch (ApplicationException e) {
		                e.printStackTrace();
		                if(e.getClass().toString().equals(e.toString())) {
		                	 ServletUtility.handleExceptionDBDown(e, req, resp,getView());
		                }else {
		                	ServletUtility.handleException(e, req, resp);
		                }
		                return;
		            }
		       }
		       else if(OP_CANCEL.equalsIgnoreCase(op)) {
		       	 ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, req, resp);
		       	 return;
		       }
		       ServletUtility.forward(getView(), req, resp);
	    }


	@Override
	protected String getView() {
		return ORSView.TIMETABLE_VIEW;
	}

}