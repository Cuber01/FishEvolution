package org.sim;

import java.util.List;
/**
 * Interface implemented by objects that can generate a text description list of their internal states.
 * The profile info is seen when clicking on a fish.
 */
public interface IProfilable {
    /**
     * Compiles and returns a list of text lines containing current parameters.
     */
    public List<String> GetProfile();
}
