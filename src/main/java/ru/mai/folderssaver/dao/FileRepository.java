package ru.mai.folderssaver.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mai.folderssaver.entity.File;

import java.util.List;

/**
 * @author 1ommy
 * @version 27.02.2023
 */
public interface FileRepository extends JpaRepository<File, Integer> {
    List<File> findAll();
}
