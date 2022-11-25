package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.util.SqlProvider;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import java.util.List;

@Mapper
public interface FileMapper {

    @InsertProvider(value = SqlProvider.class, method = "insertFile")
    int insertFile(File file);

    @SelectProvider(value = SqlProvider.class, method = "getFilesByUserId")
    List<File> getFilesByUserId(int userId);

    @DeleteProvider(value = SqlProvider.class, method = "deleteFileById")
    int deleteFileById(int fileId);

    @SelectProvider(value = SqlProvider.class, method = "getFileById")
    File getFileById(int fileId);

    @SelectProvider(value = SqlProvider.class, method = "getFileByFileName")
    File getFileByFileName(String fileName);
}
