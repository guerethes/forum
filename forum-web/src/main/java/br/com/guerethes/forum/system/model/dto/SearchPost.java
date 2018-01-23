package br.com.guerethes.forum.system.model.dto;

public class SearchPost {

    private String query;
    private Integer category;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }
}