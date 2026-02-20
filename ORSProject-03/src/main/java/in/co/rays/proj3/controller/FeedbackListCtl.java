package in.co.rays.proj3.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.FeedbackDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.model.FeedbackModelInt;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.utill.DataUtility;
import in.co.rays.proj3.utill.PropertyReader;
import in.co.rays.proj3.utill.ServletUtility;

@WebServlet(name = "FeedbackListCtl", urlPatterns = { "/ctl/FeedbackListCtl" })
public class FeedbackListCtl extends BaseCtl {

    @Override
    protected void preload(HttpServletRequest request) {
    	 HashMap<String, String> ratingMap = new HashMap<>();
         ratingMap.put("1 Stars", "1 Stars");
         ratingMap.put("2 Stars", "2 Stars");
         ratingMap.put("3 Stars", "3 Stars");
         ratingMap.put("4 Stars", "4 Stars");
         ratingMap.put("5 Stars", "5 Stars");
        request.setAttribute("ratingMap", ratingMap);
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {
        FeedbackDTO dto = new FeedbackDTO();
        dto.setFeedbackCode(DataUtility.getString(request.getParameter("feedbackCode")));
        dto.setCustomerName(DataUtility.getString(request.getParameter("customerName")));
        dto.setRating(DataUtility.getString(request.getParameter("rating")));
        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
        FeedbackDTO dto = (FeedbackDTO) populateDTO(req);
        FeedbackModelInt model = ModelFactory.getInstance().getFeedbackModel();

        try {
            List<FeedbackDTO> list = model.search(dto, pageNo, pageSize);
            List<FeedbackDTO> next = model.search(dto, pageNo + 1, pageSize);

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

        List<FeedbackDTO> list = null;
        List<FeedbackDTO> next = null;

        int pageNo = DataUtility.getInt(req.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(req.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

        FeedbackDTO dto = (FeedbackDTO) populateDTO(req);
        FeedbackModelInt model = ModelFactory.getInstance().getFeedbackModel();

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
                ServletUtility.redirect(ORSView.FEEDBACK_LIST_CTL, req, resp);
                return;
            } else if (OP_NEW.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.FEEDBACK_CTL, req, resp);
                return;
            } else if (OP_DELETE.equalsIgnoreCase(op)) {
                pageNo = 1;
                String[] ids = req.getParameterValues("ids");
                if (ids != null && ids.length > 0) {
                    FeedbackDTO deleteDTO = new FeedbackDTO();
                    for (String id : ids) {
                        deleteDTO.setId(DataUtility.getLong(id));
                        model.delete(deleteDTO);
                        ServletUtility.setSuccessMessage("Deleted Successfully !!!", req);
                    }
                } else {
                    ServletUtility.setErrorMessage("Select Atleast one Checkbox", req);
                }
            } else if (OP_BACK.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.FEEDBACK_LIST_CTL, req, resp);
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
        return ORSView.FEEDBACK_LIST_VIEW;
    }
}