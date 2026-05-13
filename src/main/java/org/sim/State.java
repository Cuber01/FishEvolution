package org.sim;

public abstract class State<T, E> {
    public T actor = null;

    public State(T actor)
    {
        this.actor = actor;
    }

    public abstract void Enter();
    public abstract void Update();
    public abstract void Draw();
    public abstract void Exit();
    public abstract E CheckTransitions();

}
