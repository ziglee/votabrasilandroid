package br.com.smartfingers.votabrasil;

import roboguice.config.AbstractAndroidModule;
import br.com.smartfingers.votabrasil.service.QuestionService;
import br.com.smartfingers.votabrasil.service.TestQuestionService;

public class MyRoboGuiceModule extends AbstractAndroidModule {

	@Override
	protected void configure() {
//		bind(QuestionService.class).to(GoogleAppQuestionService.class);
		bind(QuestionService.class).to(TestQuestionService.class);
	}
}
