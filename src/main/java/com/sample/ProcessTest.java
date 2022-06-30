package com.sample;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import java.util.ArrayList;
import java.util.List;
import org.kie.api.io.ResourceType;

import java.io.FileReader;
import java.io.StringReader;
import org.kie.api.io.Resource;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import org.kie.api.KieBase;
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

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;



/**
 * This is a sample file to launch a process.
 */
public class ProcessTest {

    public static final void main(String[] args) {
        try {
/*             // load up the knowledge base
	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
        	KieSession kSession = kContainer.newKieSession("ksession-process");

            // start a new process instance
            kSession.startProcess("com.sample.bpmn.hello"); */


            String path = "D:/sample.bpmn";
            String rulePath = "D:/helloObject.drl";
            KieBase base = getKieBase(rulePath);
            KieSession session = base.newKieSession();
            session.insert(new Object());
            session.fireAllRules();
            // session.startProcess("YGIUJOLK::::::::::::::");
            session.dispose();            


        } catch (Throwable t) {
            t.printStackTrace();
        }
    }


    public static KieSession decodeToSession(String... drl) {
        KieHelper kieHelper = new KieHelper();
        for (String s : drl) {
            kieHelper.addContent(s, ResourceType.BPMN2);
        }

        {
            Results results = kieHelper.verify();
            if (results.hasMessages(Message.Level.WARNING, Message.Level.ERROR)) {
                List<Message> messages = results.getMessages(Message.Level.WARNING, Message.Level.ERROR);
                for (Message message : messages) {
                    System.out.print(message.getText());
                }
                throw new IllegalStateException("Compilation errors.");
            }

        }

        KieBaseConfiguration config = kieHelper.ks.newKieBaseConfiguration();
            config.setOption(EventProcessingOption.STREAM);
        KieBase kieBase = kieHelper.build(config);
        KieSession kieSession = kieBase.newKieSession();
        return kieSession;
    }

    
    /**
     * 获取绝对路径下的规则文件对应的KieBase
     * @param classPath     绝对路径/文件目录
     * @return KieBase
     */
    public static KieBase getKieBase(String classPath) throws Exception {
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kfs = kieServices.newKieFileSystem();
        Resource resource = ResourceFactory.newFileResource(classPath);
        kfs.write(resource);
        KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
        if (kieBuilder.getResults().getMessages(Message.Level.ERROR).size() > 0) {
            throw new Exception();
        }
        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository()
                .getDefaultReleaseId());
        return kieContainer.getKieBase();
    }

}
