package in.co.rays.proj3.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.SupportDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.model.SupportModelInt;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.utill.DataUtility;
import in.co.rays.proj3.utill.PropertyReader;
import in.co.rays.proj3.utill.ServletUtility;

@WebServlet(name = "SupportListCtl", urlPatterns = { "/ctl/SupportListCtl" })
public class SupportListCtl extends BaseCtl {

    @Override
    protected void preload(HttpServletRequest request) {
        HashMap<String, String> ticketStatusMap = new HashMap<>();
        ticketStatusMap.put("OPEN", "OPEN");
        ticketStatusMap.put("IN PROGRESS", "IN PROGRESS");
        ticketStatusMap.put("CLOSED", "CLOSED");
        request.setAttribute("ticketStatusMap", ticketStatusMap);
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {
        SupportDTO dto = new SupportDTO();
        dto.setUserName(DataUtility.getString(request.getParameter("userName")));
        dto.setIssueType(DataUtility.getString(request.getParameter("issueType")));
        dto.setTicketStatus(DataUtility.getString(request.getParameter("ticketStatus")));
        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
        SupportDTO dto = (SupportDTO) populateDTO(req);
        SupportModelInt model = ModelFactory.getInstance().getSupportModel();

        try {
            List<SupportDTO> list = model.search(dto, pageNo, pageSize);
            List<SupportDTO> next = model.search(dto, pageNo + 1, pageSize);

            if (list.isEmpty() || list == null) {
                ServletUtility.setErrorMessage("No Records found", req);
            }
            ServletUtility.setList(list, req);
            ServletUtility.setDto(dto, req);
            ServletUtility.setPageNo(pageNo, req);
            ServletUtility.setPageSize(pageSize, req);
            req.setAttribute("nextListSize", next.size());
        } catch (ApplicationException e) {
            ServletUtility.handleExceptionDBDown(e, req, resp, getView());
            return;
        }

        ServletUtility.forward(getView(), req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<SupportDTO> list = null;
        List<SupportDTO> next = null;

        int pageNo = DataUtility.getInt(req.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(req.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

        SupportDTO dto = (SupportDTO) populateDTO(req);
        SupportModelInt model = ModelFactory.getInstance().getSupportModel();

        String op = DataUtility.getString(req.getParameter("operation"));

        try {
            if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op)) {
                if (OP_SEARCH.equalsIgnoreCase(op)) {
                    pageNo = 1;
                } else if (OP_NEXT.equalsIgnoreCase(op)) {
                    pageNo++;
                } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
                    pageNo--;
                }
            } else if (OP_RESET.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.SUPPORT_LIST_CTL, req, resp);
                return;
            } else if (OP_NEW.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.SUPPORT_CTL, req, resp);
                return;
            } else if (OP_DELETE.equalsIgnoreCase(op)) {
                pageNo = 1;
                String[] ids = req.getParameterValues("ids");
                if (ids != null && ids.length > 0) {
                    SupportDTO deleteDTO = new SupportDTO();
                    for (String id : ids) {
                        deleteDTO.setId(DataUtility.getLong(id));
                        model.delete(deleteDTO);
                        ServletUtility.setSuccessMessage("Deleted Successfully !!!", req);
                    }
                } else {
                    ServletUtility.setErrorMessage("Select Atleast one Checkbox", req);
                }
            } else if (OP_BACK.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.SUPPORT_LIST_CTL, req, resp);
                return;
            }

            list = model.search(dto, pageNo, pageSize);
            next = model.search(dto, pageNo + 1, pageSize);

            if (list.isEmpty() || list == null) {
                ServletUtility.setErrorMessage("No Records found", req);
            }
            ServletUtility.setList(list, req);
            ServletUtility.setDto(dto, req);
            ServletUtility.setPageNo(pageNo, req);
            ServletUtility.setPageSize(pageSize, req);
            req.setAttribute("nextListSize", next.size());
        } catch (ApplicationException e) {
            e.printStackTrace();
            ServletUtility.handleExceptionDBDown(e, req, resp, getView());
            return;
        }

        ServletUtility.forward(getView(), req, resp);
    }

    @Override
    protected String getView() {
        return ORSView.SUPPORT_LIST_VIEW;
    }
}