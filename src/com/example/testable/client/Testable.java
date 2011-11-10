package com.example.testable.client;

import com.example.testable.client.gin.Ginjector;
import com.example.testable.client.presenter.Presenter;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Testable implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		Ginjector ginjector = GWT.create(Ginjector.class);

		Presenter presenter = ginjector.getPresenter();

		presenter.attach();

	}
}
