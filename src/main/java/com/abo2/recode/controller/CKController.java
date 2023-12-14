package com.abo2.recode.controller;

import com.abo2.recode.dto.fileupload.FileResponse;
import com.abo2.recode.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
@RequestMapping("/api/image")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CKController {
    private final FileUploadService fileUploadService;

    public ResponseEntity<FileResponse> fileUploadFromCKEditor(HttpServletResponse response,
                                                               @RequestPart(value = "image", required = false) MultipartFile image) throws Exception {

        // 이미지를 받아 S3 에 저장 후 url을 json 형태로 반환
        return new ResponseEntity<>(FileResponse.builder().
                uploaded(true).
                url(fileUploadService.upload(image)).
                build(), HttpStatus.OK);
    }
}
