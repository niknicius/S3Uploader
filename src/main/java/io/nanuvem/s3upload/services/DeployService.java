package io.nanuvem.s3upload.services;

import io.nanuvem.s3upload.models.Application;
import io.nanuvem.s3upload.utils.NormalizeStrings;
import io.nanuvem.s3upload.utils.S3Client;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.util.LinkedList;
import java.util.List;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

@Service
public class DeployService {

    S3Client s3Client;

    public DeployService() {
        this.s3Client = new S3Client(Region.US_EAST_1);
    }

    private static void putWebsiteThreat(String s1, Throwable throwable) {
        if(throwable != null){
            System.err.println(throwable.toString());
        }else{
            System.out.println(s1);
        }
    }

    public void deploy(Application application, List<Path> paths){
        System.out.println(s3Client);
        this.s3Client.createBucket(application.getName()).whenComplete((createBucketResponse, throwable) -> {
            if(throwable != null){
                System.err.println(throwable.getMessage());
            }else{
                putObjects(createBucketResponse.location().replace("/", ""), paths,
                        application.getLocation()).whenComplete((s, throwable1) -> {
                            if(throwable1 != null){
                                System.err.println(throwable1.toString());
                            }else{
                                deployWebsite(application,
                                        createBucketResponse.location().replace("/", ""))
                                        .whenComplete(DeployService::putWebsiteThreat);
                            }
                });
            }
        });
    }

    private CompletableFuture<String> deployWebsite(Application application, String bucketName){
        CompletableFuture<String> result = new CompletableFuture<>();
        this.s3Client.putWebsite(bucketName, application).whenComplete((putBucketWebsiteResponse, throwable) -> {
            if(throwable != null){
                result.completeExceptionally(throwable);
            }else{
                result.complete(putBucketWebsiteResponse.toString());
            }
        });
        return result;
    }

    private CompletableFuture<String> putObjects(String bucketName, List<Path> paths, String workDir){
        CompletableFuture<String> result = new CompletableFuture<>();
        List<CompletableFuture<PutObjectResponse>> files = new LinkedList<>();
        for(Path path: paths){
            String objKey = NormalizeStrings.normalizeS3ObjectKey(path.toString().replace(workDir, ""));
            files.add(this.s3Client.putObject(bucketName, objKey, path.toFile()));
        }
        CompletableFuture.allOf(files.toArray(CompletableFuture[]::new)).whenComplete((aVoid, throwable) -> {
            if(throwable != null){
                result.completeExceptionally(throwable);
            }else{
                result.complete("successfully");
            }
        });
        return result;
    }

}
