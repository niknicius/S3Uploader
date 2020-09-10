package io.nanuvem.s3upload.utils;

import io.nanuvem.s3upload.models.Application;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

public class S3Client {

    private Region region;
    private S3AsyncClient s3AsyncClient;

    public static String BUCKET_NAME_PREFIX = "potter-";

    public S3Client(Region region) {
        this.region = region;
        this.s3AsyncClient = S3AsyncClient.builder().region(region).build();
    }

    public boolean checkBucketExists(String bucketName){
        AtomicBoolean exists = new AtomicBoolean(false);
        CompletableFuture<ListBucketsResponse> buckets = this.s3AsyncClient.listBuckets();
        buckets.whenComplete((listBucketsResponse, throwable) -> {
            if(throwable != null){
                System.err.println(throwable);
            }else{
                for(Bucket bucket: listBucketsResponse.buckets()){
                    if(bucket.name().equals(bucketName)){
                        exists.set(true);
                        break;
                    }
                }
            }
        });
        return exists.get();
    }

    public CompletableFuture<CreateBucketResponse> createBucket(String projectName){
        return this.s3AsyncClient.createBucket(CreateBucketRequest.builder()
                .acl(BucketCannedACL.PUBLIC_READ)
                .bucket(BUCKET_NAME_PREFIX.concat(projectName).toLowerCase())
                .build());
    }

    public CompletableFuture<PutObjectResponse> putObject(String bucketName, String objectKey, File file){
        return this.s3AsyncClient.putObject(PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build(),
                AsyncRequestBody.fromFile(file));
    }

    public CompletableFuture<PutBucketWebsiteResponse> putWebsite(String bucketName, Application application){
        return this.s3AsyncClient.putBucketWebsite(PutBucketWebsiteRequest.builder()
                .bucket(bucketName)
                .websiteConfiguration(this.makeWebsiteConfiguration(application))
                .build());
    }

    private WebsiteConfiguration makeWebsiteConfiguration(Application application){
        return WebsiteConfiguration.builder().indexDocument(IndexDocument.builder().suffix(application.getHandler())
                .build()).build();
    }

    
}
