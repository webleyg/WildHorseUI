package com.wildhorsecampground;

import javafx.scene.control.CheckBox;

        import javafx.beans.binding.Bindings;
        import javafx.beans.binding.IntegerBinding;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableSet;
        import javafx.scene.control.Button;
        import javafx.scene.control.CheckBox;

public class MultipleCheckBoxController implements GlobalConstants {
//    private ObservableSet<CheckBox> selectedCheckBoxes = FXCollections.observableSet();
//    private ObservableSet<CheckBox> unselectedCheckBoxes = FXCollections.observableSet();

    private IntegerBinding numCheckBoxesSelected = Bindings.size(selectedCheckBoxes);

    private final int maxNumSelected =  2;

    public void initialize() {
//        configureCheckBox(checkBox1);
//        configureCheckBox(checkBox2);
//        configureCheckBox(checkBox3);

//        submitButton.setDisable(true);


        numCheckBoxesSelected.addListener((obs, oldSelectedCount, newSelectedCount) -> {
//            if (newSelectedCount.intValue() >= maxNumSelected) {
            if (newSelectedCount.intValue() >= selectedCheckBoxes.size()) {
                unselectedCheckBoxes.forEach(cb -> cb.setDisable(true));
                refundMenuItem.setDisable(false);
            } else {
                unselectedCheckBoxes.forEach(cb -> cb.setDisable(false));
                refundMenuItem.setDisable(true);
            }
        });

        this.initialize();

    }

    void configureCheckBox(CheckBox checkBox) {

        if (checkBox.isSelected()) {
            selectedCheckBoxes.add(checkBox);
        } else {
            unselectedCheckBoxes.add(checkBox);
        }

        checkBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                unselectedCheckBoxes.remove(checkBox);
                selectedCheckBoxes.add(checkBox);
            } else {
                selectedCheckBoxes.remove(checkBox);
                unselectedCheckBoxes.add(checkBox);
            }

        });

    }

}