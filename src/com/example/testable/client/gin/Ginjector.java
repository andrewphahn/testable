package com.example.testable.client.gin;

import com.example.testable.client.presenter.Presenter;
import com.google.gwt.inject.client.GinModules;

@GinModules(GinModule.class)
public interface Ginjector extends com.google.gwt.inject.client.Ginjector {

	Presenter getPresenter();
}
