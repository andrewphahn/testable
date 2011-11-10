/**
 * In same package as code you are testing to allow access to protected fields and methods.
 */
package com.example.testable.client.presenter;

import static org.mockito.Mockito.verify;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import atunit.AtUnit;
import atunit.Container;
import atunit.Mock;
import atunit.MockFramework;
import atunit.Unit;

import com.example.testable.client.GreetingServiceAsync;
import com.example.testable.client.presenter.PresenterImpl.TextToServerCallback;
import com.example.testable.client.presenter.view.View;
import com.google.inject.Binder;
import com.google.inject.Module;

@RunWith(AtUnit.class)
@Container(Container.Option.GUICE)
@MockFramework(MockFramework.Option.MOCKITO)
public class PresenterTest extends Assert implements Module {

	private static final String IT_WORKED = "It worked";

	private static final String HELLO_WORLD = "Hello World";

	/**
	 * Inject the class to be tested. Using the Impl gives access to methods not
	 * on the presenter interface w/o casting.
	 */
	@Inject
	@Unit
	PresenterImpl presenter;

	/**
	 * Override the annotation configuration in the code to inject a mock
	 * instance.
	 */
	@Mock
	View view;

	/**
	 * Override the default gin call to GWT.create() to inject a mock instance.
	 */
	@Mock
	GreetingServiceAsync greetingService;

	@Override
	public void configure(Binder binder) {
		// Nothing extra to configure. All injection configuration is already
		// done by annotations in the code or the @Mock annotations above
	}

	/**
	 * Verify the Presenter.attach() delegates to the view.
	 */
	@Test
	public void testAttach() {
		presenter.attach();

		verify(view).attach();
	}

	@Test
	public void testSendToServer() {
		presenter.sendNameToServer(HELLO_WORLD);

		verify(view).setErrorLabelText("");
		verify(view).setSendButtonEnabled(false);
		verify(view).setTextToServerLabelText(HELLO_WORLD);
		verify(view).clearServerResponseLabelText();
		verify(greetingService).greetServer(Mockito.eq(HELLO_WORLD), Mockito.any(TextToServerCallback.class));
	}

	@Test
	public void testSendToServerInvalidText() {
		presenter.sendNameToServer("123");
		verify(view).setErrorLabelText(PresenterImpl.PLEASE_ENTER_AT_LEAST_FOUR_CHARACTERS);
	}

	@Test
	public void testCallbackOnSuccess() {
		TextToServerCallback callback = presenter.new TextToServerCallback();
		callback.onSuccess(IT_WORKED);

		verify(view).onSuccess(PresenterImpl.REMOTE_PROCEDURE_CALL, IT_WORKED);
	}

	@Test
	public void testCallbackOnFailure() {
		TextToServerCallback callback = presenter.new TextToServerCallback();
		callback.onFailure(new Exception());

		verify(view).onFailure(PresenterImpl.REMOTE_PROCEDURE_CALL_FAILURE);
	}
}
