package net.sprd.gwt.shared.state;

import net.sprd.gwt.shared.page.state.PageStateParser;

public class TestPageStateParser extends PageStateParser<TestPageState> {

    @Override
    public TestPageState parse(String token) {
        return new TestPageState(token);
    }

}
