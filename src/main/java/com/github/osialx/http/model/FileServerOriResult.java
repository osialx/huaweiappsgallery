
package com.github.osialx.http.model;

import java.util.List;

public class FileServerOriResult {

    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result {
        public String getResultCode() {
            return resultCode;
        }

        public void setResultCode(String resultCode) {
            this.resultCode = resultCode;
        }

        public UploadFileRsp getUploadFileRsp() {
            return uploadFileRsp;
        }

        public void setUploadFileRsp(UploadFileRsp uploadFileRsp) {
            this.uploadFileRsp = uploadFileRsp;
        }

        private String resultCode;

        private UploadFileRsp uploadFileRsp;
    }

    public static class UploadFileRsp {

        private List<FileInfo> fileInfoList;

        public List<FileInfo> getFileInfoList() {
            return fileInfoList;
        }

        public void setFileInfoList(List<FileInfo> fileInfoList) {
            this.fileInfoList = fileInfoList;
        }
    }
}
