package kz.alken1t15.backratinglogcollege.contoller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/file")
public class TestCont {

    @PostMapping("/test")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void test(@RequestParam("file") MultipartFile file){
        System.out.println(file.getName());
        System.out.println(file.getSize());
        System.out.println(file.getContentType());
        if (!file.isEmpty()){
            try {
                byte[] bytes = file.getBytes();
                System.out.println(file.getOriginalFilename());
                File saveFile = new File(file.getOriginalFilename());
                file.transferTo(saveFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
