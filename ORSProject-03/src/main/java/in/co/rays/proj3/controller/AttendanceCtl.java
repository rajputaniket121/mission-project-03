package in.co.rays.proj3.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.AttendanceDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.AttendanceModelInt;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.utill.DataUtility;
import in.co.rays.proj3.utill.DataValidator;
import in.co.rays.proj3.utill.PropertyReader;
import in.co.rays.proj3.utill.ServletUtility;

@WebServlet(name = "AttendanceCtl", urlPatterns = "/ctl/AttendanceCtl")
public class AttendanceCtl extends BaseCtl {

    @Override
    protected void preload(HttpServletRequest request) {
        HashMap<String, String> attendanceStatusMap = new HashMap<>();
        attendanceStatusMap.put("Present", "Present");
        attendanceStatusMap.put("Absent", "Absent");
        attendanceStatusMap.put("Leave", "Leave");
        request.setAttribute("attendanceStatusMap", attendanceStatusMap);
    }

    @Override
    protected boolean validate(HttpServletRequest request) {
        boolean pass = true;
        String op = request.getParameter("operation");

        if (OP_LOG_OUT.equalsIgnoreCase(op) || OP_RESET.equalsIgnoreCase(op)) {
            return pass;
        }

        if (DataValidator.isNull(request.getParameter("personName"))) {
            request.setAttribute("personName", PropertyReader.getValue("error.require", "Person Name"));
            pass = false;
        } else if (!DataValidator.isName(request.getParameter("personName"))) {
            request.setAttribute("personName", "Invalid Person Name");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("attendanceDate"))) {
            request.setAttribute("attendanceDate", PropertyReader.getValue("error.require", "Attendance Date"));
            pass = false;
        } else if (!DataValidator.isDate(request.getParameter("attendanceDate"))) {
            request.setAttribute("attendanceDate", PropertyReader.getValue("error.date", "Attendance Date"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("attendanceStatus"))) {
            request.setAttribute("attendanceStatus", PropertyReader.getValue("error.require", "Attendance Status"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("remarks"))) {
            request.setAttribute("remarks", PropertyReader.getValue("error.require", "Remarks"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {
        AttendanceDTO dto = new AttendanceDTO();
        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setPersonName(DataUtility.getString(request.getParameter("personName")));
        dto.setAttendanceDate(DataUtility.getDate(request.getParameter("attendanceDate")));
        dto.setAttendanceStatus(DataUtility.getString(request.getParameter("attendanceStatus")));
        dto.setRemarks(DataUtility.getString(request.getParameter("remarks")));
        populateBean(dto, request);
        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = DataUtility.getLong(req.getParameter("id"));
        AttendanceModelInt model = ModelFactory.getInstance().getAttendanceModel();
        if (id > 0) {
            try {
                AttendanceDTO dto = model.findByPK(id);
                ServletUtility.setDto(dto, req);
            } catch (DatabaseException e) {
                e.printStackTrace();
                ServletUtility.handleExceptionDBDown(e, req, resp, getView());
                return;
            } catch (ApplicationException e) {
                e.printStackTrace();
                if (e.getClass().toString().equals(e.toString())) {
                    ServletUtility.handleExceptionDBDown(e, req, resp, getView());
                } else {
                    ServletUtility.handleException(e, req, resp);
                }
                return;
            }
        }
        ServletUtility.forward(getView(), req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String op = DataUtility.getString(req.getParameter("operation"));
        AttendanceModelInt model = ModelFactory.getInstance().getAttendanceModel();

        if (OP_SAVE.equalsIgnoreCase(op)) {
            AttendanceDTO bean = (AttendanceDTO) populateDTO(req);
            try {
                model.add(bean);
                ServletUtility.setDto(bean, req);
                ServletUtility.setSuccessMessage("Attendance Added Successfully !!!", req);
            } catch (DuplicateRecordException dre) {
                ServletUtility.setDto(bean, req);
                ServletUtility.setErrorMessage("Person Name Already Exist !!!", req);
            } catch (ApplicationException e) {
                e.printStackTrace();
                if (e.getClass().toString().equals(e.toString())) {
                    ServletUtility.handleExceptionDBDown(e, req, resp, getView());
                } else {
                    ServletUtility.handleException(e, req, resp);
                }
                return;
            }
        } else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.ATTENDANCE_CTL, req, resp);
            return;
        } else if (OP_UPDATE.equalsIgnoreCase(op)) {
            AttendanceDTO dto = (AttendanceDTO) populateDTO(req);
            try {
                model.update(dto);
                ServletUtility.setDto(dto, req);
                ServletUtility.setSuccessMessage("Attendance Updated Successfully !!!", req);
            } catch (DuplicateRecordException dre) {
                ServletUtility.setDto(dto, req);
                ServletUtility.setErrorMessage("Person Name Already Exist !!!", req);
            } catch (ApplicationException e) {
                e.printStackTrace();
                if (e.getClass().toString().equals(e.toString())) {
                    ServletUtility.handleExceptionDBDown(e, req, resp, getView());
                } else {
                    ServletUtility.handleException(e, req, resp);
                }
                return;
            }
        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.ATTENDANCE_LIST_CTL, req, resp);
            return;
        }

        ServletUtility.forward(getView(), req, resp);
    }

    @Override
    protected String getView() {
        return ORSView.ATTENDANCE_VIEW;
    }
}