package com.example.testable.client.presenter.view;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

@ImplementedBy(ViewImpl.class)
public interface View extends IsWidget {

	/**
	 * UiBinder interface for this view.
	 */
	@UiTemplate("View.ui.xml")
	interface Binder extends UiBinder<HTMLPanel, ViewImpl> {
	}

	void attach();

	void setErrorLabelText(String text);

	void setSendButtonEnabled(boolean b);

	void setTextToServerLabelText(String textToServer);

	void onFailure(String text);

	void onSuccess(String text, String result);

	void clearServerResponseLabelText();

}
