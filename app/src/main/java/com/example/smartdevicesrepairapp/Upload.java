package com.example.smartdevicesrepairapp;

import com.google.firebase.database.Exclude;

public class Upload {
    public String reqID;
    public String reqType;
    public String reqName;
    public String reqModel;
    public String reqIssue;
    public String reqAddress;

    public String mKey;

    public Upload() {
    }

    public Upload(String reqID, String reqType, String reqName, String reqModel, String reqIssue, String reqAddress) {
        this.reqID = reqID;
        this.reqType = reqType;
        this.reqName = reqName;
        this.reqModel = reqModel;
        this.reqIssue = reqIssue;
        this.reqAddress= reqAddress;
    }

    public String getReqID() {
        return reqID;
    }

    public void setReqID(String reqID) {
        this.reqID = reqID;
    }

    public String getReqType() {
        return reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType;
    }

    public String getReqName() {
        return reqName;
    }

    public void setReqName(String reqName) {
        this.reqName = reqName;
    }

    public String getReqModel() {
        return reqModel;
    }

    public void setReqModel(String reqModel) {
        this.reqModel = reqModel;
    }

    public String getReqIssue() {
        return reqIssue;
    }

    public void setReqIssue(String reqIssue) {
        this.reqIssue = reqIssue;
    }

    public String getReqAddress() {
        return reqAddress;
    }

    public void setReqAddress(String reqAddress) {
        this.reqAddress = reqAddress;
    }

    @Exclude
    public void setKey(String Key) {
        mKey = Key;
    }
}
