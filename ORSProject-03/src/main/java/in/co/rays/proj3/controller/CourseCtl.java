package in.co.rays.proj3.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.CourseDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.CourseModelInt;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.utill.DataUtility;
import in.co.rays.proj3.utill.DataValidator;
import in.co.rays.proj3.utill.PropertyReader;
import in.co.rays.proj3.utill.ServletUtility;

@WebServlet(name = "CourseCtl", urlPatterns = {"/ctl/CourseCtl"})
public class CourseCtl extends BaseCtl {

    private static final Logger log = Logger.getLogger(CourseCtl.class);
    
    @Override
    protected boolean validate(HttpServletRequest request) {
        log.debug("CourseCtl validate method started");
        boolean pass = true;
        String op = request.getParameter("operation");

        if(OP_LOG_OUT.equalsIgnoreCase(op) || OP_RESET.equalsIgnoreCase(op)) {
            log.debug("Operation is logout or reset, skipping validation");
            return pass;
        }

        if(DataValidator.isNull(request.getParameter("name"))) {
            request.setAttribute("name", PropertyReader.getValue("error.require","Name"));
            pass = false;
            log.debug("Name validation failed");
        } else if(!DataValidator.isName(request.getParameter("name"))) {
            request.setAttribute("name", "Invalid Course Name");
            pass = false;
            log.debug("Name format is invalid");
        }

        if(DataValidator.isNull(request.getParameter("duration"))) {
            request.setAttribute("duration", PropertyReader.getValue("error.require","Duration "));
            pass = false;
            log.debug("Duration validation failed");
        }

        if(DataValidator.isNull(request.getParameter("description"))) {
            request.setAttribute("description", PropertyReader.getValue("error.require","description "));
            pass = false;
            log.debug("Description validation failed");
        }
        
        log.debug("CourseCtl validate method ended with pass = " + pass);
        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {
        log.debug("CourseCtl populateDTO method started");
        CourseDTO dto = new CourseDTO();
        
        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setCourseName(DataUtility.getString(request.getParameter("name")));
        dto.setDescription(DataUtility.getString(request.getParameter("description")));
        dto.setDuration(DataUtility.getString(request.getParameter("duration")));
        
        log.debug("CourseCtl populateDTO method ended");
        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        log.debug("CourseCtl doGet method started");
        
        String op = DataUtility.getString(request.getParameter("operation"));
        long id = DataUtility.getLong(request.getParameter("id"));
        
        CourseModelInt model = ModelFactory.getInstance().getCourseModel();
        
        if(id > 0) {
            try {
                log.debug("Fetching course details for id: " + id);
                CourseDTO dto = model.findByPK(id);
                ServletUtility.setDto(dto, request);
            }catch (ApplicationException e) {
                e.printStackTrace();
                if(e.getClass().toString().equals(e.toString())) {
                	 ServletUtility.handleExceptionDBDown(e, request, response,getView());
                }else {
                	ServletUtility.handleException(e, request, response);
                }
                return;
            }
        }
        
        ServletUtility.forward(getView(), request, response);
        log.debug("CourseCtl doGet method ended");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        log.debug("CourseCtl doPost method started");
        
        String op = DataUtility.getString(request.getParameter("operation"));
        long id = DataUtility.getLong(request.getParameter("id"));
        
        CourseModelInt model = ModelFactory.getInstance().getCourseModel();
        
        if(OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
            CourseDTO dto = (CourseDTO) populateDTO(request);
            
            try {
                if(id > 0) {
                    log.debug("Updating course with id: " + id);
                    model.update(dto);
                    log.info("Course updated successfully: " + dto.getCourseName());
                    ServletUtility.setSuccessMessage("Course is successfully updated", request);
                    ServletUtility.setDto(dto, request);
                } else {
                    log.debug("Adding new course: " + dto.getCourseName());
                    long pk = model.add(dto);
                    log.info("Course added successfully with id: " + pk);
                    ServletUtility.setSuccessMessage("Course is successfully added", request);
                    ServletUtility.setDto(dto, request);
                }
            }  catch (DuplicateRecordException e) {
                log.warn("Duplicate course found: " + dto.getCourseName());
                ServletUtility.setDto(dto, request);
                ServletUtility.setErrorMessage("Course Name already exists", request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                if(e.getClass().toString().equals(e.toString())) {
                	 ServletUtility.handleExceptionDBDown(e, request, response,getView());
                }else {
                	ServletUtility.handleException(e, request, response);
                }
                return;
            }
        } else if(OP_DELETE.equalsIgnoreCase(op)) {
            CourseDTO dto = (CourseDTO) populateDTO(request);
            try {
                log.debug("Deleting course with id: " + id);
                model.delete(dto);
                log.info("Course deleted successfully with id: " + id);
                ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
                return;
            } catch (ApplicationException e) {
                e.printStackTrace();
                if(e.getClass().toString().equals(e.toString())) {
                	 ServletUtility.handleExceptionDBDown(e, request, response,getView());
                }else {
                	ServletUtility.handleException(e, request, response);
                }
                return;
            }
        } else if(OP_CANCEL.equalsIgnoreCase(op)) {
            log.debug("Operation cancelled, redirecting to course list");
            ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
            return;
        } else if(OP_RESET.equalsIgnoreCase(op)) {
            log.debug("Reset operation, redirecting to course form");
            ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
            return;
        }
        
        ServletUtility.forward(getView(), request, response);
        log.debug("CourseCtl doPost method ended");
    }

    @Override
    protected String getView() {
        log.debug("CourseCtl getView method called");
        return ORSView.COURSE_VIEW;
    }
}