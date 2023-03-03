package ru.mai.folderssaver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mai.folderssaver.entity.File;
import ru.mai.folderssaver.entity.User;

/**
 * @author 1ommy
 * @version 28.02.2023
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileDTO {
    private Integer id;

    private String path;

    private Boolean isFile;

    private File parent;

    private User user;
}
