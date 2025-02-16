package com.lunaciadev.choreographer.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.lunaciadev.choreographer.data.UIDataPackage;
import com.lunaciadev.choreographer.types.Crate;
import com.lunaciadev.choreographer.types.Priority;
import com.lunaciadev.choreographer.utils.Signal;
import com.lunaciadev.choreographer.widgets.ItemColumn;

public class EditItemPopup extends Dialog {
    private UIDataPackage uiDataPackage;

    private Label itemNameField;
    private SelectBox<String> priorityField;
    private TextField amountField;
    private Stage stage;

    private int itemID;

    /**
     * Emitted when the form is submitted after an edit request.
     * 
     * @param itemID     {@link Integer} The ItemID that was given
     * @param priorityID {@link Priority} The PriorityID that was given
     * @param goal       {@link Integer} Manufacturing goal of the item
     */
    public Signal editItemFormSubmitted;

    public EditItemPopup(UIDataPackage uiDataPackage, Stage stage) {
        super("", uiDataPackage.getSkin());
        this.uiDataPackage = uiDataPackage;
        this.editItemFormSubmitted = new Signal();
        this.stage = stage;
        setLayout();
    }

    private void setLayout() {
        Table rootTable = this.getContentTable();
        itemNameField = new Label("", uiDataPackage.getSkin());
        priorityField = new SelectBox<String>(uiDataPackage.getSkin());
        amountField = new TextField("", uiDataPackage.getSkin());
        amountField.setTextFieldFilter(new TextFieldFilter.DigitsOnlyFilter());

        Array<String> temp = new Array<>();
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
                editItemFormSubmitted.emit(itemID, selectedPriority, Integer.parseInt(amountField.getText()));

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
     * Slot, triggered by {@link ItemColumn#editButtonClicked}
     */
    public void onEditItemButtonClicked(Object... args) {
        Crate crate = (Crate) args[0];

        this.itemID = crate.getId();
        itemNameField.setText(uiDataPackage.getItemData().getItemName(itemID));
        priorityField.setSelected(crate.getPriority().getReadableName());
        amountField.setText(Integer.toString(crate.getQueueNeeded()));

        stage.addActor(this);
        this.show(stage);
    }
}
