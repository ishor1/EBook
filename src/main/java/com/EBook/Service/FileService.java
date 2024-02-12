package com.EBook.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public interface FileService {
//upload File
	 String uploadFile(MultipartFile file,String path) throws IOException;
//get File
	    InputStream getResource(String path,String name) throws FileNotFoundException;
//delete File
	    Boolean deleteFile(String path,String productImageName) throws  FileNotFoundException;
//Upload File
//	    Boolean updateFIle(MultipartFile file,String path,String productId) throws FileNotFoundException;
}
