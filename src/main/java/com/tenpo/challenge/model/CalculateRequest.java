package com.tenpo.challenge.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "calculate_requests")
public class CalculateRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valueA")
    private Long valueA;

    @Column(name = "valueB")
    private Long valueB;

    @Column(name = "result")
    private Long result;

    @Column(name = "date")
    private LocalDateTime date;

    public CalculateRequest(){}

    public CalculateRequest(Long valueA, Long valueB, Long result, LocalDateTime date) {
        this.valueA = valueA;
        this.valueB = valueB;
        this.result = result;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getValueA() {
        return valueA;
    }

    public void setValueA(Long valueA) {
        this.valueA = valueA;
    }

    public Long getValueB() {
        return valueB;
    }

    public void setValueB(Long valueB) {
        this.valueB = valueB;
    }

    public Long getResult() {
        return result;
    }

    public void setResult(Long result) {
        this.result = result;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "CalculateRequest{" +
                "valueA=" + valueA +
                ", valueB=" + valueB +
                ", result=" + result +
                ", date=" + date +
                '}';
    }
}
