package com.sample;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;

import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;


import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;;

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
            KieSession kSession = kContainer.newKieSession("ksession-rules");

            // NOTE Create a session to load the .dri rules from the resource
            {
                // NOTE Expose global variable to Drools Engine
                kSession.setGlobal("list", DroolsTest.messageList);
                // go !
                insertMessageToSession(kSession);
                insertMapToSession(kSession);
                //NOTE Assign a evenListener for Debug 
                kSession.addEventListener( new DebugRuleRuntimeEventListener() );

                kSession.fireAllRules();
                System.out.println( "After first session  size is :" + DroolsTest.messageList.size());
                // assertSame(message, DroolsTest.messageList.get(0));
                //NOTE Always remember to call dispose so as to release the memory
                kSession.dispose();

            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        System.out.println("After first session released: list size is :" + DroolsTest.messageList.size());

    }

    public static void insertMessageToSession(final KieSession kSession){
        Message message = new Message();
        message.setMessage("Hello World");
        message.setStatus(Message.HELLO);
        kSession.insert(message);
    }

    public static void insertMapToSession(final KieSession kSession){
        HashMap<String, String> Sites = new HashMap<String, String>();
        Sites.put("hometown", "China");
        Sites.put("age", "18");
        Sites.put("skill", "C++");
        System.out.println(Sites.toString());
        kSession.insert(Sites);
    }

    public static class Message {

        public static final int HELLO = 0;
        public static final int GOODBYE = 1;
        public static final int GOODNIGHT = 2;

        private String message;

        private int status;

        private int length;

        private String hometown = "China";

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
