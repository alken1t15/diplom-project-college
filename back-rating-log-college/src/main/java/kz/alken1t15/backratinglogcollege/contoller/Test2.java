package kz.alken1t15.backratinglogcollege.contoller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@AllArgsConstructor
public class Test2 {
    @GetMapping("/arr")
    public String test(){
        System.out.println("fsdfsd");
        return "trsaf";
    }
}
