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
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.CourseModelInt;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.model.SubjectModelInt;
import in.co.rays.proj3.utill.DataUtility;
import in.co.rays.proj3.utill.DataValidator;
import in.co.rays.proj3.utill.PropertyReader;
import in.co.rays.proj3.utill.ServletUtility;

@WebServlet(name = "SubjectCtl",urlPatterns = {"/ctl/SubjectCtl"})
public class SubjectCtl extends BaseCtl{
	
	@Override
	protected void preload(HttpServletRequest request) {
		CourseModelInt model = ModelFactory.getInstance().getCourseModel();
		try {
			List<CourseDTO> courseList = model.list();
			request.setAttribute("courseList", courseList);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	@Override
    protected boolean validate(HttpServletRequest request) {
        boolean pass = true;
        String op = request.getParameter("operation");

        if(OP_LOG_OUT.equalsIgnoreCase(op) || OP_RESET.equalsIgnoreCase(op)) {
            return pass;
        }

        if(DataValidator.isNull(request.getParameter("name"))) {
            request.setAttribute("name", PropertyReader.getValue("error.require","Name"));
            pass = false;
        } else if(!DataValidator.isName(request.getParameter("name"))) {
            request.setAttribute("name", "Invalid Subject Name");
            pass = false;
        }

        if(DataValidator.isNull(request.getParameter("courseId"))) {
            request.setAttribute("courseId", PropertyReader.getValue("error.require","CourseId"));
            pass = false;
        }else if(!DataValidator.isLong(request.getParameter("courseId"))) {
            request.setAttribute("courseId", "Invalid Course Name");
            pass = false;
        }

        if(DataValidator.isNull(request.getParameter("description"))) {
            request.setAttribute("description", PropertyReader.getValue("error.require","description "));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {
        SubjectDTO dto = new SubjectDTO();
        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setName(DataUtility.getString(request.getParameter("name")));
        dto.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
        dto.setDescription(DataUtility.getString(request.getParameter("description")));
        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	Long id = DataUtility.getLong(req.getParameter("id"));
    	SubjectModelInt model = ModelFactory.getInstance().getSubjectModel();
    	if(id>0) {
    		try {
    			SubjectDTO dto = model.findByPK(id);
				ServletUtility.setDto(dto, req);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, req, resp);
				return;
			}
    	}
        ServletUtility.forward(getView(), req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String op = DataUtility.getString(req.getParameter("operation"));
        SubjectModelInt model = ModelFactory.getInstance().getSubjectModel();
        if(UserCtl.OP_SAVE.equalsIgnoreCase(op)) {
            SubjectDTO dto = (SubjectDTO) populateDTO(req);
            try {
            	model.add(dto);
                ServletUtility.setDto(dto, req);
                ServletUtility.setSuccessMessage("Subject Added SuccessFully !!!", req);
            }catch(DuplicateRecordException dre) {
                ServletUtility.setDto(dto, req);
                ServletUtility.setErrorMessage("Subject Already Exist !!!", req);
            }catch(ApplicationException ae) {
                ae.printStackTrace();
                ServletUtility.handleException(ae, req, resp);
                return;
            }
            ServletUtility.forward(getView(), req, resp);
        }else if(OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.SUBJECT_CTL, req, resp);
            return;
        }else if(OP_UPDATE.equalsIgnoreCase(op)) {
        	SubjectDTO dto = (SubjectDTO) populateDTO(req);
	       	try {
	               model.update(dto);
	               ServletUtility.setDto(dto, req);
	               ServletUtility.setSuccessMessage("Subject Updated SuccessFully !!!", req);
	           }catch(DuplicateRecordException dre) {
	               ServletUtility.setDto(dto, req);
	               ServletUtility.setErrorMessage("Subject Already Exist !!!", req);
	           }catch(ApplicationException ae) {
	               ae.printStackTrace();
	               ServletUtility.handleException(ae, req, resp);
	               return;
	           }
	       }
	       else if(OP_CANCEL.equalsIgnoreCase(op)) {
	       	 ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, req, resp);
	       	 return;
	       }
	       ServletUtility.forward(getView(), req, resp);
    }

	@Override
	protected String getView() {
		return ORSView.SUBJECT_VIEW;
	}

}