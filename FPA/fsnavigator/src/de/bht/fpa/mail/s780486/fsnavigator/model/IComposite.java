package de.bht.fpa.mail.s780486.fsnavigator.model;

/**
 * Interface that declares typical methods for the composite pattern. A model
 * shall implement this if the composite pattern is wanted.
 * 
 * @author M. Fischboeck (780486)
 * 
 */
public interface IComposite {

  /**
   * Returns the objects parent
   * 
   * @return The parent object or null if this is the root node
   */
  public Object getParent();

  /**
   * Returns an array of children
   * 
   * @return The array. Might be empty if no children available but never null.
   */
  public Object[] getChildren();

  /**
   * Returns whether the object has children or not
   * 
   * @return Returns true if {@link #getChildren()} would return a non empty
   *         array
   */
  public boolean hasChildren();
}
