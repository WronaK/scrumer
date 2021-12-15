package com.example.scrumer.raport.controller;

import com.example.scrumer.project.entity.Project;
import com.example.scrumer.project.service.ProjectService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/report")
public class RaportController {
    private final ProjectService projectService;

    @GetMapping("/productBacklog/{idProject}")
    public ResponseEntity<Resource> generatedProductBacklogReport(@PathVariable Long idProject) throws FileNotFoundException, JRException, NotFoundException, IllegalAccessException {
        String contentDisposition = "attachment; filename=\"" + "productBacklog.pdf" + "\"";
        Project project = projectService.findById(idProject);

        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(projectService.getProductBacklogByProjectId(idProject));
        JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/templateRaports/ProductBacklog.jrxml"));

        HashMap<String, Object> map = new HashMap<>();
        map.put("idProject", project.getId());
        map.put("projectName", project.getProjectName());
        map.put("productOwnerEmail", project.getProductOwner().getEmail());
        map.put("productOwnerName", project.getProductOwner().getUserDetails().getUsername());

        JasperPrint report = JasperFillManager.fillReport(compileReport, map, jrBeanCollectionDataSource);

        byte[] data = JasperExportManager.exportReportToPdf(report);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new ByteArrayResource(data));
    }
}
