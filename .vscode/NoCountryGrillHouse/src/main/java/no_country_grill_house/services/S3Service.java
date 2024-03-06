package no_country_grill_house.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import no_country_grill_house.models.dtos.FotoPlatilloDto;
import no_country_grill_house.models.dtos.PlatilloDto;
import no_country_grill_house.models.enums.Rol;

public interface S3Service {

    String uploadFileUsuario(MultipartFile file, String username, Rol rol) throws IOException;

    String downloadFile(String fileName) throws IOException;

    String deleteFile(String fileName) throws IOException;

    String updateFileUsuario(MultipartFile file, String username, Rol rol) throws IOException;

    FotoPlatilloDto uploadFilePlatillo(MultipartFile file) throws IOException;

    String updateFilePlatillo(MultipartFile file, PlatilloDto platilloDto) throws IOException;

}
