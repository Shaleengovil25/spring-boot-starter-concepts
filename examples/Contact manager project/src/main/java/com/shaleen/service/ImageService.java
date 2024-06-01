package com.shaleen.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;

@Service
public class ImageService {
	
	@Autowired Cloudinary cloudinary;
	
	

	public String uploadImage(MultipartFile contactImage) {
		// TODO Auto-generated method stub
		String filename = UUID.randomUUID().toString();
		
		byte[] data;
		try {
			data = new byte[contactImage.getInputStream().available()];
			contactImage.getInputStream().read(data);
			cloudinary.uploader().upload(data, ObjectUtils.asMap(
					"public_id",filename
					));
			return getUrlFromPublicId(filename);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}



	private String getUrlFromPublicId(String publicId) {
		// TODO Auto-generated method stub

		return cloudinary
				.url()
				.transformation(
						new Transformation<>()
						)
				.generate(publicId);
	}

}
