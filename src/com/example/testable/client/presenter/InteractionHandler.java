package com.example.testable.client.presenter;

import com.google.inject.ImplementedBy;

@ImplementedBy(PresenterImpl.class)
public interface InteractionHandler {

	void sendNameToServer(String textToServer);

}
