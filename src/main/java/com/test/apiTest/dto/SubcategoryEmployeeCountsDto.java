package com.test.apiTest.dto;

public class SubcategoryEmployeeCountsDto {
    private Long id;
    private String subcategory;
    private Integer[] levelCounts;
    private Long count;

    private Long grantTotalEmp;

    public SubcategoryEmployeeCountsDto(Integer id, String subcategory, Integer[] levelCounts, Long count, Long grantTotalEmp) {
        this.id = Long.valueOf(id);
        this.subcategory = subcategory;
        this.levelCounts = levelCounts;
        this.count = count;
        this.grantTotalEmp = grantTotalEmp;
    }

    public Long getGrantTotalEmp() {
        return grantTotalEmp;
    }

    public void setGrantTotalEmp(Long grantTotalEmp) {
        this.grantTotalEmp = grantTotalEmp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return subcategory;
    }

    public void setCategory(String category) {
        this.subcategory = category;
    }

    public Integer[] getLevelCounts() {
        return levelCounts;
    }

    public void setLevelCounts(Integer[] levelCounts) {
        this.levelCounts = levelCounts;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Integer count : levelCounts) {
            sb.append(count).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
