package kittyfacts.controller;


import kittyfacts.model.Task;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;




@Controller
public class MainController {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${url.kittyfacts}")
    private String url;

    @GetMapping("/list")
    public String itSystemGetProfile(Model model) {
        ResponseEntity<Task[]> taskList = restTemplate.getForEntity(url + "/engine-rest/task?processDefinitionKey=KittyFact", Task[].class);

        model.addAttribute("tasks", taskList.getBody());
        return "main";
    }

}
