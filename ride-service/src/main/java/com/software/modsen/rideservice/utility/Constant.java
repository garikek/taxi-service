package com.software.modsen.rideservice.utility;

public class Constant {
    public static final String RIDE_NOT_FOUND_BY_ID = "Ride not found - %s";
    public static final String RIDE_NOT_FOUND_BY_PASSENGER_ID_AND_STATUS = "Ride not found by passenger id %s and status REQUESTED";
    public static final String RIDE_NOT_FOUND_BY_DRIVER_ID_AND_STATUS = "Ride not found by driver id %s and status IN PROGRESS";
    public static final String RIDE_STILL_IN_PROGRESS = "There is still a ride with driver id %s in progress";
    public static final String UNEXPECTED_ERROR_MESSAGE = "An unexpected error occurred. Please try again later.";
    public static final String AUTHORIZE_EXCEPTION_MESSAGE = "Access Denied: You don't have permission to access this resource.";
    public static final String INVALID_ADDRESS = "Invalid address: %s";
    public static final String RECEIVED_MESSAGE = "Received message: {}";
    public static final String UNKNOWN_ACTION_MESSAGE = "Unknown action: {}";
    public static final String ERROR_PROCESSING_MESSAGE = "Error processing message: {}";
    public static final String GET_RIDES = "Received list of rides";
    public static final String GET_RIDE_BY_ID = "Received ride by id {}";
    public static final String ADD_RIDE = "Ride created and saved";
    public static final String DELETE_RIDE = "Ride deleted";
    public static final String UPDATE_RIDE = "Ride updated";
    public static final String RIDE_STATUS_UPDATE = "Ride {} set status {}";
    public static final String SENDING_MESSAGE = "Sending message. Action : {}";
}
