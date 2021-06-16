package org.webgme.guest.control.rti;

import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cpswt.utils.CpswtUtils;

import hla.rti.FederateNotExecutionMember;
import hla.rti.InteractionClassNotDefined;
import hla.rti.InteractionClassNotPublished;
import hla.rti.InteractionClassNotSubscribed;
import hla.rti.LogicalTime;
import hla.rti.NameNotFound;
import hla.rti.RTIambassador;
import hla.rti.ReceivedInteraction;

import org.cpswt.hla.*;

/**
* Implements InteractionRoot.C2WInteractionRoot.ctrlToSock
*/
public class ctrlToSock extends C2WInteractionRoot {

    private static final Logger logger = LogManager.getLogger();

    /**
    * Creates an instance of the ctrlToSock interaction class with default parameter values.
    */
    public ctrlToSock() {}

    private static int _actualLogicalGenerationTime_handle;
    private static int _ctrlTime_handle;
    private static int _elapsedFromSock_handle;
    private static int _elapsedThruProc_handle;
    private static int _federateFilter_handle;
    private static int _originFed_handle;
    private static int _sourceFed_handle;

    private static boolean _isInitialized = false;

    private static int _handle;

    /**
    * Returns the handle (RTI assigned) of the ctrlToSock interaction class.
    * Note: As this is a static method, it is NOT polymorphic, and so, if called on
    * a reference will return the handle of the class pertaining to the reference,
    * rather than the handle of the class for the instance referred to by the reference.
    * For the polymorphic version of this method, use {@link #getClassHandle()}.
    *
    * @return the RTI assigned integer handle that represents this interaction class
    */
    public static int get_handle() {
        return _handle;
    }

    /**
    * Returns the fully-qualified (dot-delimited) name of the ctrlToSock interaction class.
    * Note: As this is a static method, it is NOT polymorphic, and so, if called on
    * a reference will return the name of the class pertaining to the reference,
    * rather than the name of the class for the instance referred to by the reference.
    * For the polymorphic version of this method, use {@link #getClassName()}.
    *
    * @return the fully-qualified HLA class path for this interaction class
    */
    public static String get_class_name() {
        return "InteractionRoot.C2WInteractionRoot.ctrlToSock";
    }

    /**
    * Returns the simple name (the last name in the dot-delimited fully-qualified
    * class name) of the ctrlToSock interaction class.
    *
    * @return the name of this interaction class
    */
    public static String get_simple_class_name() {
        return "ctrlToSock";
    }

    private static Set< String > _datamemberNames = new HashSet< String >();
    private static Set< String > _allDatamemberNames = new HashSet< String >();

    /**
    * Returns a set containing the names of all of the non-hidden parameters in the
    * ctrlToSock interaction class.
    * Note: As this is a static method, it is NOT polymorphic, and so, if called on
    * a reference will return a set of parameter names pertaining to the reference,
    * rather than the parameter names of the class for the instance referred to by
    * the reference.  For the polymorphic version of this method, use
    * {@link #getParameterNames()}.
    *
    * @return a modifiable set of the non-hidden parameter names for this interaction class
    */
    public static Set< String > get_parameter_names() {
        return new HashSet< String >(_datamemberNames);
    }

    /**
    * Returns a set containing the names of all of the parameters in the
    * ctrlToSock interaction class.
    * Note: As this is a static method, it is NOT polymorphic, and so, if called on
    * a reference will return a set of parameter names pertaining to the reference,
    * rather than the parameter names of the class for the instance referred to by
    * the reference.  For the polymorphic version of this method, use
    * {@link #getParameterNames()}.
    *
    * @return a modifiable set of the parameter names for this interaction class
    */
    public static Set< String > get_all_parameter_names() {
        return new HashSet< String >(_allDatamemberNames);
    }

    static {
        _classNameSet.add("InteractionRoot.C2WInteractionRoot.ctrlToSock");
        _classNameClassMap.put("InteractionRoot.C2WInteractionRoot.ctrlToSock", ctrlToSock.class);

        _datamemberClassNameSetMap.put("InteractionRoot.C2WInteractionRoot.ctrlToSock", _datamemberNames);
        _allDatamemberClassNameSetMap.put("InteractionRoot.C2WInteractionRoot.ctrlToSock", _allDatamemberNames);

        _datamemberNames.add("ctrlTime");
        _datamemberNames.add("elapsedFromSock");
        _datamemberNames.add("elapsedThruProc");

        _datamemberTypeMap.put("ctrlTime", "double");
        _datamemberTypeMap.put("elapsedFromSock", "double");
        _datamemberTypeMap.put("elapsedThruProc", "double");

        _allDatamemberNames.add("actualLogicalGenerationTime");
        _allDatamemberNames.add("ctrlTime");
        _allDatamemberNames.add("elapsedFromSock");
        _allDatamemberNames.add("elapsedThruProc");
        _allDatamemberNames.add("federateFilter");
        _allDatamemberNames.add("originFed");
        _allDatamemberNames.add("sourceFed");
    }

