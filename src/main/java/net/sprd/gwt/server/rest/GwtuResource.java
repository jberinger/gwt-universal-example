package net.sprd.gwt.server.rest;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import net.sprd.gwt.server.GwtuServer;
import net.sprd.gwt.server.data.TestPageDataLoader;
import net.sprd.gwt.server.dom.Function;
import net.sprd.gwt.shared.TestApp;
import net.sprd.gwt.shared.data.TestPageData;
import net.sprd.gwt.shared.page.data.PageDataLoader;
import net.sprd.gwt.shared.state.TestPageState;
import net.sprd.gwt.shared.state.TestPageStateParser;

@Path(value = "")
public class GwtuResource {
    
    Logger log = Logger.getLogger("net.sprd.gwt.server.rest.resources.GwtuResource");
    
    static {
        GwtuServer.init();
        
        PageDataLoader.setPageDataLoader(new TestPageDataLoader());
    }

    private TestApp testapp = new TestApp();

    @GET
    @Path("{query: .*}")
    @Produces({ MediaType.TEXT_HTML + ";charset=utf-8" })
    public Response page(@PathParam("query") String query) throws Exception {
        try {
            String html = GwtuServer.renderHtml(testapp, query,
                    "<!doctype html>\n<html><body><div id='root'></div></body></html>", "/gwtutest/gwtutest.nocache.js",
                    new Function<TestPageData, String>() {
                        @Override
                        public String apply(TestPageData value) {
                            ObjectWriter ow = new ObjectMapper().writer();
                            try {
                                String json = ow.writeValueAsString(value);
                                return "var pageData = " + json + ";";
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
            
            Response.ResponseBuilder responseBuilder = Response.ok(html);
            return responseBuilder.build();
        } catch (Exception e) {
            log.log(Level.SEVERE, "error query " + query, e);
            throw e;
        } finally {
        }
    }

    @GET
    @Path(value = "/data")
    @Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8" })
    public Response test(@QueryParam("query") String query) throws Exception {
        try {
            TestPageData testPageData = TestPageDataLoader.getInstance()
                    .load(TestPageStateParser.<TestPageState>getParser().parse(query));
            Response.ResponseBuilder responseBuilder = Response.ok(testPageData);
            return responseBuilder.build();
        } catch (Exception e) {
            log.log(Level.SEVERE, "error test " + query, e);
            throw e;
        } finally {
        }
    }
}
