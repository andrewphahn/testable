package com.example.testable.client.presenter;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.example.testable.client.GreetingServiceAsync;
import com.example.testable.client.presenter.view.View;
import com.example.testable.shared.FieldVerifier;
import com.google.gwt.user.client.rpc.AsyncCallback;

@Singleton
public class PresenterImpl implements Presenter, InteractionHandler {

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
		view.setErrorLabelText("");
		if (!FieldVerifier.isValidName(textToServer)) {
			view.setErrorLabelText("Please enter at least four characters");
			return;
		}

		// Then, we send the input to the server.
		view.setSendButtonEnabled(false);
		view.setTextToServerLabelText(textToServer);
		view.clearServerResponseLabelText();
		greetingService.greetServer(textToServer, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				// Show the RPC error message to the user
				view.onFailure("Remote Procedure Call - Failure");

			}

			public void onSuccess(String result) {
				view.onSuccess("Remote Procedure Call", result);
			}
		});
	}
}
