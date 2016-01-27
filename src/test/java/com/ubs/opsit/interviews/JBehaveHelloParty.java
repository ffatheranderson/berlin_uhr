package com.ubs.opsit.interviews;

import static com.ubs.opsit.interviews.support.BehaviouralTestEmbedder.aBehaviouralTestRunner;
import static org.assertj.core.api.Assertions.assertThat;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Test;

public class JBehaveHelloParty {

    private String first, second;

    @Test
    public void helloParty() throws Exception {
	aBehaviouralTestRunner()
		.usingStepsFrom(this)
		.withStory("hello-party.story")
		.run();
    }

    @When("any text here and the key words $first $second")
    public void whenTheText(String first, String second) {
	this.first = first;
	this.second = second;
    }

    @Then("the result must be as $second $first")
    public void thenPrint(String first, String second) {
	assertThat(first).isEqualTo(this.first);
	assertThat(second).isEqualTo(this.second);
    }

}
