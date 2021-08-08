package cn.domain;

import java.util.List;

public class Page<T> {
    private static int totalItems;
    private static int totalPages;
    private int currentPage;
    private static int itemsPerPages;
    private List<T>itemsInThisPage;

    public Page(){};
    public Page(int totalItems, int totalPages, int currentPage, int itemsPerPages, List<T> itemsInThisPage) {
        Page.totalItems = totalItems;
        Page.totalPages = totalPages;
        this.currentPage = currentPage;
        Page.itemsPerPages = itemsPerPages;
        this.itemsInThisPage = itemsInThisPage;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        Page.totalItems = totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        Page.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getItemsPerPages() {
        return itemsPerPages;
    }

    public void setItemsPerPages(int itemsPerPages) {
        Page.itemsPerPages = itemsPerPages;
    }

    public List<T> getItemsInThisPage() {
        return itemsInThisPage;
    }

    public void setItemsInThisPage(List<T> itemsInThisPage) {
        this.itemsInThisPage = itemsInThisPage;
    }

    @Override
    public String toString() {
        return "Page{" +
                "totalItems=" + totalItems +
                ", totalPages=" + totalPages +
                ", currentPage=" + currentPage +
                ", itemsPerPages=" + itemsPerPages +
                ", itemsInThisPage=" + itemsInThisPage +
                '}';
    }
}
