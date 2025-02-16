package com.lunaciadev.choreographer.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.lunaciadev.choreographer.data.ItemData;
import com.lunaciadev.choreographer.data.UIDataPackage;
import com.lunaciadev.choreographer.types.Priority;
import com.lunaciadev.choreographer.utils.Signal;

public class AddItemPopup extends Dialog {
    private Stage stage;
    private Skin skin;
    private ItemData itemData;

    private SelectBox<String> itemNameField;
    private SelectBox<String> priorityField;
    private TextField amountField;

    /**
     * Emitted when the form is submitted after an add request.
     * 
     * @param itemID     {@link Integer} The ItemID that was given
     * @param priorityID {@link Priority} The PriorityID that was given
     * @param goal       {@link Integer} Manufacturing goal of the item
     */
    public Signal addItemFormSubmitted;

    public AddItemPopup(UIDataPackage uiDataPackage, Stage stage) {
        super("", uiDataPackage.getSkin());
        this.addItemFormSubmitted = new Signal();
        this.skin = uiDataPackage.getSkin();
        this.itemData = uiDataPackage.getItemData();
        this.stage = stage;
        setLayout();
    }

    private void setLayout() {
        Table rootTable = this.getContentTable();
        itemNameField = new SelectBox<String>(skin);
        priorityField = new SelectBox<String>(skin);
        amountField = new TextField("", skin);
        amountField.setTextFieldFilter(new TextFieldFilter.DigitsOnlyFilter());

        Array<String> temp = new Array<>();
        for (int i = 0; i < itemData.getItemDataSize(); i++) {
            temp.add(itemData.getItemName(i));
        }
        itemNameField.setItems(temp);

        temp.clear();

        for (Priority priority : Priority.values()) {
            temp.add(priority.getReadableName());
        }

        priorityField.setItems(temp);

        rootTable.add(new Label("Item Name", skin));
        rootTable.add(new Label("Priority", skin));
        rootTable.add(new Label("Amount", skin));

        rootTable.row();

        rootTable.add(itemNameField);
        rootTable.add(priorityField);
        rootTable.add(amountField);

        TextButton submitForm = new TextButton("OK", skin);
        TextButton cancelForm = new TextButton("Cancel", skin);

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

        stage.addActor(this);
        this.show(stage);
    }
}
