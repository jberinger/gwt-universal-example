package net.sprd.gwt.shared;

import java.util.List;

import com.google.gwt.user.client.Window;

import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLFormElement;
import elemental2.dom.HTMLImageElement;
import elemental2.dom.HTMLInputElement;
import net.sprd.gwt.client.dom.DomUtil;
import net.sprd.gwt.client.state.UrlService;
import net.sprd.gwt.shared.data.Item;
import net.sprd.gwt.shared.data.TestPageData;
import net.sprd.gwt.shared.dom.Tags;
import net.sprd.gwt.shared.page.App;
import net.sprd.gwt.shared.page.state.PageStateParser;
import net.sprd.gwt.shared.state.TestPageState;
import net.sprd.gwt.shared.state.TestPageStateParser;

public class TestApp2 extends App<TestPageState, TestPageData> {

    private HTMLDivElement main;
    private HTMLFormElement form;
    private HTMLInputElement textInputElement;
    private HTMLInputElement buttonElement;
    private HTMLDivElement tileList;

    public TestApp2() {
        PageStateParser.setParser(new TestPageStateParser());
        setRootId("root");
    }

    @Override
    protected void render(TestPageState pageState, TestPageData pageData) {
        main = create(Tags.DIV, null);
        main.style.fontFamily = "sans-serif";
        form = create(Tags.FORM, main);
        textInputElement = create(Tags.INPUT, form);
        textInputElement.type = "text";
        textInputElement.value = pageState.getQuery();
        textInputElement.style.margin = css().margin("16px");
        buttonElement = create(Tags.INPUT, form);
        buttonElement.type = "submit";

        buttonElement.addEventListener("click", event -> {
            event.preventDefault();
            UrlService.newUrlToken(textInputElement.value);
        });

        tileList = create(Tags.DIV,"tilelist", main);
        tileList.style.display = "block";

        createItemList(pageData.getItems());
    }

    private void createItemList(List<Item> items) {
        if (items != null && items.size() > 0) {
            int i = 0;
            for (final Item item : items) {
                HTMLDivElement tile = create(Tags.DIV, "tile-"+(++i), tileList);
                tile.style.display="inline-block";
                tile.style.verticalAlign="top";
                tile.style.margin = css().margin("16px");
                HTMLImageElement tileImg = create(Tags.IMG, tile);
                tileImg.src = item.getImageUrl();
                tileImg.alt = item.getName();
                tileImg.style.height = css().height("200px");
                tileImg.style.width = css().width("200px");
                HTMLDivElement tileLabel = create(Tags.DIV, tile);
                tileLabel.textContent = item.getName();
                tileLabel.style.display = "block";
                tileLabel.style.overflow = "hidden";
                tileLabel.style.maxWidth = css().maxWidth("200px");
                tileLabel.style.marginTop = css().marginTop("8px");

                tile.addEventListener("click", event -> {
                    event.preventDefault();
                    Window.alert(item.getName());;
                });
            }
        }
    }

    @Override
    protected void update(TestPageState pageState, TestPageData pageData) {
        textInputElement.value = pageState.getQuery();
        DomUtil.removeAllChildren(tileList);
        createItemList(pageData.getItems());
    }
    
    @Override
    public HTMLElement getMainElement() {
        return main;
    }

}
