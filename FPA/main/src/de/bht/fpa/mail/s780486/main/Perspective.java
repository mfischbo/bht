package de.bht.fpa.mail.s780486.main;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

/**
 * This class defines the layout of our mail application. It adds three
 * placeholders:
 * <ul>
 * <li>left: one placeholder on the left side, taking the whole vertical space</li>
 * <li>top: one placeholder on the right-upper side</li>
 * <li>bottom: one placeholder on the right-lower side</li>
 * </ul>
 * 
 * <pre>
 * --------------------
 * |      |           |
 * |      |    top    |
 * | left |------------
 * |      |           |
 * |      |   bottom  |
 * --------------------
 * </pre>
 * 
 * You can see the visual design of the perspective using the <i>WindowBuilder
 * Editor</i>. Richt-click on Perspective.java, then select open-with ->
 * WindowBuilder Editor). In the opened editor, change to the Design-Tab.
 * <p>
 * You can add Views to the placeholders (left, top, and bottom) by defining a
 * <i>perspective extension</i> in a plugin.xml file:
 * 
 * <pre>
 *   ...
 *   &lt;extension point="org.eclipse.ui.views"&gt;
 *     &lt;view
 *       class="my.fancy.View"
 *       id="my.fancy.View"
 *       name="My Fancy View Name"
 *       restorable="true"&gt;
 *   &lt;/view&gt;
 *   &lt;/extension&gt;
 *   
 *   ...
 *   
 *   &lt;extension point="org.eclipse.ui.perspectiveExtensions"&gt;  
 *     &lt;perspectiveExtension targetID="*"&gt;
 *       &lt;view
 *         closeable="false"
 *         id="my.fancy.View"
 *         relationship="stack"
 *         relative="de.bht.fpa.mail.s&lt;MATRIKELNUMMER&gt;.main.perspective.bottom"
 *       &lt;/view&gt;
 *     &lt;/perspectiveExtension&gt;
 *   &lt;/extension&gt;
 *  ...
 * </pre>
 * 
 * </p>
 * 
 * @author Siamak Haschemi
 */
public class Perspective implements IPerspectiveFactory {
  private static final String PERSPECTIVE_ID = Activator.PLUGIN_ID + ".perspective";

  public static final String LEFT = PERSPECTIVE_ID + ".left";
  public static final String TOP = PERSPECTIVE_ID + ".top";
  public static final String BOTTOM = PERSPECTIVE_ID + ".bottom";

  private static final float BOTTOM_RATIO = 0.50f;
  private static final float TOP_RATIO = 0.50f;
  private static final float LEFT_RATIO = 0.25f;

  @Override
  public void createInitialLayout(IPageLayout layout) {
    String editorAreaId = layout.getEditorArea();
    layout.setEditorAreaVisible(false);
    layout.setFixed(false);
    layout.createPlaceholderFolder(LEFT, IPageLayout.LEFT, LEFT_RATIO, editorAreaId);
    layout.createPlaceholderFolder(TOP, IPageLayout.TOP, TOP_RATIO, editorAreaId);
    layout.createPlaceholderFolder(BOTTOM, IPageLayout.BOTTOM, BOTTOM_RATIO, editorAreaId);
  }
}