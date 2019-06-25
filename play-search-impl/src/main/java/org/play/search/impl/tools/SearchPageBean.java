package org.play.search.impl.tools;

import java.util.ArrayList;
import java.util.List;

public class SearchPageBean<T> {

	private int currentPage = 1;
    private int totalPages = 0;
    private int pageSize = 0;
    private int totalRows = 0;
    private int startNum = 0;
    private int nextPage = 0;
    private int previousPage = 0;
    private boolean hasNextPage = false;
    private boolean hasPreviousPage = false;
    private List<String> pageCodes;
    private int showPageSize = 8;
    private List<T> result;
    public SearchPageBean() {}

    public SearchPageBean(int pageSize, int currentPage, int totalRows,List<T> result)
    {

        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.totalRows = totalRows;
        this.result=result;
        if ((totalRows % pageSize) == 0)
        {
            totalPages = totalRows / pageSize;
        } else
        {
            totalPages = totalRows / pageSize + 1;
        }

        if (currentPage >= totalPages)
        {
            hasNextPage = false;
            currentPage = totalPages;
        } else
        {
            hasNextPage = true;
        }

        if (currentPage <= 1)
        {
            hasPreviousPage = false;
            currentPage = 1;
        } else
        {
            hasPreviousPage = true;
        }

        startNum = (currentPage - 1) * pageSize;
        nextPage = currentPage + 1;

        if (nextPage >= totalPages)
        {
            nextPage = totalPages;
        }

        previousPage = currentPage - 1;

        if (previousPage <= 1)
        {
            previousPage = 1;
        }
        reflashPageCode();
    }
    
    

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public void reflashPageCode()
    {
        this.pageCodes = new ArrayList<String>();
        if (this.totalPages <= this.showPageSize)
        {
            for (int i = 1; i <= this.totalPages; i++)
            {
                this.pageCodes.add(String.valueOf(i));
            }
            return;
        }
        int middleSide = this.showPageSize >> 1;
        if (this.currentPage <= middleSide)
        {
            for (int i = 1; i <= this.showPageSize; i++)
            {
                this.pageCodes.add(String.valueOf(i));
            }
            this.pageCodes.add(String.valueOf(".."));
            return;
        }
        if ((this.totalPages - this.currentPage) <= middleSide)
        {
            this.pageCodes.add(String.valueOf(".."));
            for (int i = this.showPageSize - 1; i >= 0; i--)
            {
                this.pageCodes.add(String.valueOf(this.totalPages - i));
            }
            return;
        }
        if (middleSide < this.currentPage
                && this.currentPage - (middleSide + 1) > 0)
            this.pageCodes.add(String.valueOf(".."));

        for (int i = 0; i < this.showPageSize; i++)
        {
            this.pageCodes.add(String.valueOf((this.currentPage + i)
                    - middleSide));
        }
        if (middleSide > this.currentPage
                || this.totalPages - (this.currentPage + middleSide) > 0)
            this.pageCodes.add(String.valueOf(".."));
    }

    public boolean isHasNextPage()
    {
        return hasNextPage;
    }

    public boolean isHasPreviousPage()
    {
        return hasPreviousPage;
    }

    /**
     * @return the nextPage
     */
    public int getNextPage()
    {
        return nextPage;
    }

    /**
     * @param nextPage
     *            the nextPage to set
     */
    public void setNextPage(int nextPage)
    {
        this.nextPage = nextPage;
    }

    /**
     * @return the previousPage
     */
    public int getPreviousPage()
    {
        return previousPage;
    }

    /**
     * @param previousPage
     *            the previousPage to set
     */
    public void setPreviousPage(int previousPage)
    {
        this.previousPage = previousPage;
    }

    /**
     * @return the currentPage
     */
    public int getCurrentPage()
    {
        return currentPage;
    }

    /**
     * @param currentPage
     *            the currentPage to set
     */
    public void setCurrentPage(int currentPage)
    {
        this.currentPage = currentPage;
    }

    /**
     * @return the pageSize
     */
    public int getPageSize()
    {
        return pageSize;
    }

    /**
     * @param pageSize
     *            the pageSize to set
     */
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    /**
     * @return the totalPages
     */
    public int getTotalPages()
    {
        return totalPages;
    }

    /**
     * @param totalPages
     *            the totalPages to set
     */
    public void setTotalPages(int totalPages)
    {
        this.totalPages = totalPages;
    }

    /**
     * @return the totalRows
     */
    public int getTotalRows()
    {
        return totalRows;
    }

    /**
     * @param totalRows
     *            the totalRows to set
     */
    public void setTotalRows(int totalRows)
    {
        this.totalRows = totalRows;
    }

    /**
     * @param hasNextPage
     *            the hasNextPage to set
     */
    public void setHasNextPage(boolean hasNextPage)
    {
        this.hasNextPage = hasNextPage;
    }

    /**
     * @param hasPreviousPage
     *            the hasPreviousPage to set
     */
    public void setHasPreviousPage(boolean hasPreviousPage)
    {
        this.hasPreviousPage = hasPreviousPage;
    }

    /**
     * @return the startNum
     */
    public int getStartNum()
    {
        return startNum;
    }

    /**
     * @param startNum
     *            the startNum to set
     */
    public void setStartNum(int startNum)
    {
        this.startNum = startNum;
    }

    public List<String> getPageCodes()
    {
        if (this.pageCodes == null) {
              return new ArrayList<String>();
            }
        return pageCodes;
    }

    public void setPageCodes(List<String> pageCodes)
    {
        this.pageCodes = pageCodes;
    }

    public int getShowPageSize()
    {
        return showPageSize;
    }

    public void setShowPageSize(int showPageSize)
    {
        this.showPageSize = showPageSize;
    }
    
}
