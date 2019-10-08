package net.sprd.gwt.shared;

import static net.sprd.gwt.shared.dom.Attributes.ALT;
import static net.sprd.gwt.shared.dom.Attributes.CLASS;
import static net.sprd.gwt.shared.dom.Attributes.HREF;
import static net.sprd.gwt.shared.dom.Attributes.SRC;
import static net.sprd.gwt.shared.dom.Attributes.STYLE;
import static net.sprd.gwt.shared.dom.Attributes.TEXT;
import static net.sprd.gwt.shared.dom.Attributes.TYPE;
import static net.sprd.gwt.shared.dom.Attributes.VALUE;
import static net.sprd.gwt.shared.dom.Tags.ANCHOR;
import static net.sprd.gwt.shared.dom.Tags.DIV;
import static net.sprd.gwt.shared.dom.Tags.FORM;
import static net.sprd.gwt.shared.dom.Tags.IMG;
import static net.sprd.gwt.shared.dom.Tags.INPUT;

import java.util.List;

import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLFormElement;
import elemental2.dom.HTMLInputElement;
import net.sprd.gwt.client.dom.DomUtil;
import net.sprd.gwt.client.state.UrlService;
import net.sprd.gwt.shared.data.Item;
import net.sprd.gwt.shared.data.TestPageData;
import net.sprd.gwt.shared.page.App;
import net.sprd.gwt.shared.page.state.PageStateParser;
import net.sprd.gwt.shared.state.TestPageState;
import net.sprd.gwt.shared.state.TestPageStateParser;

public class TestApp extends App<TestPageState, TestPageData> {

    private HTMLDivElement main;
    private HTMLFormElement form;
    private HTMLInputElement textInputElement;
    private HTMLInputElement buttonElement;
    private HTMLDivElement tileList;
    
    public TestApp() {
        PageStateParser.setParser(new TestPageStateParser());
        setRootId("root");
    }

    @Override
    protected void render(TestPageState pageState, TestPageData pageData) {
        main = create(DIV, null, STYLE, "font-family: sans-serif;");
        form = create(FORM, main);
        textInputElement = create(INPUT, form, TYPE, "text", VALUE, pageState.getQuery(), STYLE, "margin: 16px;");
        buttonElement = create("input", form, TYPE, "submit");

        buttonElement.addEventListener("click", event -> {
            event.preventDefault();
            UrlService.newUrlToken(textInputElement.value);
        });

        tileList = create(DIV, main, STYLE, "display: block;");

        createItemList(pageData.getItems());
        createSpreadshirtLink(pageState.getQuery());
    }
    
    @Override
    protected void update(TestPageState pageState, TestPageData pageData) {
        textInputElement.value = pageState.getQuery();
        DomUtil.removeAllChildren(tileList);
        createItemList(pageData.getItems());
        main.removeChild(DomGlobal.document.getElementById("link"));
        createSpreadshirtLink(pageState.getQuery());
    }

    private void createItemList(List<Item> items) {
        if (items != null && items.size() > 0) {
            for (Item item : items) {
                HTMLDivElement tile = create(DIV, tileList, STYLE,
                        "display: inline-block; margin: 16px; vertical-align: top;");
                HTMLElement image = create(IMG, tile, SRC, item.getImageUrl(), ALT, item.getName(), STYLE,
                        "height: 200px; width: 200px;");
                create(DIV, tile, TEXT, item.getName(), STYLE,
                        "display: block; max-width: 200px; overflow: hidden; margin-top: 8px;", CLASS, "hidden");

                image.addEventListener("click", event -> {
                    event.preventDefault();
                    DomGlobal.window.alert(item.getName());
                });
            }
        }
    }
    
    private void createSpreadshirtLink(String query) {
        create(ANCHOR, "link", main, CLASS, "", HREF, "https://www.spreadshirt.de/"+query, TEXT, "www.spreadshirt.de/"+query);

    }

    

    @Override
    public HTMLElement getMainElement() {
        return main;
    }

}
