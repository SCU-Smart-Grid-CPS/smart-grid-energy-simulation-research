/*
 * File:    processor.java
 * Project:	timesync demo
 * Author:  Brian Woo-Shem
 * Date:	2021-06-16
 * Owner:	Created using resources from Santa Clara University School of Engineering and NIST
 * Purpose:	Processor federate code in timesync demo simulation
 */

// Import packages necessary. Most are added automatically by WebGME
package org.webgme.guest.processor;
import org.cpswt.utils.CpswtUtils; //Added this one for time delay fix
import org.webgme.guest.processor.rti.*;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.InteractionRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Define the processor type of federate for the federation.
// Non-commented code is created automatically by WebGME
public class processor extends processorBase {
    private final static Logger log = LogManager.getLogger();
    
    //Added as to track whether it received stuff from socket yet
    private boolean receivedSimTime = false;
    
    private double currentTime = 0;

    public processor(FederateConfig params) throws Exception {
        super(params);
    }
    
    // Declare variables for use later
    double sockTime;
    double procTime;

    private void checkReceivedSubscriptions() {
        InteractionRoot interaction = null;
        while ((interaction = getNextInteractionNoWait()) != null) {
            if (interaction instanceof sockToProc) {
                handleInteractionClass((sockToProc) interaction);
            }
            else {
                log.debug("unhandled interaction: {}", interaction.getClassName());
            }
        }
    }
    

    private void execute() throws Exception {
        if(super.isLateJoiner()) {
            log.info("turning off time regulation (late joiner)");
            currentTime = super.getLBTS() - super.getLookAhead();
            super.disableTimeRegulation();
        }

        /////////////////////////////////////////////
        // TODO perform basic initialization below //
        /////////////////////////////////////////////

        AdvanceTimeRequest atr = new AdvanceTimeRequest(currentTime);
        putAdvanceTimeRequest(atr);

        if(!super.isLateJoiner()) {
            log.info("waiting on readyToPopulate...");
            readyToPopulate();
            log.info("...synchronized on readyToPopulate");
        }

        ///////////////////////////////////////////////////////////////////////
        // TODO perform initialization that depends on other federates below //
        ///////////////////////////////////////////////////////////////////////
        /*
      //Added to eliminate time delay
        SimTime simTime = create_SimTime();
        simTime.set_timeScale(configuration.timeScale);
        simTime.set_timeZone(configuration.timeZone);
        simTime.set_timeZonePosix(configuration.timeZonePosix);
        simTime.set_unixTimeStart(configuration.unixTimeStart);
        simTime.set_unixTimeStop(configuration.unixTimeStop);
        simTime.sendInteraction(getLRC()); // send RO
        log.info("sent SimTime interaction");
*/

        if(!super.isLateJoiner()) {
            log.info("waiting on readyToRun...");
            readyToRun();
            log.info("...synchronized on readyToRun");
        }

        startAdvanceTimeThread();
        log.info("started logical time progression");


        
        while (!exitCondition) {
            atr.requestSyncStart();
            enteredTimeGrantedState();

            ////////////////////////////////////////////////////////////
            // TODO send interactions that must be sent every logical //
            // time step below                                        //
            ////////////////////////////////////////////////////////////

            // For removing time delay. It waits indefinitely until the handleInteractionClass has run
            while (!receivedSimTime) {
                log.info("waiting to receive SimTime...");
                synchronized (lrc) {
                    lrc.tick();
                }
                checkReceivedSubscriptions();
                if (!receivedSimTime) {
                    CpswtUtils.sleep(1000);
                }
            }
            // When this loop runs each time, reset to false because next time the loop runs it will not have gotten data yet
            receivedSimTime = false;

            // Set the interaction's parameters.
            //
            //    procToCtrl vprocToCtrl = create_procToCtrl();
            //    vprocToCtrl.set_actualLogicalGenerationTime( < YOUR VALUE HERE > );
            //    vprocToCtrl.set_federateFilter( < YOUR VALUE HERE > );
            //    vprocToCtrl.set_originFed( < YOUR VALUE HERE > );
            //    vprocToCtrl.set_procTime( < YOUR VALUE HERE > );
            //    vprocToCtrl.set_sockTime( < YOUR VALUE HERE > );
            //    vprocToCtrl.set_sourceFed( < YOUR VALUE HERE > );
            //    vprocToCtrl.sendInteraction(getLRC(), currentTime + getLookAhead());

            checkReceivedSubscriptions();
            
            // Set the processor's time to be sent to the current time
            // Note: we could have instead skipped this step and simply put in "currentTime" everywhere
            //       there is "procTime" below. It was done this way here because it's easier to explain
            //		 where the info goes.
            procTime = currentTime;
            
            // Create the C2W Interaction root objects through which info is sent across.
            // Copy paste this from the big commented section above labeled "Set the interaction's parameters"
            procToCtrl vprocToCtrl = create_procToCtrl();
            
         // Set the variable sockTime in each of the C2Ws 
            // Copy pasted from above, but replaced "< YOUR VALUE HERE >"
            vprocToCtrl.set_sockTime( sockTime );
            vprocToCtrl.set_procTime( procTime );
            
            // This tells the socket to send the data in the C2Ws.
            // Copy pasted from above, BUT removed the currentTime + getLookAhead() instruction to sendInteraction
//          vprocToCtrl.sendInteraction(getLRC(), currentTime + getLookAhead());
            vprocToCtrl.sendInteraction(getLRC());
            
            // human readable output so we know what was sent
            log.info("=> sent sockTime = {},    procTime = {}", sockTime, procTime);

            ////////////////////////////////////////////////////////////////////
            // TODO break here if ready to resign and break out of while loop //
            ////////////////////////////////////////////////////////////////////

            if (!exitCondition) {
                currentTime += super.getStepSize();
                AdvanceTimeRequest newATR =
                    new AdvanceTimeRequest(currentTime);
                putAdvanceTimeRequest(newATR);
                atr.requestSyncEnd();
                atr = newATR;
            }
        }

        
        // call exitGracefully to shut down federate
        exitGracefully();

        //////////////////////////////////////////////////////////////////////
        // TODO Perform whatever cleanups are needed before exiting the app //
        //////////////////////////////////////////////////////////////////////
    }

    // This method is autocreated empty; we add what to do when it gets the C2W interaction
    // from socket.
    private void handleInteractionClass(sockToProc interaction) {
        ///////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the interaction //
        ///////////////////////////////////////////////////////////////
    	
    	// Define the appropriate variables and call the C2W interaction to get the values sent
    	sockTime = interaction.get_sockTime();
    	
    	//record what this federate received in human readable
    	log.info("From socket, sockTime = {}", sockTime);
    	
    	// The simulation data has been received, so change this so the while loop we added
    	// knows we have the data and can proceed
    	receivedSimTime = true;
    }



    public static void main(String[] args) {
        try {
            FederateConfigParser federateConfigParser =
                new FederateConfigParser();
            FederateConfig federateConfig =
                federateConfigParser.parseArgs(args, FederateConfig.class);
            processor federate =
                new processor(federateConfig);
            federate.execute();
            log.info("Done.");
            System.exit(0);
        }
        catch (Exception e) {
            log.error(e);
            System.exit(1);
        }
    }
}
