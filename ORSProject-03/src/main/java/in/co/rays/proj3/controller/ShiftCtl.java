package in.co.rays.proj3.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.ShiftDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.ShiftModelInt;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.utill.DataUtility;
import in.co.rays.proj3.utill.DataValidator;
import in.co.rays.proj3.utill.PropertyReader;
import in.co.rays.proj3.utill.ServletUtility;

@WebServlet(name = "ShiftCtl", urlPatterns = "/ctl/ShiftCtl")
public class ShiftCtl extends BaseCtl {

    @Override
    protected void preload(HttpServletRequest request) {
        HashMap<String, String> shiftStatusMap = new HashMap<>();
        shiftStatusMap.put("Available", "Available");
        shiftStatusMap.put("Unavailable", "Unavailable");
        request.setAttribute("shiftStatusMap", shiftStatusMap);
    }

    @Override
    protected boolean validate(HttpServletRequest request) {
        boolean pass = true;
        String op = request.getParameter("operation");

        if (OP_LOG_OUT.equalsIgnoreCase(op) || OP_RESET.equalsIgnoreCase(op)) {
            return pass;
        }

        if (DataValidator.isNull(request.getParameter("shiftCode"))) {
            request.setAttribute("shiftCode", PropertyReader.getValue("error.require", "Shift Code"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("shiftName"))) {
            request.setAttribute("shiftName", PropertyReader.getValue("error.require", "Shift Name"));
            pass = false;
        } else if (!DataValidator.isName(request.getParameter("shiftName"))) {
            request.setAttribute("shiftName", "Invalid Shift Name");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("startTime"))) {
            request.setAttribute("startTime", PropertyReader.getValue("error.require", "Start Time"));
            pass = false;
        } else if (!DataValidator.isDate(request.getParameter("startTime"))) {
            request.setAttribute("startTime", "Invalid Start Time (HH:MM format)");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("endTime"))) {
            request.setAttribute("endTime", PropertyReader.getValue("error.require", "End Time"));
            pass = false;
        } else if (!DataValidator.isDate(request.getParameter("endTime"))) {
            request.setAttribute("endTime", "Invalid End Time (HH:MM format)");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("shiftStatus"))) {
            request.setAttribute("shiftStatus", PropertyReader.getValue("error.require", "Shift Status"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {
        ShiftDTO dto = new ShiftDTO();
        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setShiftCode(DataUtility.getString(request.getParameter("shiftCode")));
        dto.setShiftName(DataUtility.getString(request.getParameter("shiftName")));
        dto.setStartTime(DataUtility.getDate(request.getParameter("startTime")));
        dto.setEndTime(DataUtility.getDate(request.getParameter("endTime")));
        dto.setShiftStatus(DataUtility.getString(request.getParameter("shiftStatus")));
        populateBean(dto, request);
        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = DataUtility.getLong(req.getParameter("id"));
        ShiftModelInt model = ModelFactory.getInstance().getShiftModel();
        if (id > 0) {
            try {
                ShiftDTO dto = model.findByPK(id);
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
        ShiftModelInt model = ModelFactory.getInstance().getShiftModel();

        if (OP_SAVE.equalsIgnoreCase(op)) {
            ShiftDTO bean = (ShiftDTO) populateDTO(req);
            try {
                model.add(bean);
                ServletUtility.setDto(bean, req);
                ServletUtility.setSuccessMessage("Shift Added Successfully !!!", req);
            } catch (DuplicateRecordException dre) {
                ServletUtility.setDto(bean, req);
                ServletUtility.setErrorMessage("Shift Code Already Exist !!!", req);
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
            ServletUtility.redirect(ORSView.SHIFT_CTL, req, resp);
            return;
        } else if (OP_UPDATE.equalsIgnoreCase(op)) {
            ShiftDTO dto = (ShiftDTO) populateDTO(req);
            try {
                model.update(dto);
                ServletUtility.setDto(dto, req);
                ServletUtility.setSuccessMessage("Shift Updated Successfully !!!", req);
            } catch (DuplicateRecordException dre) {
                ServletUtility.setDto(dto, req);
                ServletUtility.setErrorMessage("Shift Code Already Exist !!!", req);
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
            ServletUtility.redirect(ORSView.SHIFT_LIST_CTL, req, resp);
            return;
        }

        ServletUtility.forward(getView(), req, resp);
    }

    @Override
    protected String getView() {
        return ORSView.SHIFT_VIEW;
    }
}