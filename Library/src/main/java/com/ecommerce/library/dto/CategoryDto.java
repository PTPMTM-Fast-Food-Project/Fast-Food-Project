package com.ecommerce.library.dto;

public class CategoryDto {
    private Long id;
    private String name;
    private Long productSize;

    public CategoryDto() {
    }

    public CategoryDto(Long id, String name, Long productSize) {
        this.id = id;
        this.name = name;
        this.productSize = productSize;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getProductSize() {
        return productSize;
    }

    public void setProductSize(Long productSize) {
        this.productSize = productSize;
    }
}