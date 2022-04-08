package kittyfacts.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExternalTaskRs {

    String id;
    String processInstanceId;
    Map<String, Object> variables;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public ExternalTaskRs() {
    }
}
