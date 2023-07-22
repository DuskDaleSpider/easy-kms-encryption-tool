package com.dakota.easykmsencryption.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
public class KMSKey {

    String aliasName;
    String aliasArn;
    String keyId;
    String keyArn;

    @Override
    public String toString(){
        return String.format("%s (Key: %s)", aliasName, keyId);
    }
}
