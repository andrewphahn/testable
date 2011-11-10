package com.example.testable.client.presenter;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.example.testable.client.GreetingServiceAsync;
import com.example.testable.client.presenter.view.View;
import com.example.testable.shared.FieldVerifier;
import com.google.gwt.user.client.rpc.AsyncCallback;

@Singleton
public class PresenterImpl implements Presenter, InteractionHandler {

	protected static final String PLEASE_ENTER_AT_LEAST_FOUR_CHARACTERS = "Please enter at least four characters";
	protected static final String REMOTE_PROCEDURE_CALL_FAILURE = "Remote Procedure Call - Failure";
	protected static final String REMOTE_PROCEDURE_CALL = "Remote Procedure Call";

	protected class TextToServerCallback implements AsyncCallback<String> {

		public void onFailure(Throwable caught) {
			// Show the RPC error message to the user
			view.onFailure(REMOTE_PROCEDURE_CALL_FAILURE);

		}

		public void onSuccess(String result) {
			view.onSuccess(REMOTE_PROCEDURE_CALL, result);
		}
	}

	@Inject
	protected View view;

	@Inject
	protected GreetingServiceAsync greetingService;

	@Override
	public void attach() {
		view.attach();
	}

	/**
	 * Send the name from the nameField to the server and wait for a response.
	 */
	@Override
	public void sendNameToServer(String textToServer) {
		// First, we validate the input.
		if (!FieldVerifier.isValidName(textToServer)) {
			view.setErrorLabelText(PLEASE_ENTER_AT_LEAST_FOUR_CHARACTERS);
			return;
		} else {
			view.setErrorLabelText("");
		}

		// Then, we send the input to the server.
		view.setSendButtonEnabled(false);
		view.setTextToServerLabelText(textToServer);
		view.clearServerResponseLabelText();
		greetingService.greetServer(textToServer, new TextToServerCallback());
	}
}
