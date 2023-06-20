package com.edu.pe.sigeriope.bean.datatables;

import java.util.List;

public class DataTableResponse<t> {
    private List<t> data;
    private int draw;
    private int iTotalDisplayRecords;
    private int iTotalRecords;

    public List<t> getData() {
        return data;
    }

    public void setData(List<t> data) {
        this.data = data;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    public int getiTotalRecords() {
        return iTotalRecords;
    }

    public void setiTotalRecords(int iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
    }
}
