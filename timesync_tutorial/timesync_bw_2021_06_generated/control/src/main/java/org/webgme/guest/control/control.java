/*
 * File:    control.java
 * Project:	timesync demo
 * Author:  Brian Woo-Shem
 * Date:	2021-06-16
 * Purpose:	Control federate code in timesync demo simulation
 */

package org.webgme.guest.control;
import org.cpswt.utils.CpswtUtils; //Added for time delay fix
import org.webgme.guest.control.rti.*;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.InteractionRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Define the control type of federate for the federation.
// Non-commented code is created automatically by WebGME
public class control extends controlBase {
    private final static Logger log = LogManager.getLogger();
    
    //For removing time delay
    // Each boolean tracks whether the interaction has been handled yet
    private boolean receivedSimTime = false;
    private boolean recProc = false;
    
    private double currentTime = 0;

    public control(FederateConfig params) throws Exception {
        super(params);
    }

    private void checkReceivedSubscriptions() {
        InteractionRoot interaction = null;
        while ((interaction = getNextInteractionNoWait()) != null) {
            if (interaction instanceof procToCtrl) {
                handleInteractionClass((procToCtrl) interaction);
            }
            else if (interaction instanceof sockToCtrl) {
                handleInteractionClass((sockToCtrl) interaction);
            }
            else {
                log.debug("unhandled interaction: {}", interaction.getClassName());
            }
        }
    }
    
    // Variables to declare
    double sockTime;
    double sockTimeProc;
    double procTime;
    double ctrlTime;
    double elapSock;
    double elapProc;
    
    
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
            
            //Added to remove time delay
            // Repeatedly checks if both interactions have been received, and if not
            // holds the rest of this from running until they are received.
            while (!receivedSimTime || !recProc) {
                log.info("waiting to receive SimTime...");
                synchronized (lrc) {
                    lrc.tick();
                }
                checkReceivedSubscriptions();
                if (!receivedSimTime || !recProc) {
                    CpswtUtils.sleep(1000);
                }
            }
            // Once out of above loop, the stuff below will run. Set these back to false
            // so that next timestep when it runs again, it will say it's not received
            // until the data is received.
            receivedSimTime = false;
            recProc = false;

            // Set the interaction's parameters.
            //
            //    ctrlToSock vctrlToSock = create_ctrlToSock();
            //    vctrlToSock.set_actualLogicalGenerationTime( < YOUR VALUE HERE > );
            //    vctrlToSock.set_ctrlTime( < YOUR VALUE HERE > );
            //    vctrlToSock.set_elapsedFromSock( < YOUR VALUE HERE > );
            //    vctrlToSock.set_elapsedThruProc( < YOUR VALUE HERE > );
            //    vctrlToSock.set_federateFilter( < YOUR VALUE HERE > );
            //    vctrlToSock.set_originFed( < YOUR VALUE HERE > );
            //    vctrlToSock.set_sourceFed( < YOUR VALUE HERE > );
            //    vctrlToSock.sendInteraction(getLRC(), currentTime + getLookAhead());

            checkReceivedSubscriptions();
            
            // Set the control time to be sent to the current time
            // Note: we could have instead skipped this step and simply put in "currentTime" everywhere
            //       there is "ctrlTime" below. It was done this way here because it's easier to explain
            //		 where the info goes.
            ctrlTime = currentTime;
            // Calculate elapsed time, using the times received from socket and processor
            elapSock = ctrlTime - sockTime;
            elapProc = ctrlTime - procTime;
            
            // Create the C2WInteractionRoot called ctrlToSock
            ctrlToSock vctrlToSock = create_ctrlToSock();
            
            //Put in values for variables
            vctrlToSock.set_ctrlTime( ctrlTime );
            vctrlToSock.set_elapsedFromSock( elapSock );
            vctrlToSock.set_elapsedThruProc( elapProc );
            
            // Interaction ready, send data thru
            // KEEP the later part because control is the last part of the loop and we want the timestep
            // to iterate when the interaction is received.
            vctrlToSock.sendInteraction(getLRC(), currentTime + getLookAhead());
            
            //Log stuff for humans
            log.info("=> Sent elapsedTimeSock = {},    elapsedTimeProc = {},    ctrlTime = {}", elapSock, elapProc, ctrlTime);

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


    //There are two handleInteractionClass() methods becuase there are two C2WInteractionRoots
    // From processor
    private void handleInteractionClass(procToCtrl interaction) {
        ///////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the interaction //
        ///////////////////////////////////////////////////////////////
    	
    	// Define the appropriate variables and call the C2W interaction to get the values sent
    	sockTimeProc = interaction.get_sockTime();
    	procTime = interaction.get_procTime();
    	
    	//record what this federate received
    	log.info("From processor, sockTimeP = {}   procTime = {}", sockTimeProc, procTime);
    	// Set that processor interaction is received
    	recProc = true;
    }

    // From Socket
    private void handleInteractionClass(sockToCtrl interaction) {
        ///////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the interaction //
        ///////////////////////////////////////////////////////////////
    	
    	// Define the appropriate variables and call the C2W interaction to get the values sent
    	sockTime = interaction.get_sockTime();
    	
    	//record what this federate received
    	log.info("From socket, sockTime = {}", sockTime);
    	// Set that socket interaction is received
    	receivedSimTime = true;
    }
    
    public static void main(String[] args) {
        try {
            FederateConfigParser federateConfigParser =
                new FederateConfigParser();
            FederateConfig federateConfig =
                federateConfigParser.parseArgs(args, FederateConfig.class);
            control federate =
                new control(federateConfig);
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
