package com.example.restservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FileController {

  private List<File> files = new ArrayList<File>();

  @GetMapping("/file")
  public List<File> getFile(@RequestParam(value = "fileId", defaultValue = "") String fileId) {
    Collections.sort(this.files, new SortByFileName());
    return this.files;
  }

  @PostMapping("/file")
  public File addFile(@RequestBody File file) {
    System.out.println(file);
    File newFile = new File(file.fileName, file.fileContent);
    newFile.fileId = this.files.size() + 1;
    this.files.add(newFile);
    return newFile;
  }

  @PutMapping("/file")
  public String updateFile(@RequestParam(value = "fileId") int fileId, @RequestBody File file) {
    String updateStatus = "Not updated";
    if (fileId > 0) {
      for (File tempFile : this.files) {
        if (tempFile.fileId == fileId) {
          if (tempFile.fileName != file.fileName) {
            tempFile.fileName = file.fileName;
            updateStatus = "Updated successfully";
          }
          if (tempFile.fileContent != file.fileContent) {
            tempFile.fileContent = file.fileContent;
            updateStatus = "Updated successfully";
          }
        }
      }
    }
    return updateStatus;
  }

  @DeleteMapping("/file")
  public String deleteFile(@RequestParam(value="fileId") int fileId) {
    String deleteStatus = "Not deleted";
    if (fileId > 0) {
      int i = 0;
      int toBeDeletedIndex = -1;
      for (File tempFile:this.files) {
        if (tempFile.fileId == fileId) {
          toBeDeletedIndex = i;
          break;
        }
        i++;
      }
      if (toBeDeletedIndex > -1) {
        this.files.remove(toBeDeletedIndex);
        deleteStatus = "Deleted successfully";
      }
    }
    return deleteStatus;
  }

  @GetMapping("/file/dummy/create")
  public int fileDummyCreate() {
    this.addFile(new File("Dropbox", "encoded pdf"));
    this.addFile(new File("Cutecat", "meow may be?"));
    this.addFile(new File("Diary-2JUL2022", "encrypted for no reason"));
    return this.files.size();
  }

  @GetMapping("/file/dummy/reset")
  public int fileDummyReset() {
    this.files.clear();
    return this.files.size();
  }

}

class SortByFileName implements Comparator<File> {
  public int compare(File a, File b) {
    return b.fileId - a.fileId;
  }
}