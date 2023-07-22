package com.dakota.easykmsencryption.models;

public class KMSKey {

    String aliasName;
    String aliasArn;
    String keyId;
    String keyArn;

    public KMSKey(String aliasName, String aliasArn, String keyId, String keyArn){
        this.aliasName = aliasName;
        this.aliasArn = aliasArn;
        this.keyId = keyId;
        this.keyArn = keyArn;
    }

    public String getAliasName(){ return aliasName; }
    public void setAliasName(String name) { this.aliasName = name; }

    public String getAliasArn(){ return aliasArn; }
    public void setAliasArn(String arn){ this.aliasArn = arn; }

    public String getKeyId(){ return keyId; }
    public void setKeyId(String id){ this.keyId = id; }

    public String getKeyArn(){ return keyArn; }
    public void setKeyArn(String arn){ this.keyArn = arn; }

    @Override
    public String toString(){
        return String.format("%s (Key: %s)", aliasName, keyId);
    }
}
