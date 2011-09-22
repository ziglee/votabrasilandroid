package br.com.smartfingers.votabrasil;

import roboguice.config.AbstractAndroidModule;
import br.com.smartfingers.votabrasil.service.GoogleAppQuestionService;
import br.com.smartfingers.votabrasil.service.QuestionService;

public class MyRoboGuiceModule extends AbstractAndroidModule {

	@Override
	protected void configure() {
		bind(QuestionService.class).to(GoogleAppQuestionService.class);
//		bind(QuestionService.class).to(TestQuestionService.class);
	}
}
