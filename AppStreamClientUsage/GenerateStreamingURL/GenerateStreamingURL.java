package com.amazonaws;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.http.JsonResponseHandler;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.appstream.*;
import com.amazonaws.services.appstream.model.*;

public class test {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws InterruptedException {

		List<String> names = Arrays.asList("<FLEET_NAME>");

		// Set Credentials

		AWSCredentials credentials = null;
		try {
			credentials = new ProfileCredentialsProvider().getCredentials();
			System.out.println("Credentials Found!");
		} catch (Exception e) {
			throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
					+ "Please make sure that your credentials file is at the correct "
					+ "location (~/.aws/credentials), and is in valid format.", e);
		}
		// Create AppStream Client

		System.out.println("Creating AppStream Client...");
		AmazonAppStream appstreamClient = new AmazonAppStreamClient(credentials);
		Region usEast1 = Region.getRegion(Regions.US_EAST_1);
		appstreamClient.setRegion(usEast1);

		// Create Fleet Request

		CreateFleetRequest req = new CreateFleetRequest();
		ComputeCapacity computeCapacity = new ComputeCapacity();
		computeCapacity.setDesiredInstances(1);
		req.setComputeCapacity(computeCapacity);
		req.setDisplayName("<FLEET_NAME>");
		req.setName("<FLEET_NAME>");
		req.setImageName("Amazon-AppStream2-Sample-Image-06-20-2017");
		req.setInstanceType("stream.standard.medium");

		// Create Stack Request

		CreateStackRequest req2 = new CreateStackRequest();
		req2.setDisplayName("<FLEET_NAME>");
		req2.setName("<FLEET_NAME>");

		// Create Start Fleet Request

		StartFleetRequest req3 = new StartFleetRequest();
		req3.setName("<FLEET_NAME>");

		// Create Describe Request

		DescribeFleetsRequest req4 = new DescribeFleetsRequest();
		req4.setNames(names);

		// Create Streaming URL request

		CreateStreamingURLRequest req5 = new CreateStreamingURLRequest();
		req5.setFleetName("<FLEET_NAME>");
		req5.setUserId("<USER_NAME>");
		req5.setStackName("<STACK_NAME>");

		// Create Associate Fleet Request

		AssociateFleetRequest req6 = new AssociateFleetRequest();
		req6.setFleetName("<FLEET_NAME>");
		req6.setStackName(">STACK_NAME>");

		// Main Function

		try {
			// Create Fleet
			appstreamClient.createFleet(req);
			System.out.println("Creating Fleet...");
			// Create a stack
			appstreamClient.createStack(req2);
			System.out.println("Creating Stack...");
			// Start Fleet
			appstreamClient.startFleet(req3);
			System.out.println("Starting Fleet...");
			
			// Check if fleet is started
			int count = 0;
			int maxTries = 5;
			while (true) {
				try {
					DescribeFleetsResult r = appstreamClient.describeFleets(req4);
					if (r.getFleets().toString().toLowerCase().contains("state: running")) {
						System.out.println("Fleet Started!");
						appstreamClient.associateFleet(req6);
						CreateStreamingURLResult CSurl = appstreamClient.createStreamingURL(req5);
						System.out.println("Streaming URL: " + CSurl.getStreamingURL());
						break;
					} else {
						System.out.println("Waiting for Fleet to start...");
						Thread.sleep(100000);
					}
				} catch (Exception e) {
					if (++count == maxTries)
						throw e;
				}
			}
			System.out.println("Streaming Link Generated Successfully!");
		} catch (AmazonAppStreamException e) {
			e.printStackTrace();
		}
	}
}