package com.EBook.ServiceImp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.EBook.Service.FileService;
@Service
public class FileServiceImp implements FileService {

	 @Override
	    public String uploadFile(MultipartFile file, String path) throws IOException {
		try {

			
		 String originalFileName = file.getOriginalFilename();
	        String filename = UUID.randomUUID().toString();
	        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
	        String fileNameWithExetension = filename+extension;
	        String fullPathWithFilename = path+fileNameWithExetension;

	            //upload file
	            Files.copy(file.getInputStream(), Paths.get(fullPathWithFilename));
		     return fileNameWithExetension;
		}
		catch(Exception e) {
			e.printStackTrace();
			return "fileNotSaved";
		}
	    }

	    @Override
	    public InputStream getResource(String path, String name) throws FileNotFoundException {

	        String fullPath = path + File.separator + name;
	        InputStream inputStream = new FileInputStream(fullPath);

	        return inputStream;
	    }

	    @Override
	    public Boolean deleteFile(String path, String imageName) throws FileNotFoundException {
	        String fullPath = path+File.separator+imageName;
	        try {
	            Path paths = Paths.get(fullPath);
	            Files.delete(paths);
	            return true;
	        } catch (IOException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }

//	    @Override
//	    public Boolean updateFIle(MultipartFile file, String path,String productId) throws FileNotFoundException {
//
//	        ProductDto productDto = productService.get(productId);
//	        try {
//	            deleteFile(path,productDto.getProductImageName());
//	            String imageName = uploadFile(file, path);
//	            productDto.setProductImageName(imageName);
//	            ProductDto productDto1 = productService.update(productDto,productId);
//	            return true;
//	        } catch (IOException e) {
//	            return false;
//	        }

//	    }
}
