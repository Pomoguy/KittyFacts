package kittyfacts.controller;


import kittyfacts.model.ProcessInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;


import java.util.HashMap;
import java.util.Map;


@Controller
public class MainController {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${url.kittyfacts}")
    private String url;

    @GetMapping("/")
    public String getForm(Model model) {
        model.addAttribute("error", "");
        return "main";
    }

    @PostMapping("/start")
    public String startProcess(
            @RequestParam String email,
            Model model) {

        try {
            Map<String, Object> variables = new HashMap<>();
            Map<String,String> emailMap = new HashMap<>();
            emailMap.put("value", email);
            emailMap.put("type", "String");
            variables.put("email", emailMap);
            ProcessInstance processInstance = new ProcessInstance(variables);
            ResponseEntity<ProcessInstance> response = restTemplate.postForEntity(url + "/engine-rest/process-definition/key/KittyFact/start", processInstance, ProcessInstance.class);

        } catch (Exception exception) {
            model.addAttribute("error", "Что то пошло не так, пожалуйста попробуйте позднее");
        }


        return "redirect:/";
    }

}
