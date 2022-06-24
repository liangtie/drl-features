package com.sample;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;;

/**
 * This is a sample class to launch a rule.
 */
public class DroolsTest {

    public static List<Message> messageList = new ArrayList<DroolsTest.Message>();

    public static final void main(String[] args) {
        try {

            // NOTE Mandatory steps to initiate the sessions;
            // load up the knowledge base
            KieServices ks = KieServices.Factory.get();
            KieContainer kContainer = ks.getKieClasspathContainer();

            // NOTE Create a session to load the .dri rules from the resource
            {
                KieSession kSession = kContainer.newKieSession("ksession-rules");
                // NOTE Expose global variable to Drools Engine
                kSession.setGlobal("list", DroolsTest.messageList);
                // go !

                //NOTE Assign a evenListener for Debug 
                kSession.addEventListener( new DebugRuleRuntimeEventListener() );
                Message message = new Message();
                message.setMessage("Hello World");
                message.setStatus(Message.HELLO);
                kSession.insert(message);
                kSession.fireAllRules();
                System.out.println( "After first session  size is :" + DroolsTest.messageList.size());
                assertSame(message, DroolsTest.messageList.get(0));
                //NOTE Always remember to call dispose so as to release the memory
                kSession.dispose();

            }
            // NOTE Create a session to load the .xls decision table from the resource
            {
                KieSession kSession = kContainer.newKieSession("ksession-dtables");

                // go !
                Message message = new Message();
                message.setMessage("BBB");
                message.setStatus(Message.HELLO);
                kSession.insert(message);
                kSession.fireAllRules();
                kSession.dispose();

            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        System.out.println("After first session released: list size is :" + DroolsTest.messageList.size());

    }

    public static class Message {

        public static final int HELLO = 0;
        public static final int GOODBYE = 1;
        public static final int GOODNIGHT = 2;

        private String message;

        private int status;

        private int length;

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
            this.length = message.length();
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getLength() {
            return this.length;
        }

    }

}
