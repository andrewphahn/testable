package com.example.testable.client.presenter.view;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.example.testable.client.presenter.InteractionHandler;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Provider;

@Singleton
public class ViewImpl extends Composite implements View {

	private static final Binder BINDER = GWT.create(Binder.class);

	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network " + "connection and try again.";

	@Inject
	protected Provider<InteractionHandler> interacrionHandlerProvider;

	@UiField
	protected TextBox nameField;

	@UiField
	protected Button sendButton;

	@UiField
	protected Label errorLabel;

	private DialogBox dialogBox;

	private Button closeButton;

	private HTML serverResponseLabel;

	private Label textToServerLabel;

	public ViewImpl() {
		super();

		initWidget(BINDER.createAndBindUi(this));

		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();

		createDialog();
	}

	@UiHandler("sendButton")
	public void handleSendButtonClick(ClickEvent event) {
		interacrionHandlerProvider.get().sendNameToServer(nameField.getText());
	}

	@UiHandler("nameField")
	public void handleNameFieldEnter(KeyUpEvent event) {
		if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			interacrionHandlerProvider.get().sendNameToServer(nameField.getText());
		}
	}

	public void createDialog() {
		dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		textToServerLabel = new Label();
		serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				sendButton.setEnabled(true);
				sendButton.setFocus(true);
			}
		});
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public void attach() {
		RootPanel.get().add(this);
	}

	@Override
	public void setErrorLabelText(String text) {
		errorLabel.setText(text);
	}

	@Override
	public void setSendButtonEnabled(boolean enabled) {
		sendButton.setEnabled(enabled);
	}

	@Override
	public void setTextToServerLabelText(String textToServer) {
		textToServerLabel.setText(textToServer);
	}

	@Override
	public void onFailure(String string) {
		dialogBox.setText(string);
		serverResponseLabel.addStyleName("serverResponseLabelError");
		serverResponseLabel.setHTML(SERVER_ERROR);
		dialogBox.center();
		closeButton.setFocus(true);
	}

	@Override
	public void onSuccess(String text, String result) {
		dialogBox.setText(text);
		serverResponseLabel.removeStyleName("serverResponseLabelError");
		serverResponseLabel.setHTML(result);
		dialogBox.center();
		closeButton.setFocus(true);
	}

	@Override
	public void clearServerResponseLabelText() {
		serverResponseLabel.setText("");
	}
}
