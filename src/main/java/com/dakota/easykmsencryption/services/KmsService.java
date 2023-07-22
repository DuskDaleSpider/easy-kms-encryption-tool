package com.dakota.easykmsencryption.services;

import com.amazonaws.encryptionsdk.AwsCrypto;
import com.amazonaws.encryptionsdk.CommitmentPolicy;
import com.amazonaws.encryptionsdk.kmssdkv2.KmsMasterKeyProvider;
import com.dakota.easykmsencryption.models.KMSKey;
import software.amazon.awssdk.services.kms.KmsClient;
import software.amazon.awssdk.services.kms.model.AliasListEntry;
import software.amazon.awssdk.services.kms.model.DescribeKeyRequest;
import software.amazon.awssdk.services.kms.model.DescribeKeyResponse;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class KmsService {

    private final KmsClient client;
    private final AwsCrypto crypto;

    public KmsService(){
        client = KmsClient.builder().build();
        crypto = AwsCrypto.builder().withCommitmentPolicy(CommitmentPolicy.RequireEncryptRequireDecrypt).build();
    }

    public List<KMSKey> getKmsKeys(){
        List<KMSKey> keys = new ArrayList<>();
        List<AliasListEntry> aliases = client.listAliases().aliases().stream()
                .filter(aliasListEntry -> !aliasListEntry.aliasName().contains("alias/aws"))
                .toList();

        for(var alias : aliases){
            DescribeKeyRequest request = DescribeKeyRequest.builder().keyId(alias.targetKeyId()).build();
            DescribeKeyResponse response = client.describeKey(request);
            KMSKey key = new KMSKey(alias.aliasName(), alias.aliasArn(), response.keyMetadata().keyId(), response.keyMetadata().arn());
            keys.add(key);
        }

        return keys;
    }

    public String encrypt(String plaintext, String keyArn){
        String result = "";
        try {
            KmsMasterKeyProvider keyProvider = KmsMasterKeyProvider.builder().buildStrict(keyArn);
            var cryptoResult = crypto.encryptData(keyProvider, plaintext.getBytes(StandardCharsets.UTF_8));
            result = Base64.getEncoder().encodeToString(cryptoResult.getResult());
        } catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        return result;
    }

    public String decrypt(String ciphertext, String keyArn){
        String result = "";
        try{
            KmsMasterKeyProvider keyProvider = KmsMasterKeyProvider.builder().buildStrict(keyArn);
            byte[] decodedCipher = Base64.getDecoder().decode(ciphertext);
            byte[] decrypted = crypto.decryptData(keyProvider, decodedCipher).getResult();
            result = new String(decrypted, StandardCharsets.UTF_8);
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        return  result;
    }

}