    protected static void init(RTIambassador rti) {
        if (_isInitialized) return;
        _isInitialized = true;

        C2WInteractionRoot.init(rti);

        boolean isNotInitialized = true;
        while(isNotInitialized) {
            try {
                _handle = rti.getInteractionClassHandle("InteractionRoot.C2WInteractionRoot.ctrlToSock");
                isNotInitialized = false;
            } catch (FederateNotExecutionMember e) {
                logger.error("could not initialize: Federate Not Execution Member", e);
                return;
            } catch (NameNotFound e) {
                logger.error("could not initialize: Name Not Found", e);
                return;
            } catch (Exception e) {
                logger.error(e);
                CpswtUtils.sleepDefault();
            }
        }

        _classNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ctrlToSock", get_handle());
        _classHandleNameMap.put(get_handle(), "InteractionRoot.C2WInteractionRoot.ctrlToSock");
        _classHandleSimpleNameMap.put(get_handle(), "ctrlToSock");

        isNotInitialized = true;
        while(isNotInitialized) {
            try {
                _actualLogicalGenerationTime_handle = rti.getParameterHandle("actualLogicalGenerationTime", get_handle());
                _ctrlTime_handle = rti.getParameterHandle("ctrlTime", get_handle());
                _elapsedFromSock_handle = rti.getParameterHandle("elapsedFromSock", get_handle());
                _elapsedThruProc_handle = rti.getParameterHandle("elapsedThruProc", get_handle());
                _federateFilter_handle = rti.getParameterHandle("federateFilter", get_handle());
                _originFed_handle = rti.getParameterHandle("originFed", get_handle());
                _sourceFed_handle = rti.getParameterHandle("sourceFed", get_handle());
                isNotInitialized = false;
            } catch (FederateNotExecutionMember e) {
                logger.error("could not initialize: Federate Not Execution Member", e);
                return;
            } catch (InteractionClassNotDefined e) {
                logger.error("could not initialize: Interaction Class Not Defined", e);
                return;
            } catch (NameNotFound e) {
                logger.error("could not initialize: Name Not Found", e);
                return;
            } catch (Exception e) {
                logger.error(e);
                CpswtUtils.sleepDefault();
            }
        }

        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ctrlToSock.actualLogicalGenerationTime", _actualLogicalGenerationTime_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ctrlToSock.ctrlTime", _ctrlTime_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ctrlToSock.elapsedFromSock", _elapsedFromSock_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ctrlToSock.elapsedThruProc", _elapsedThruProc_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ctrlToSock.federateFilter", _federateFilter_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ctrlToSock.originFed", _originFed_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ctrlToSock.sourceFed", _sourceFed_handle);

        _datamemberHandleNameMap.put(_actualLogicalGenerationTime_handle, "actualLogicalGenerationTime");
        _datamemberHandleNameMap.put(_ctrlTime_handle, "ctrlTime");
        _datamemberHandleNameMap.put(_elapsedFromSock_handle, "elapsedFromSock");
        _datamemberHandleNameMap.put(_elapsedThruProc_handle, "elapsedThruProc");
        _datamemberHandleNameMap.put(_federateFilter_handle, "federateFilter");
        _datamemberHandleNameMap.put(_originFed_handle, "originFed");
        _datamemberHandleNameMap.put(_sourceFed_handle, "sourceFed");
    }

    private static boolean _isPublished = false;

