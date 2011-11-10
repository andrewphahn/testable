package com.example.testable.presenter;

import static org.mockito.Mockito.verify;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import atunit.AtUnit;
import atunit.Container;
import atunit.Mock;
import atunit.MockFramework;
import atunit.Unit;

import com.example.testable.client.presenter.PresenterImpl;
import com.example.testable.client.presenter.view.View;
import com.google.inject.Binder;
import com.google.inject.Module;

@RunWith(AtUnit.class)
@Container(Container.Option.GUICE)
@MockFramework(MockFramework.Option.MOCKITO)
public class PresenterTest extends Assert implements Module {

	/**
	 * Inject the class to be tested. Using the Impl gives access to methods not
	 * on the presenter interface w/o casting.
	 */
	@Inject
	@Unit
	PresenterImpl presenter;

	@Mock
	View view;

	@Override
	public void configure(Binder arg0) {

	}

	/**
	 * Verify the Presenter.attach() delegates to the view.
	 */
	@Test
	public void testAttach() {
		presenter.attach();

		verify(view).attach();
	}
}
