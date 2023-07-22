# Easy KMS Encryption

This tool allows you to encrypt and decrypt plaintext using AWS KMS keys. 

On start up it will fetch all kms key aliases available to the current credentials

## Prerequisites
 * Java 17
 * Maven
 * An AWS Credentials file [\[documentation\]](https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html)

## Running the application
 Run `mvn javafx:run`