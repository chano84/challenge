package com.tenpo.challenge.controller.dto;

import com.tenpo.challenge.model.CalculateRequest;

import java.util.List;

public class PageDTO {

    private List<CalculateRequest> list;

    private Integer number;

    private Integer numberOfElements;

    private Integer totalPages;

    public PageDTO(){}

    public PageDTO(List<CalculateRequest> list, Integer number, Integer numberOfElements, Integer totalPages){
        this.list = list;
        this.number = number;
        this.numberOfElements = numberOfElements;
        this.totalPages = totalPages;
    }

    public List<CalculateRequest> getList() {
        return list;
    }

    public void setList(List<CalculateRequest> list) {
        this.list = list;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(Integer numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
}
