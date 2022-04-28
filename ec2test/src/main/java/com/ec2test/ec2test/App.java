package com.ec2test.ec2test;

import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.SdkClock.Instance;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeAccountAttributesResult;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.DescribeSnapshotsRequest;
import com.amazonaws.services.ec2.model.DescribeSnapshotsResult;
import com.amazonaws.services.ec2.model.DescribeVolumeAttributeRequest;
import com.amazonaws.services.ec2.model.DescribeVolumesRequest;
import com.amazonaws.services.ec2.model.DescribeVolumesResult;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.Snapshot;
import com.amazonaws.services.ec2.model.Volume;
import com.ec2test.ec2test.config.Config;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Starting...." );
        
        AWSCredentials credentials = new BasicAWSCredentials(Config.ACCESS_KEY_ID, Config.ACCESS_SEC_KEY);
        
        AmazonEC2 ec2Client = AmazonEC2ClientBuilder.standard()
        		              .withCredentials(new AWSStaticCredentialsProvider(credentials))
        		              .withRegion(Regions.AP_SOUTH_1)
        		              .build();
        
        DescribeInstancesRequest request = new DescribeInstancesRequest();
       
        System.out.println("retriving..");
       
        	System.out.println("inside..");
            DescribeInstancesResult response = ec2Client.describeInstances(request);
            						
             List<Reservation> reservations =  response.getReservations();
             
             int Start_state = 0,Stop_state = 0;
             
             for(Reservation res : reservations) {
            	for (com.amazonaws.services.ec2.model.Instance instance : res.getInstances()) {
					
				String state =	instance.getState().getName();
					if(state.equals("stopped")) {
						Stop_state++;
					}else {
						Start_state++;
					}
				}
             }
             System.out.println("Total instance : "+ (Start_state+Stop_state) + " stop : "+Stop_state+" start : "+Start_state);
            
            DescribeVolumesRequest volumeRequest = new DescribeVolumesRequest();
            DescribeVolumesResult volumeResult =    ec2Client.describeVolumes(volumeRequest);
            
          List<Volume> volumeList =  volumeResult.getVolumes();
          System.out.println("volumId...");
          for(Volume v : volumeList) {
        	
        	System.out.println(v.getVolumeId());
          }
            
             DescribeSnapshotsRequest snapshotRequest = new DescribeSnapshotsRequest();
            DescribeSnapshotsResult snapshotResult = ec2Client.describeSnapshots(snapshotRequest);
           List<Snapshot> snapshotlist = snapshotResult.getSnapshots();
           
           int snapCount = 0;
           
           System.out.println("snapshotList..");
           for(Snapshot s : snapshotlist) {
        	   String snap = s.getSnapshotId();
        	  if(!snap.isEmpty()) {
        	  snapCount++;
        	  }
           }
               System.out.println("snapshortCount : "+snapCount);
             System.out.println("end");
           
    }
}

