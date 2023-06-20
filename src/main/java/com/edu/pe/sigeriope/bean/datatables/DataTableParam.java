/**
 * 
 */
package com.edu.pe.sigeriope.bean.datatables;

import java.util.List;

/**
 * @author dasamo
 *
 */
public class DataTableParam {
	private int draw;
    private int start;
    private int length;

    private Search search;
    
    private String searchColumn;

    private List<Order> order;

    private List<Column> columns;

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public Search getSearch() {
		return search;
	}

	public void setSearch(Search search) {
		this.search = search;
	}

	public List<Order> getOrder() {
		return order;
	}

	public void setOrder(List<Order> order) {
		this.order = order;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
	
	public String getSearchColumn() {
		return searchColumn;
	}

	public void setSearchColumn(String searchColumn) {
		this.searchColumn = searchColumn;
	}
	
	public String paginate(String sql) {
		return  sql+" OFFSET "+this.start+" ROWS FETCH NEXT "+this.length+" ROWS ONLY";
	}

	@Override
	public String toString() {
		return "DataTablesParam [draw=" + draw + ", start=" + start + ", length=" + length + ", search=" + search
				+ ", searchColumn=" + searchColumn + ", order=" + order + ", columns=" + columns + "]";
	}

}
