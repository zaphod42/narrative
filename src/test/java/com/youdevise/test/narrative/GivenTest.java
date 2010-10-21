package com.youdevise.test.narrative;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(JMock.class)
public class GivenTest {
    private Mockery context = new Mockery();
    
    @SuppressWarnings("unchecked")
    @Test public void
    usesTheActorToPerformTheAction() {
        final Actor<String> actor = context.mock(Actor.class);
        final Action<Actor<String>> action = context.mock(Action.class);
        
        context.checking(new Expectations() {{
            oneOf(actor).perform(action);
        }});
        
        Given.the(actor).was_able_to(action);
    }
    
    @SuppressWarnings("unchecked")
    @Test public void
    canPerformanManyActionsInARow() {
        final Actor<String> actor = context.mock(Actor.class);
        final Action<Actor<String>> action = context.mock(Action.class, "first action");
        final Action<Actor<String>> otherAction = context.mock(Action.class, "second action");
        
        final Sequence orderOfActions = context.sequence("Order of the actions");
        context.checking(new Expectations() {{
            oneOf(actor).perform(action); inSequence(orderOfActions);
            oneOf(actor).perform(otherAction); inSequence(orderOfActions);
        }});
        
        Given.the(actor).was_able_to(action)
                        .was_able_to(otherAction);
    }
}