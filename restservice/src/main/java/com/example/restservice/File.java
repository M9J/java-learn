package com.example.restservice;

public class File {

  public int fileId;
  public String fileName;
  public String fileContent;
  public String fileCreatedDate;

  File() {
  }

  File(String fileName, String fileContent) {
    this.fileName = fileName;
    this.fileContent = fileContent;
    this.fileId = 0;
    this.fileCreatedDate = "TODAY";
  }

}