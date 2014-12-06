package funcionalidade;

import models.Serie;
import org.junit.*;

import play.twirl.api.Content;

import java.util.LinkedList;
import java.util.List;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest {

    @Test
    public void simpleCheck() {
        int a = 1 + 1;
        assertThat(a).isEqualTo(2);
    }

    @Test
    public void renderTemplate() {
        List<Serie> list = new LinkedList<Serie>();
        Content html = views.html.index.render(list);
        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("Series");
    }


}
