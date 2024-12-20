package com.quantum_leap.api.config;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSConfig {
    @Value("${aws.region}")
    private String awsRegion;

    /**
     * Creates and returns an Amazon S3 client instance.
     * <p>
     * This method builds the AWS S3 client using the following configurations:
     * </p>
     * <ul>
     *     <li>Region defined in the <code>application.properties</code> file.</li>
     *     <li>Pre-configured IAM credentials of the environment where the application is running.</li>
     * </ul>
     *
     * @return The AmazonS3 client instance.
     */
    @Bean
    public AmazonS3 createS3Instance(){
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withRegion(awsRegion)
                .build();
    }
}
