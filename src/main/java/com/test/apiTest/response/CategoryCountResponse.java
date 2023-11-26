package com.test.apiTest.response;

import com.test.apiTest.model.CategoryCount;

public class CategoryCountResponse {
    private String status;
    private String message;
    private CategoryCount data;

    public CategoryCountResponse(String status, String message, CategoryCount data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CategoryCount getData() {
        return data;
    }

    public void setData(CategoryCount data) {
        this.data = data;
    }
}

