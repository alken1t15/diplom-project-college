package kz.alken1t15.backratinglogcollege.contoller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

@RestController
@RequestMapping("/file")
public class TestCont {

    @PostMapping("/test")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void test(@RequestParam("file") MultipartFile file){
        if (!file.isEmpty()){
            try {
                String path= "C:\\fileDiplomProject\\";
                String uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(path,uniqueFileName);
                file.transferTo(filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
