package org.sim;

import java.util.List;
/**
 * Interface implemented by objects that can generate a text description list of their internal states.
 */
public interface IProfilable {
    /**
     * Compiles and returns a list of text lines containing current parameters for display purposes.
     */
    public List<String> GetProfile();
}
