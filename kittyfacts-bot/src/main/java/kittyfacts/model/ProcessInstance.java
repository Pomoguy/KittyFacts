package kittyfacts.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcessInstance {

    Map<String, Object> variables;

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    public void setVariable(String name, Object variable) {
        this.variables.put(name, variable);
    }

    public Object getVariable(String name) {
        return this.variables.get(name);
    }

    public ProcessInstance(Map<String, Object> variables) {
        this.variables = variables;
    }

    public ProcessInstance() {

    }
}
