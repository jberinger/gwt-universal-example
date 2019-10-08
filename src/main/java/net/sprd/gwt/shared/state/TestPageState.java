package net.sprd.gwt.shared.state;

import net.sprd.gwt.shared.page.state.PageState;

public class TestPageState implements PageState {
    
    private String query;
    
    public TestPageState(String query) {
        super();
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

}