    /**
    * Publishes the ctrlToSock interaction class for a federate.
    *
    * @param rti handle to the Local RTI Component
    */
    public static void publish(RTIambassador rti) {
        if (_isPublished) return;

        init(rti);

        synchronized(rti) {
            boolean isNotPublished = true;
            while(isNotPublished) {
                try {
                    rti.publishInteractionClass(get_handle());
                    isNotPublished = false;
                } catch (FederateNotExecutionMember e) {
                    logger.error("could not publish: Federate Not Execution Member", e);
                    return;
                } catch (InteractionClassNotDefined e) {
                    logger.error("could not publish: Interaction Class Not Defined", e);
                    return;
                } catch (Exception e) {
                    logger.error(e);
                    CpswtUtils.sleepDefault();
                }
            }
        }

        _isPublished = true;
        logger.debug("publish: {}", get_class_name());
    }

    /**
    * Unpublishes the ctrlToSock interaction class for a federate.
    *
    * @param rti handle to the Local RTI Component
    */
    public static void unpublish(RTIambassador rti) {
        if (!_isPublished) return;

        init(rti);

        synchronized(rti) {
            boolean isNotUnpublished = true;
            while(isNotUnpublished) {
                try {
                    rti.unpublishInteractionClass(get_handle());
                    isNotUnpublished = false;
                } catch (FederateNotExecutionMember e) {
                    logger.error("could not unpublish: Federate Not Execution Member", e);
                    return;
                } catch (InteractionClassNotDefined e) {
                    logger.error("could not unpublish: Interaction Class Not Defined", e);
                    return;
                } catch (InteractionClassNotPublished e) {
                    logger.error("could not unpublish: Interaction Class Not Published", e);
                    return;
                } catch (Exception e) {
                    logger.error(e);
                    CpswtUtils.sleepDefault();
                }
            }
        }

        _isPublished = false;
        logger.debug("unpublish: {}", get_class_name());
    }

    private static boolean _isSubscribed = false;

    /**
    * Subscribes a federate to the ctrlToSock interaction class.
    *
    * @param rti handle to the Local RTI Component
    */
    public static void subscribe(RTIambassador rti) {
        if (_isSubscribed) return;

        init(rti);

        synchronized(rti) {
            boolean isNotSubscribed = true;
            while(isNotSubscribed) {
                try {
                    rti.subscribeInteractionClass(get_handle());
                    isNotSubscribed = false;
                } catch (FederateNotExecutionMember e) {
                    logger.error("could not subscribe: Federate Not Execution Member", e);
                    return;
                } catch (InteractionClassNotDefined e) {
                    logger.error("could not subscribe: Interaction Class Not Defined", e);
                    return;
                } catch (Exception e) {
                    logger.error(e);
                    CpswtUtils.sleepDefault();
                }
            }
        }

        _isSubscribed = true;
        logger.debug("subscribe: {}", get_class_name());
    }

    /**
    * Unsubscribes a federate from the ctrlToSock interaction class.
    *
    * @param rti handle to the Local RTI Component
    */
    public static void unsubscribe(RTIambassador rti) {
        if (!_isSubscribed) return;

        init(rti);

        synchronized(rti) {
            boolean isNotUnsubscribed = true;
            while(isNotUnsubscribed) {
                try {
                    rti.unsubscribeInteractionClass(get_handle());
                    isNotUnsubscribed = false;
                } catch (FederateNotExecutionMember e) {
                    logger.error("could not unsubscribe: Federate Not Execution Member", e);
                    return;
                } catch (InteractionClassNotDefined e) {
                    logger.error("could not unsubscribe: Interaction Class Not Defined", e);
                    return;
                } catch (InteractionClassNotSubscribed e) {
                    logger.error("could not unsubscribe: Interaction Class Not Subscribed", e);
                    return;
                } catch (Exception e) {
                    logger.error(e);
                    CpswtUtils.sleepDefault();
                }
            }
        }

        _isSubscribed = false;
        logger.debug("unsubscribe: {}", get_class_name());
    }

    /**
    * Return true if "handle" is equal to the handle (RTI assigned) of this class
    * (that is, the ctrlToSock interaction class).
    *
    * @param handle handle to compare to the value of the handle (RTI assigned) of
    * this class (the ctrlToSock interaction class).
    * @return "true" if "handle" matches the value of the handle of this class
    * (that is, the ctrlToSock interaction class).
    */
    public static boolean match(int handle) {
        return handle == get_handle();
    }

    /**
    * Returns the handle (RTI assigned) of this instance's interaction class .
    *
    * @return the handle (RTI assigned) if this instance's interaction class
    */
    public int getClassHandle() {
        return get_handle();
    }

    /**
    * Returns the fully-qualified (dot-delimited) name of this instance's interaction class.
    *
    * @return the fully-qualified (dot-delimited) name of this instance's interaction class
    */
    public String getClassName() {
        return get_class_name();
    }

