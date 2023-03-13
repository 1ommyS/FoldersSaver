package ru.mai.folderssaver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.mai.folderssaver.dto.FileDTO;
import ru.mai.folderssaver.dto.FileEditDto;
import ru.mai.folderssaver.exception.JwtTokenExpiredException;
import ru.mai.folderssaver.service.FileService;
import ru.mai.folderssaver.util.FileDownloadUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

/**
 * @author 1ommy
 * @version 28.02.2023
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/file")
public class FileController {

    private final FileService fileService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<FileDTO> getAllFiles() {
        return fileService.getAllFiles();
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('MEMBER')")
    public void storeFile(@RequestBody MultipartFile file, @RequestParam String path) throws IOException {
        fileService.storeFile(file, path);
    }

    @GetMapping("/load")
    @PreAuthorize("hasAuthority('MEMBER')")
    public ResponseEntity<?> loadFileByPath(@RequestParam String path) throws IOException {
        FileDownloadUtil downloadUtil = new FileDownloadUtil();

        Resource resource = downloadUtil.getFileAsResource(path);


        if (resource == null) {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }

        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }

    @PutMapping("/edit/my")
    @PreAuthorize("hasAuthority('Member')")
    public void editFile(@RequestBody FileEditDto fileEditDto) throws JwtTokenExpiredException, FileNotFoundException {
        fileService.editFile(fileEditDto);
    }
}
