package ru.mai.folderssaver.service;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.mai.folderssaver.dao.FileRepository;
import ru.mai.folderssaver.dto.FileDTO;
import ru.mai.folderssaver.dto.FileEditDto;
import ru.mai.folderssaver.exception.JwtTokenExpiredException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 1ommy
 * @version 28.02.2023
 */
@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository repository;
    private final ModelMapper mapper;
    private Path rootLocation;
    private final JwtService jwtService;


    public void init(String path) throws IOException {
        this.rootLocation = Paths.get("/files/");
        Files.createDirectories(rootLocation);
    }

    public List<FileDTO> getAllFiles() {
        return repository.findAll().stream().map(e -> mapper.map(e, FileDTO.class)).collect(Collectors.toList());
    }


    public void storeFile(MultipartFile file, String path) throws IOException {
        init(path);
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, rootLocation.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public Resource loadFileByPath(String path) throws FileNotFoundException, MalformedURLException {
        Path file = load(path);
        Resource resource = new UrlResource(file.toUri());
        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new FileNotFoundException(
                    "Could not read file: " + path);
        }
    }

    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    public void removeFileByPath(String path) throws IOException {
        FileUtils.deleteDirectory(Paths.get(path).toFile());
    }

    public void editFile(FileEditDto dto) throws JwtTokenExpiredException, FileNotFoundException {
        if (jwtService.isTokenExpired(dto.getToken())) throw new JwtTokenExpiredException("Token is expired");
        PrintWriter prw = new PrintWriter(dto.getPath());
        prw.println(dto.getContent());
        prw.close();
    }
}
