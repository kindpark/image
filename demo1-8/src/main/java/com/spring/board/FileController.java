
package com.spring.board;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@Slf4j
public class FileController {
	private FileService files;
	//파일 생성자 통해 전달
	public FileController(FileService files) {
		this.files = files;
	}
	//파일 업로드
	@PostMapping("/uploadFile")
	public FileUpload uploadFile(@RequestParam("file") MultipartFile file) {
		String fileName = files.storeFile(file);
		String fileUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/").path(fileName).toUriString();
		return new FileUpload(fileName, fileUri, file.getContentType(), file.getSize());
	}
	//다중 업로드
	@PostMapping("/uploadFile")
	public List<FileUpload> uploadFile_(@RequestParam("files") MultipartFile[] files){
		//stream으로 컬렉션으 함수형으로 모아주고, 파일 하나씩 올리는 작업
		return Arrays.asList(files).stream().map(file->uploadFile(file)).collect(Collectors.toList());
	}
	@GetMapping("/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, 
			HttpServletRequest request){
		Resource resource = files.loadFileAsResource(fileName);
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		}catch(IOException ioe) {
			//log.info("파일 타입이 결정되지 않았습니다.");
		}
		if(contentType == null) {
			contentType = "application/octet-stream";
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);	
	}
}