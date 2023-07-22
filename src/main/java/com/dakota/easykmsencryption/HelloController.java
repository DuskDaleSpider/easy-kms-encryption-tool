package com.dakota.easykmsencryption;

import com.dakota.easykmsencryption.models.KMSKey;
import com.dakota.easykmsencryption.services.KmsService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class HelloController {

    @FXML ComboBox<KMSKey> keyDropDown;
    @FXML TextArea inputTextArea;
    @FXML TextArea outputTextArea;
    @FXML Button encryptButton;
    @FXML Button decryptButton;
    @FXML CheckBox filterCheckBox;
    @FXML TextField filterTextField;

    private KmsService kmsService;
    private List<KMSKey> kmsKeys;

    public void initialize(){
        kmsService = new KmsService();
        kmsKeys = kmsService.getKmsKeys();

        keyDropDown.getItems().addAll(kmsKeys);
    }

    @FXML
    public void buttonOnClick(ActionEvent event){
        String input = inputTextArea.getText();
        KMSKey selectedKey = keyDropDown.getValue();

        if(event.getSource() == encryptButton)
            outputTextArea.setText(kmsService.encrypt(input, selectedKey.getKeyArn()));
        else if(event.getSource() == decryptButton)
            outputTextArea.setText(kmsService.decrypt(input, selectedKey.getKeyArn()));
    }

    @FXML
    public void inputOnClick(MouseEvent event){
        inputTextArea.setText("");
    }

    @FXML
    public void checkboxOnClick(ActionEvent event){
        filter();
    }

    @FXML
    public void textFieldOnKey(KeyEvent event){
        filter();
    }

    public void filter(){
        ObservableList<KMSKey> items = keyDropDown.getItems();
        items.clear();

        if(filterCheckBox.isSelected()){
            String filter = filterTextField.getText().trim();
            List<KMSKey> filteredKeys = kmsKeys.stream().filter(key -> key.getAliasName().contains(filter)).toList();
            items.addAll(filteredKeys);
        }else{
            items.addAll(kmsKeys);
        }

        keyDropDown.setItems(items);
    }
}