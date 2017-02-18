# AWS_Signature


  This is specially about uploading file on S3 using the AWS Signature Version 2 and AWS Signature Version 4.
  
Prerequisite:
            1.Amazon S3 credentials access key
            2.Amazon S3 credentials secret key
            3.Changes in src/main/resources/aws.prperties file according to your AWS bucket.
            4.Change REGION_NAME in AWSConstants.java file as per your bucket region.
            
Note:
      Amazon S3 supports Signature Version 4, a protocol for authenticating API requests to AWS services, in all AWS regions. At this time, AWS regions created before January 30, 2014 will continue to support the previous protocol, Signature Version 2. Any new regions after January 30, 2014 will support only Signature Version 4 and therefore all requests to those regions must be made with Signature Version 4.
      
      
 Run Application.java file.
 API request response is display on swagger-ui,use following link on Browser.               
 http://localhost:8080/swagger-ui.html
 After response you can put those response values in respective html files( src/web/java/signatureV2.html AND src/web/java/signatureV4.html).
