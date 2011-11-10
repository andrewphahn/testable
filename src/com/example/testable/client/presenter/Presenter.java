package com.example.testable.client.presenter;

import com.google.inject.ImplementedBy;

@ImplementedBy(PresenterImpl.class)
public interface Presenter {

	void attach();

}
