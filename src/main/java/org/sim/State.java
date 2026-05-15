package org.sim;

public abstract class State<T, E> {
    public T actor = null;
    public E AssociatedType;

    public State(T actor, E associatedType)
    {
        this.actor = actor;
        this.AssociatedType = associatedType;
    }

    public abstract void Enter();
    public abstract void Update();
    public abstract void Exit();
    public abstract E CheckTransitions();

}
