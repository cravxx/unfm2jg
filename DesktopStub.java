import java.applet.AppletContext;
import java.applet.AppletStub;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * An implementation of <code>AppletStub</code>, optimized for desktop apps. It's not complete though, only the methods needed by Nfm2 are implemented.
 *
 * @author DragShot
 */
class DesktopStub implements AppletStub {

    private final AppletContext context = new DesktopContext();

    @Override
    /**
     * @inheritdoc
     */
    public boolean isActive() {
        return true;
    }

    @Override
    /**
     * @inheritdoc
     */
    public URL getDocumentBase() {
        try {
            return new URL("file:///" + System.getProperty("user.dir") + "/");
        } catch (MalformedURLException ex) {
            return null;
        }
    }

    @Override
    /**
     * @inheritdoc
     */
    public URL getCodeBase() {
        try {
            return new URL("file:///" + System.getProperty("user.dir") + "/");
        } catch (MalformedURLException ex) {
            return null;
        }
    }

    @Override
    /**
     * This method is not implemented.
     */
    public String getParameter(String name) {
        return null;
    }

    @Override
    /**
     * @inheritdoc
     */
    public AppletContext getAppletContext() {
        return context;
    }

    @Override
    /**
     * @inheritdoc
     */
    public void appletResize(int width, int height) {
        /* empty */
    }
}