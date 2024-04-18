package kz.alken1t15.backratinglogcollege.contoller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class TestCont {

    @PostMapping("/test")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void test(@RequestParam("file") MultipartFile file){
        System.out.println(file.getName());
        System.out.println(file.getSize());
        System.out.println(file.getContentType());
    }
}