    /**
    * Returns the simple name (last name in its fully-qualified dot-delimited name)
    * of this instance's interaction class.
    *
    * @return the simple name of this instance's interaction class
    */
    public String getSimpleClassName() {
        return get_simple_class_name();
    }

    /**
    * Returns a set containing the names of all of the non-hiddenparameters of an
    * interaction class instance.
    *
    * @return set containing the names of all of the parameters of an
    * interaction class instance
    */
    public Set< String > getParameterNames() {
        return get_parameter_names();
    }

    /**
    * Returns a set containing the names of all of the parameters of an
    * interaction class instance.
    *
    * @return set containing the names of all of the parameters of an
    * interaction class instance
    */
    public Set< String > getAllParameterNames() {
        return get_all_parameter_names();
    }

    @Override
    public String getParameterName(int datamemberHandle) {
        if (datamemberHandle == _actualLogicalGenerationTime_handle) return "actualLogicalGenerationTime";
        else if (datamemberHandle == _ctrlTime_handle) return "ctrlTime";
        else if (datamemberHandle == _elapsedFromSock_handle) return "elapsedFromSock";
        else if (datamemberHandle == _elapsedThruProc_handle) return "elapsedThruProc";
        else if (datamemberHandle == _federateFilter_handle) return "federateFilter";
        else if (datamemberHandle == _originFed_handle) return "originFed";
        else if (datamemberHandle == _sourceFed_handle) return "sourceFed";
        else return super.getParameterName(datamemberHandle);
    }

    /**
    * Publishes the interaction class of this instance of the class for a federate.
    *
    * @param rti handle to the Local RTI Component
    */
    public void publishInteraction(RTIambassador rti) {
        publish(rti);
    }

    /**
    * Unpublishes the interaction class of this instance of this class for a federate.
    *
    * @param rti handle to the Local RTI Component
    */
    public void unpublishInteraction(RTIambassador rti) {
        unpublish(rti);
    }

    /**
    * Subscribes a federate to the interaction class of this instance of this class.
    *
    * @param rti handle to the Local RTI Component
    */
    public void subscribeInteraction(RTIambassador rti) {
        subscribe(rti);
    }

    /**
    * Unsubscribes a federate from the interaction class of this instance of this class.
    *
    * @param rti handle to the Local RTI Component
    */
    public void unsubscribeInteraction(RTIambassador rti) {
        unsubscribe(rti);
    }

    @Override
    public String toString() {
        return getClass().getName() + "("
                + "actualLogicalGenerationTime:" + get_actualLogicalGenerationTime()
                + "," + "ctrlTime:" + get_ctrlTime()
                + "," + "elapsedFromSock:" + get_elapsedFromSock()
                + "," + "elapsedThruProc:" + get_elapsedThruProc()
                + "," + "federateFilter:" + get_federateFilter()
                + "," + "originFed:" + get_originFed()
                + "," + "sourceFed:" + get_sourceFed()
                + ")";
    }

    private double _ctrlTime = 0;
    private double _elapsedFromSock = 0;
    private double _elapsedThruProc = 0;

    /**
    * Set the value of the "ctrlTime" parameter to "value" for this parameter.
    *
    * @param value the new value for the "ctrlTime" parameter
    */
    public void set_ctrlTime( double value ) {
        _ctrlTime = value;
    }

    /**
    * Returns the value of the "ctrlTime" parameter of this interaction.
    *
    * @return the value of the "ctrlTime" parameter
    */
    public double get_ctrlTime() {
        return _ctrlTime;
    }
    /**
    * Set the value of the "elapsedFromSock" parameter to "value" for this parameter.
    *
    * @param value the new value for the "elapsedFromSock" parameter
    */
    public void set_elapsedFromSock( double value ) {
        _elapsedFromSock = value;
    }

    /**
    * Returns the value of the "elapsedFromSock" parameter of this interaction.
    *
    * @return the value of the "elapsedFromSock" parameter
    */
    public double get_elapsedFromSock() {
        return _elapsedFromSock;
    }
    /**
    * Set the value of the "elapsedThruProc" parameter to "value" for this parameter.
    *
    * @param value the new value for the "elapsedThruProc" parameter
    */
    public void set_elapsedThruProc( double value ) {
        _elapsedThruProc = value;
    }

