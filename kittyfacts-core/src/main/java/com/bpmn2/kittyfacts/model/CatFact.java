package com.bpmn2.kittyfacts.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)  //Любые поля во входящем json'е не объявленные в классе игнорируются
public class CatFact {

    String fact;
    Integer length;

    public String getFact() {
        return fact;
    }

    public void setFact(String fact) {
        this.fact = fact;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}
