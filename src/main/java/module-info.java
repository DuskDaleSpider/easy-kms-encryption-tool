module com.dakota.easykmsencryption {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires software.amazon.awssdk.services.kms;
    requires software.amazon.awssdk.regions;
    requires lombok;
    requires aws.encryption.sdk.java;

    opens com.dakota.easykmsencryption to javafx.fxml;
    exports com.dakota.easykmsencryption;
}