    /**
    * Returns the value of the "elapsedThruProc" parameter of this interaction.
    *
    * @return the value of the "elapsedThruProc" parameter
    */
    public double get_elapsedThruProc() {
        return _elapsedThruProc;
    }

    protected ctrlToSock( ReceivedInteraction datamemberMap, boolean initFlag ) {
        super( datamemberMap, false );
        if ( initFlag ) setParameters( datamemberMap );
    }

    protected ctrlToSock( ReceivedInteraction datamemberMap, LogicalTime logicalTime, boolean initFlag ) {
        super( datamemberMap, logicalTime, false );
        if ( initFlag ) setParameters( datamemberMap );
    }

    /**
    * Creates an instance of the ctrlToSock interaction class, using
    * "datamemberMap" to initialize its parameter values.
    * "datamemberMap" is usually acquired as an argument to an RTI federate
    * callback method, such as "receiveInteraction".
    *
    * @param datamemberMap data structure containing initial values for the
    * parameters of this new ctrlToSock interaction class instance
    */
    public ctrlToSock( ReceivedInteraction datamemberMap ) {
        this( datamemberMap, true );
    }

    /**
    * Like {@link #ctrlToSock( ReceivedInteraction datamemberMap )}, except this
    * new ctrlToSock interaction class instance is given a timestamp of
    * "logicalTime".
    *
    * @param datamemberMap data structure containing initial values for the
    * parameters of this new ctrlToSock interaction class instance
    * @param logicalTime timestamp for this new ctrlToSock interaction class
    * instance
    */
    public ctrlToSock( ReceivedInteraction datamemberMap, LogicalTime logicalTime ) {
        this( datamemberMap, logicalTime, true );
    }

    /**
    * Creates a new ctrlToSock interaction class instance that is a duplicate
    * of the instance referred to by ctrlToSock_var.
    *
    * @param ctrlToSock_var ctrlToSock interaction class instance of which
    * this newly created ctrlToSock interaction class instance will be a
    * duplicate
    */
    public ctrlToSock( ctrlToSock ctrlToSock_var ) {
        super( ctrlToSock_var );

        set_ctrlTime( ctrlToSock_var.get_ctrlTime() );
        set_elapsedFromSock( ctrlToSock_var.get_elapsedFromSock() );
        set_elapsedThruProc( ctrlToSock_var.get_elapsedThruProc() );
    }

    /**
    * Returns the value of the parameter whose name is "datamemberName"
    * for this interaction.
    *
    * @param datamemberName name of parameter whose value is to be
    * returned
    * @return value of the parameter whose name is "datamemberName"
    * for this interaction
    */
    public Object getParameter( String datamemberName ) {
        if ( "ctrlTime".equals(datamemberName) ) return new Double(get_ctrlTime());
        else if ( "elapsedFromSock".equals(datamemberName) ) return new Double(get_elapsedFromSock());
        else if ( "elapsedThruProc".equals(datamemberName) ) return new Double(get_elapsedThruProc());
        else return super.getParameter( datamemberName );
    }

    protected boolean setParameterAux( String datamemberName, String val ) {
        boolean retval = true;
        if ( "ctrlTime".equals( datamemberName) ) set_ctrlTime( Double.parseDouble(val) );
        else if ( "elapsedFromSock".equals( datamemberName) ) set_elapsedFromSock( Double.parseDouble(val) );
        else if ( "elapsedThruProc".equals( datamemberName) ) set_elapsedThruProc( Double.parseDouble(val) );
        else retval = super.setParameterAux( datamemberName, val );

        return retval;
    }

    protected boolean setParameterAux( String datamemberName, Object val ) {
        boolean retval = true;
        if ( "ctrlTime".equals( datamemberName) ) set_ctrlTime( (Double)val );
        else if ( "elapsedFromSock".equals( datamemberName) ) set_elapsedFromSock( (Double)val );
        else if ( "elapsedThruProc".equals( datamemberName) ) set_elapsedThruProc( (Double)val );
        else retval = super.setParameterAux( datamemberName, val );

        return retval;
    }

    public void copyFrom( Object object ) {
        super.copyFrom( object );
        if ( object instanceof ctrlToSock ) {
            ctrlToSock data = (ctrlToSock)object;
            _ctrlTime = data._ctrlTime;
            _elapsedFromSock = data._elapsedFromSock;
            _elapsedThruProc = data._elapsedThruProc;
        }
    }
}

