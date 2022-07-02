package com.example.restservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

  @GetMapping("/file/dummy/create")
  public int fileDummyCreate() {
    this.files.add(new File("Dropbox.pdf", "encoded pdf"));
    this.files.add(new File("Cutecat.png", "meow may be?"));
    this.files.add(new File("Diary-2JUL2022.txt", "encrypted for no reason"));
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