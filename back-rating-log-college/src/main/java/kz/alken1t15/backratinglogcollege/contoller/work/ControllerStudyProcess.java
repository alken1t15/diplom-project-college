package kz.alken1t15.backratinglogcollege.contoller.work;

import kz.alken1t15.backratinglogcollege.dto.work.ProcessDTO;
import kz.alken1t15.backratinglogcollege.dto.work.ProcessReturnDTO;
import kz.alken1t15.backratinglogcollege.service.ServiceStudyProcess;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/process")
@AllArgsConstructor
public class ControllerStudyProcess {
    private ServiceStudyProcess serviceStudyProcess;


    @PostMapping
    public ProcessReturnDTO getStudyProcess(@RequestBody ProcessDTO process){
    return serviceStudyProcess.getStudyProcess(process);
    }

}
