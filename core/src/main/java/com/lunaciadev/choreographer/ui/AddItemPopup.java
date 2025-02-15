package com.lunaciadev.choreographer.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.lunaciadev.choreographer.data.UIDataPackage;
import com.lunaciadev.choreographer.types.Crate;
import com.lunaciadev.choreographer.types.Priority;
import com.lunaciadev.choreographer.utils.Signal;

public class AddItemPopup extends Dialog {
    private UIDataPackage uiDataPackage;

    private SelectBox<String> itemNameField;
    private SelectBox<String> priorityField;
    private TextField amountField;

    /**
     * Emitted when the form is submitted.
     * 
     * @param itemID     {@link Integer} The ItemID that was given
     * @param priorityID {@link Priority} The PriorityID that was given
     * @param goal       {@link Integer} Manufacturing goal of the item
     */
    public Signal addItemFormSubmitted;

    public AddItemPopup(UIDataPackage uiDataPackage) {
        super("", uiDataPackage.getSkin());
        this.uiDataPackage = uiDataPackage;
        this.addItemFormSubmitted = new Signal();
        setLayout();
    }

    private void setLayout() {
        Table rootTable = this.getContentTable();
        itemNameField = new SelectBox<String>(uiDataPackage.getSkin());
        priorityField = new SelectBox<String>(uiDataPackage.getSkin());
        amountField = new TextField("", uiDataPackage.getSkin());

        Array<String> temp = new Array<>();
        for (int i = 0; i < uiDataPackage.getItemData().getItemDataSize(); i++) {
            temp.add(uiDataPackage.getItemData().getItemName(i));
        }
        itemNameField.setItems(temp);

        temp.clear();

        for (Priority priority : Priority.values()) {
            temp.add(priority.getReadableName());
        }

        priorityField.setItems(temp);

        rootTable.add(new Label("Item Name", uiDataPackage.getSkin()));
        rootTable.add(new Label("Priority", uiDataPackage.getSkin()));
        rootTable.add(new Label("Amount", uiDataPackage.getSkin()));

        rootTable.row();

        rootTable.add(itemNameField);
        rootTable.add(priorityField);
        rootTable.add(amountField);

        TextButton submitForm = new TextButton("OK", uiDataPackage.getSkin());
        TextButton cancelForm = new TextButton("Cancel", uiDataPackage.getSkin());

        submitForm.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Priority selectedPriority = null;
                int priorityID = priorityField.getSelectedIndex();

                for (Priority priority : Priority.values()) {
                    if (priority.getId() == priorityID) {
                        selectedPriority = priority;
                        break;
                    }
                }

                /**
                 * Null case shouldnt show up at all, as the input is guaranteed to always
                 * return an ID that is being used by Priority
                 */

                addItemFormSubmitted.emit(itemNameField.getSelectedIndex(), selectedPriority,
                        Integer.parseInt(amountField.getText()));
                hide();
            }
        });

        cancelForm.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hide();
            }
        });

        button(submitForm);
        button(cancelForm);
    }

    /**
     * Slot, triggered by {@link MainScreen#addButtonClicked}
     */
    public void onAddNewItemButtonClicked(Object... args) {
        itemNameField.setSelectedIndex(0);
        priorityField.setSelectedIndex(0);
        amountField.setText("");

        Stage stage = (Stage) args[0];
        stage.addActor(this);
        this.show(stage);
    }

    /**
     * Slot, triggered by an ItemEdit Signal (not added yet TODO)
     */
    public void onEditItemButtonClicked(Object... args) {
        Stage stage = (Stage) args[0];
        Crate crate = (Crate) args[1];

        itemNameField.setSelected(uiDataPackage.getItemData().getItemName(crate.getId()));
        priorityField.setSelected(crate.getPriority().getReadableName());
        amountField.setText(Integer.toString(crate.getQueueNeeded()));

        stage.addActor(this);
        this.show(stage);
    }
}
