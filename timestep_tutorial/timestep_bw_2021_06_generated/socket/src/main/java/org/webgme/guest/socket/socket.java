/*
 * File:    socket.java
 * Project:	timestep demo
 * Author:  Brian Woo-Shem
 * Date:	2021-06-16
 * Owner:	Created using resources from Santa Clara University School of Engineering and NIST
 * Purpose:	Socket federate code in timestep demo simulation
 */

// Import packages necessary. Most are added automatically by WebGME
package org.webgme.guest.socket;
import org.webgme.guest.socket.rti.*;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.InteractionRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Define the socket type of federate for the federation.
// Non-commented code is created automatically by WebGME
public class socket extends socketBase {
    private final static Logger log = LogManager.getLogger();

    //Default variable tracking time
    private double currentTime = 0;

    public socket(FederateConfig params) throws Exception {
        super(params);
    }

    //Variables added for inputs and outputs
    double sockTime;
    double elapProc;
    double elapSock;
    
    private void checkReceivedSubscriptions() {
        InteractionRoot interaction = null;
        while ((interaction = getNextInteractionNoWait()) != null) {
            if (interaction instanceof ctrlToSock) {
                handleInteractionClass((ctrlToSock) interaction);
            }
            else {
                log.debug("unhandled interaction: {}", interaction.getClassName());
            }
        }
    }

    // the Execute method, which runs when the federation is active.
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

            // Set the interaction's parameters.
            //
            //    sockToCtrl vsockToCtrl = create_sockToCtrl();
            //    vsockToCtrl.set_actualLogicalGenerationTime( < YOUR VALUE HERE > );
            //    vsockToCtrl.set_federateFilter( < YOUR VALUE HERE > );
            //    vsockToCtrl.set_originFed( < YOUR VALUE HERE > );
            //    vsockToCtrl.set_sockTime( < YOUR VALUE HERE > );
            //    vsockToCtrl.set_sourceFed( < YOUR VALUE HERE > );
            //    vsockToCtrl.sendInteraction(getLRC(), currentTime + getLookAhead());
            //    sockToProc vsockToProc = create_sockToProc();
            //    vsockToProc.set_actualLogicalGenerationTime( < YOUR VALUE HERE > );
            //    vsockToProc.set_federateFilter( < YOUR VALUE HERE > );
            //    vsockToProc.set_originFed( < YOUR VALUE HERE > );
            //    vsockToProc.set_sockTime( < YOUR VALUE HERE > );
            //    vsockToProc.set_sourceFed( < YOUR VALUE HERE > );
            //    vsockToProc.sendInteraction(getLRC(), currentTime + getLookAhead());

            checkReceivedSubscriptions();
            
            // Set the socket's time to be sent to the current time
            // Note: we could have instead skipped this step and simply put in "currentTime" everywhere
            //       there is "sockTime" below. It was done this way here because it's easier to explain
            //		 where the info goes.
            sockTime = currentTime;
            
            // Create the C2W Interaction root objects through which info is sent across.
            // Copy paste this from the big commented section above labeled "Set the interaction's parameters"
            // Note we need two of them in the socket - sockToCtrl to control federate,
            //	and sockToProc to processor federate.
            sockToCtrl vsockToCtrl = create_sockToCtrl();
            sockToProc vsockToProc = create_sockToProc();
            
            // Set the variable sockTime in each of the C2Ws 
            // Copy pasted from above, but replaced "< YOUR VALUE HERE >" with "sockTime"
            vsockToCtrl.set_sockTime( sockTime );
            vsockToProc.set_sockTime( sockTime );
            
            // This tells the socket to send the data in the C2Ws.
            vsockToCtrl.sendInteraction(getLRC(), currentTime + getLookAhead());
            vsockToProc.sendInteraction(getLRC(), currentTime + getLookAhead());
            
            // Output this so we know what it did
            log.info("=> sent sockTime = {}", sockTime);

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

    private void handleInteractionClass(ctrlToSock interaction) {
        ///////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the interaction //
        ///////////////////////////////////////////////////////////////
    	
    	// Even though elapsedFromSock and elapsedThruProc were sent by control, by default
    	// it won't do anything with them. 
    	// Define the appropriate variables and call the C2W interaction to get the values sent
    	double elapsedFromSock = interaction.get_elapsedFromSock();
    	double elapsedThruProc = interaction.get_elapsedThruProc();
    	double ctrlTime = interaction.get_ctrlTime();
    	// Print data received in readable format
    	log.info("From socket, fromSock = {},    thruProc = {}", elapsedFromSock);
    	log.info("From processor, thruProc = {}", elapsedThruProc);
    }

    public static void main(String[] args) {
        try {
            FederateConfigParser federateConfigParser =
                new FederateConfigParser();
            FederateConfig federateConfig =
                federateConfigParser.parseArgs(args, FederateConfig.class);
            socket federate =
                new socket(federateConfig);
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
