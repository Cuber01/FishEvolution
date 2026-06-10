package org.sim;
/**
 * Abstract template class for implementing state behaviors in the Finite State Machine (FSM).
 * @param <T> The type of the actor class using this state.
 * @param <E> The enum type representing state names.
 */
public abstract class State<T, E> {
  protected T actor = null;
  public E AssociatedType;

  /**
   * Binds the state to a specific actor object and registers its type.
   */
    public State(T actor, E associatedType)
    {
        this.actor = actor;
        this.AssociatedType = associatedType;
    }

    /**
   * Executed automatically once when the actor enters this behavior state.
   */
    public abstract void Enter();

    /**
   * Executed continuously on every simulation tick while this state remains active.
   */
    public abstract void Update();

    /**
   * Executed automatically once when the actor leaves this behavior state.
   */
    public abstract void Exit();

    /**
   * Checks current environment conditions to decide if the actor should switch to another state.
   * @return The next state type enum, or the current state type if no change is needed.
   */
    public abstract E CheckTransitions();

}
